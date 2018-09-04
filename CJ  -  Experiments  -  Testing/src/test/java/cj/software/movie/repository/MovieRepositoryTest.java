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
import cj.software.movie.entity.MovieSetup;

@RunWith(MockitoJUnitRunner.class)
public class MovieRepositoryTest
{

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
		Movie lNewMovie = MovieSetup.createMovie(false);
		Movie lSavedMovie = MovieSetup.createMovie(true);

		when(this.entityManager.merge(lNewMovie)).thenReturn(lSavedMovie);

		long lReturnedId = this.repository.save(lNewMovie);

		verify(this.entityManager).merge(lNewMovie);
		assertThat(lReturnedId).as("returned id").isEqualTo(MovieSetup.MOVIE_ID);
	}

	@Test
	public void findExistingMovie()
	{
		Movie lReturnedMovie = MovieSetup.createMovie(true);
		when(this.entityManager.find(Movie.class, MovieSetup.MOVIE_ID)).thenReturn(lReturnedMovie);

		Optional<Movie> lReturned = this.repository.findById(MovieSetup.MOVIE_ID);
		verify(this.entityManager).find(Movie.class, MovieSetup.MOVIE_ID);
		assertThat(lReturned)
				.isNotNull()
				.hasValue(lReturnedMovie)
				.usingFieldByFieldValueComparator();
	}

	@Test
	public void findNonExistingMovie()
	{
		when(this.entityManager.find(Movie.class, MovieSetup.MOVIE_ID)).thenReturn(null);
		Optional<Movie> lReturned = this.repository.findById(MovieSetup.MOVIE_ID);
		verify(this.entityManager).find(Movie.class, MovieSetup.MOVIE_ID);
		assertThat(lReturned).isNotNull().isNotPresent();
	}
}
