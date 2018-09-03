package cj.software.movie.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Before;
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

	private MovieRepository repository;

	@Before
	public void setupRepository()
	{
		this.repository = new MovieRepository();
		this.repository.entityManager = this.entityManager;
	}

	@Test
	public void saveMovie()
	{
		Movie lNewMovie = this.createMovie(false);
		Movie lSavedMovie = this.createMovie(true);

		when(this.entityManager.merge(lNewMovie)).thenReturn(lSavedMovie);

		long lReturnedId = this.repository.save(lNewMovie);

		verify(this.entityManager).merge(lNewMovie);
		assertThat(lReturnedId).as("returned id").isEqualTo(MOVIE_ID);
	}

	@Test
	public void findExistingMovie()
	{
		Movie lReturnedMovie = this.createMovie(true);
		when(this.entityManager.find(Movie.class, MOVIE_ID)).thenReturn(lReturnedMovie);

		Optional<Movie> lReturned = this.repository.findById(MOVIE_ID);
		verify(this.entityManager).find(Movie.class, MOVIE_ID);
		assertThat(lReturned)
				.isNotNull()
				.hasValue(lReturnedMovie)
				.usingFieldByFieldValueComparator();
	}

	@Test
	public void findNonExistingMovie()
	{
		when(this.entityManager.find(Movie.class, MOVIE_ID)).thenReturn(null);
		Optional<Movie> lReturned = this.repository.findById(MOVIE_ID);
		verify(this.entityManager).find(Movie.class, MOVIE_ID);
		assertThat(lReturned).isNotNull().isNotPresent();
	}

	private Movie createMovie(boolean pWithId)
	{
		Movie lResult = new Movie();
		lResult.setTitle("Das Boot");
		lResult.setDirector("Wolfgang Petersen");
		lResult.setReleaseYear(1981);
		if (pWithId)
		{
			lResult.setId(MOVIE_ID);
		}
		return lResult;
	}
}
