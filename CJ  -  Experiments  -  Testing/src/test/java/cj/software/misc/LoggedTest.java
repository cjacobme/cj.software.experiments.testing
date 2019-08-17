package cj.software.misc;

import static org.assertj.core.api.Assertions.*;

import org.junit.Rule;
import org.junit.Test;

import cj.software.rule.SystemPropertyRule;
import cj.software.rule.TestLogger;

public class LoggedTest
{
	@Rule
	public TestLogger testLogger = new TestLogger();

	@Rule
	public SystemPropertyRule sysPropRule = SystemPropertyRule
			.builder()
			.withProperty("one", "1")
			.withProperty("two", "2")
			.withProperty("three", "3")
			.build();

	@Test
	public void one()
	{
		assertThat("one").isEqualTo("two");
	}

	@Test
	public void sysPropSet()
	{
		String lValue = System.getProperty("one");
		assertThat(lValue).isEqualTo("1");
	}

	@Test
	public void sysPropNotSet()
	{
		String lValue = System.getProperty("arbitrary");
		assertThat(lValue).isNull();
	}
}
