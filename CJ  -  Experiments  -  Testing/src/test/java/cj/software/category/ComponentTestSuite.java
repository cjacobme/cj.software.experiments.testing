package cj.software.category;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@Categories.IncludeCategory(ComponentTest.class)
@SuiteClasses(
{ AllTests.class
})
public class ComponentTestSuite
{

}
