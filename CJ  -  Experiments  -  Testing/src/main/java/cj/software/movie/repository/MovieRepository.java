package cj.software.movie.repository;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cj.software.movie.entity.Movie;

@Stateless
public class MovieRepository
{
	@PersistenceContext
	EntityManager entityManager;

	public long save(Movie pMovie)
	{
		Movie lMerged = this.entityManager.merge(pMovie);
		return lMerged.getId();
	}

	public Optional<Movie> findById(Long pMovieId)
	{
		Movie lFound = this.entityManager.find(Movie.class, pMovieId);
		Optional<Movie> lResult = Optional.ofNullable(lFound);
		return lResult;
	}

}
