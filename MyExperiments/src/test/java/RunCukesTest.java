import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(monochrome = true, plugin = { "pretty", "html:target/cucumber"},
        tags = {"@helloWorld"},features = "src/test/resources/feature")
public class RunCukesTest extends AbstractTestNGCucumberTests {


}