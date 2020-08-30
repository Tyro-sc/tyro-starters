import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber)
@CucumberOptions(plugin = ["pretty", "html:target/cucumber"], monochrome = true)
class RunCucumberTest {
}
