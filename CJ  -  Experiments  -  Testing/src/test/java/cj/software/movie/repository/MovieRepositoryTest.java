package cj.software.movie.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import cj.software.movie.entity.Movie;

@RunWith(MockitoJUnitRunner.class)
public class MovieRepositoryTest
{
	private static final long MOVIE_ID = 4711L;

	@Mock
	private EntityManager entityManager;

	@Test
	public void saveMovie()
	{
		Movie lNewMovie = this.createMovie();
		Movie lSavedMovie = this.createMovie();
		lSavedMovie.setId(MOVIE_ID);

		when(this.entityManager.merge(lNewMovie)).thenReturn(lSavedMovie);

		MovieRepository lRepo = new MovieRepository();
		lRepo.entityManager = this.entityManager;

		long lReturnedId = lRepo.save(lNewMovie);

		verify(this.entityManager).merge(lNewMovie);
		assertThat(lReturnedId).as("returned id").isEqualTo(MOVIE_ID);
	}

	private Movie createMovie()
	{
		Movie lResult = new Movie();
		lResult.setTitle("Das Boot");
		lResult.setDirector("Wolfgang Petersen");
		lResult.setReleaseYear(1981);
		return lResult;
	}
}
