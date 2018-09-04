package cj.software.movie.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import cj.software.movie.entity.Movie;
import cj.software.movie.repository.MovieRepository;

public class MovieService
{

	MovieRepository movieRepository;

	MovieLoader movieLoader;

	public Movie searchByMovieId(Long pMovieId) throws FileNotFoundException, IOException
	{
		Optional<Movie> lCached = this.movieRepository.findById(pMovieId);
		Movie lResult;
		if (lCached.isPresent())
		{
			lResult = lCached.get();
		}
		else
		{
			lResult = this.movieLoader.searchByMovieId(pMovieId);
		}
		return lResult;
	}

}
