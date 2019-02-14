package cj.software.movie.arqullian;

import java.io.File;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import cj.software.movie.entity.Movie;
import cj.software.movie.repository.MovieRepository;
import cj.software.movie.service.MovieLoader;
import cj.software.movie.service.MovieService;

@RunWith(Arquillian.class)
public class ArquillianBaseTest
{
	@Inject
	private MovieService movieService;

	@EJB
	private MovieRepository movieRepository;

	@PersistenceContext
	private EntityManager entityManager;

	private Logger logger = LogManager.getFormatterLogger();

	@Deployment
	public static WebArchive createDeployment()
	{
		String lSwarmJpaCoordinates = "org.wildfly.swarm:jpa";
		File[] lSwarmFiles = Maven
				.resolver()
				.loadPomFromFile("pom.xml")
				.resolve(lSwarmJpaCoordinates)
				.withTransitivity()
				.asFile();
		String lLog4jCoreCoordinates = "org.apache.logging.log4j:log4j-core";
		String lLog4jApiCoordinate = "org.apache.logging.log4j:log4j-api";
		File[] lLog4jCoreFiles = Maven
				.resolver()
				.loadPomFromFile("pom.xml")
				.resolve(lLog4jCoreCoordinates, lLog4jApiCoordinate)
				.withoutTransitivity()
				.asFile();
		WebArchive lResult = ShrinkWrap
				.create(WebArchive.class, ArquillianBaseTest.class + ".war")
				.addClasses(
						MovieLoader.class,
						MovieService.class,
						MovieRepository.class,
						Movie.class)
				.addAsLibraries(lSwarmFiles)
				.addAsLibraries(lLog4jCoreFiles)
				.addAsResource("log4j2.xml")
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		System.out.println(lResult.toString(true));
		System.setProperty("swarm.datasources.data-sources.MyDS.driver-name", "postgresql");
		System.setProperty(
				"swarm.datasources.data-sources.MyDS.connection-url",
				"jdbc:postgresql://localhost:5432/experiments");
		System.setProperty("swarm.datasources.data-sources.MyDS.user-name", "experiments");
		System.setProperty("swarm.datasources.data-sources.MyDS.password", "experiments");
		return lResult;
	}

	@Test
	public void allSetUp()
	{
		Assert.assertNotNull("entity mgr", this.entityManager);
		Assert.assertNotNull("movie service", this.movieService);
		Assert.assertNotNull("movie repo", this.movieRepository);
		this.logger.info("all checked");
	}

	@Test
	public void insertMovies()
	{
		long lId1 = this.movieRepository.save(
				this.createMovie("ein gutes Jahr", "Ridley Scott", 2006));
		this.logger.info("Movie has id %d: %s", lId1, "ein gutes Jahr");
		long lId2 = this.movieRepository.save(this.createMovie("Apollo 13", "Ron Howard", 1995));
		this.logger.info("Movie has id %d: %s", lId2, "Apollo 13");
		Query lQuery = this.entityManager.createQuery("SELECT COUNT(*) FROM Movie");
		List<?> lResultList = lQuery.getResultList();
		Number lResult = (Number) lResultList.get(0);
		Assert.assertEquals(2, lResult.longValue());
	}

	private Movie createMovie(String pTitle, String pDirector, int pYear)
	{
		Movie lResult = new Movie();
		lResult.setTitle(pTitle);
		lResult.setDirector(pDirector);
		lResult.setReleaseYear(pYear);
		return lResult;
	}
}
