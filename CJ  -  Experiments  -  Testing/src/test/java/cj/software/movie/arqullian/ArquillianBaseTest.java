package cj.software.movie.arqullian;

import java.io.File;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
