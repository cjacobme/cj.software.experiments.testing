package cj.software.movie.arqullian;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
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

	@Deployment
	public static WebArchive createDeployment()
	{
		WebArchive lResult = ShrinkWrap
				.create(WebArchive.class, ArquillianBaseTest.class + ".war")
				.addClasses(
						MovieLoader.class,
						MovieService.class,
						MovieRepository.class,
						Movie.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return lResult;
	}

	@Test
	public void allSetUp()
	{
		Assert.assertNotNull("entity mgr", this.entityManager);
		Assert.assertNotNull("movie service", this.movieService);
		Assert.assertNotNull("movie repo", this.movieRepository);
	}
}
