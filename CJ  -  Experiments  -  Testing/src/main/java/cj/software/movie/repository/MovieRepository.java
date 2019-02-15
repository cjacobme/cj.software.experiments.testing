package cj.software.movie.repository;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cj.software.movie.entity.Movie;

@Stateless
public class MovieRepository
{
	private Logger logger = LogManager.getFormatterLogger();

	@PersistenceContext
	EntityManager entityManager;

	public long save(Movie pMovie)
	{
		Movie lMerged = this.entityManager.merge(pMovie);
		return lMerged.getId();
	}

	public Optional<Movie> findById(Long pMovieId)
	{
		String lProperty = System.getProperty("com.cj.required.example");
		if (lProperty == null)
		{
			throw new RuntimeException(
					"mandatory system propery \"com.cj.required.example\" not set");
		}
		this.logger.info("mandatory property: %s", lProperty);
		lProperty = System.getProperty("yet.another.property");
		if (lProperty != null)
		{
			this.logger.info("yet.another.property: %s", lProperty);
		}
		else
		{
			this.logger.info("yet.another.property not set");
		}
		Movie lFound = this.entityManager.find(Movie.class, pMovieId);
		Optional<Movie> lResult = Optional.ofNullable(lFound);
		return lResult;
	}

}
