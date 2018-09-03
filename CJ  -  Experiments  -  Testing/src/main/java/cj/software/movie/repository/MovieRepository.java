package cj.software.movie.repository;

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

}
