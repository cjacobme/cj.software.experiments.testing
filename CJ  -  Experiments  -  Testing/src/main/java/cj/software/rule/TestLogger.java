/**
 * 
 */
package cj.software.rule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * a test rule that logs the test activities
 */
public class TestLogger
		implements
		TestRule
{
	@Override
	public Statement apply(Statement pStatement, Description pDescription)
	{
		Statement lResult = new Statement()
		{

			@Override
			public void evaluate() throws Throwable
			{
				String lName = pDescription.getClassName() + "." + pDescription.getDisplayName();
				final Logger logger = LogManager.getFormatterLogger(lName);
				String lMethodName = pDescription.getMethodName();
				logger.info(lMethodName);
				pStatement.evaluate();
			}
		};
		return lResult;
	}

}
