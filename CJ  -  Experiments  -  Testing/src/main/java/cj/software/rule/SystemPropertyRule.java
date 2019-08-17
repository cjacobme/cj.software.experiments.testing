package cj.software.rule;

import java.util.HashMap;
import java.util.Map;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * a rule that can be used for setting and clearing of System Properties.
 */
public class SystemPropertyRule
		implements
		TestRule
{
	private Map<String, String> properties;

	private SystemPropertyRule()
	{
		this.properties = new HashMap<>();
	}

	@Override
	public Statement apply(Statement pStatement, Description pDescription)
	{
		Statement lResult = new Statement()
		{
			@Override
			public void evaluate() throws Throwable
			{
				SystemPropertyRule.this.setSystemProperties();
				try
				{
					pStatement.evaluate();
				}
				finally
				{
					SystemPropertyRule.this.clearProperties();
				}
			}
		};
		return lResult;
	}

	private void setSystemProperties()
	{
		for (String bKey : this.properties.keySet())
		{
			String lValue = this.properties.get(bKey);
			System.setProperty(bKey, lValue);
		}
	}

	private void clearProperties()
	{
		for (String bKey : this.properties.keySet())
		{
			System.clearProperty(bKey);
		}
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder
	{
		SystemPropertyRule instance;

		private Builder()
		{
			this.instance = new SystemPropertyRule();
		}

		public Builder withProperty(String pKey, String pValue)
		{
			this.instance.properties.put(pKey, pValue);
			return this;
		}

		public SystemPropertyRule build()
		{
			SystemPropertyRule lResult = this.instance;
			this.instance = null;
			return lResult;
		}
	}
}
