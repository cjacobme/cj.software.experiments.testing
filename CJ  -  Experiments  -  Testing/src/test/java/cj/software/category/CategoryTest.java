package cj.software.category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CategoryTest
{
	Logger logger = LogManager.getFormatterLogger();

	@Test
	@Category(
	{ ComponentTest.class, IntegrationTest.class
	})
	public void componentOrIntegration()
	{
		this.logger.info("component or integration");
	}

	@Test
	@Category(ComponentTest.class)
	public void componentOnly()
	{
		this.logger.info("component only");
	}

	@Test
	@Category(UnitTest.class)
	public void unit()
	{
		this.logger.info("unit");
	}
}
