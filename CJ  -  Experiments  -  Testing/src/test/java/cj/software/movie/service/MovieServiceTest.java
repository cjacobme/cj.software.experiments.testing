package cj.software.movie.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import cj.software.movie.entity.Movie;
import cj.software.movie.entity.MovieSetup;
import cj.software.movie.repository.MovieRepository;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest
{
	@Mock
	private MovieRepository movieRepository;

	@Mock
	private MovieLoader movieLoader;

	private MovieService movieService;

	@Before
	public void setupMovieService()
	{
		this.movieService = new MovieService();
		this.movieService.movieRepository = this.movieRepository;
		this.movieService.movieLoader = this.movieLoader;
	}

	@Test
	public void returnCachedDirectly() throws FileNotFoundException, IOException
	{
		Movie lMovie = MovieSetup.createMovie(true);

		when(this.movieRepository.findById(MovieSetup.MOVIE_ID)).thenReturn(Optional.of(lMovie));

		final Movie lFoundMovie = this.movieService.searchByMovieId(MovieSetup.MOVIE_ID);
		assertThat(lFoundMovie).as("found movie").isEqualToComparingFieldByField(lMovie);

		verify(this.movieLoader, times(0)).searchByMovieId(anyLong());
		verify(this.movieRepository).findById(MovieSetup.MOVIE_ID);
	}

	@Test
	public void returnNotCachedShouldBeLoaded() throws FileNotFoundException, IOException
	{
		when(this.movieRepository.findById(MovieSetup.MOVIE_ID)).thenReturn(Optional.empty());
		Movie lMovie = MovieSetup.createMovie(true);
		when(this.movieLoader.searchByMovieId(MovieSetup.MOVIE_ID)).thenReturn(lMovie);

		Movie lFoundMovie = this.movieService.searchByMovieId(MovieSetup.MOVIE_ID);
		assertThat(lFoundMovie).as("found movie").isEqualToComparingFieldByField(lMovie);
		verify(this.movieLoader).searchByMovieId(MovieSetup.MOVIE_ID);
	}
}
