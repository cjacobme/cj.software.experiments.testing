package cj.software.category;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@Categories.IncludeCategory(
{ IntegrationTest.class, UnitTest.class
})
@SuiteClasses(
{ AllTests.class
})
public class CombinedTestSuites
{

}
