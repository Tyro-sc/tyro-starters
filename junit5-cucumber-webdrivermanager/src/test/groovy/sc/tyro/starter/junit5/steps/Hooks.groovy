package sc.tyro.starter.junit5.steps

import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import sc.tyro.web.WebBundle

import static io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver

class Hooks {
    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class)

    private static WebDriver webDriver
    private static WebDriverManager wdm

    @Before
    static void beforeScenario(Scenario scenario) {
        LOGGER.info("Scenario: " + scenario.getName() + " started")

        wdm = firefoxdriver()
        webDriver = wdm.create()
        WebBundle.init(webDriver)
    }

    @After
    static void afterScenario(Scenario scenario) {
        LOGGER.info("Scenario: " + scenario.name + " finished.Status: " + scenario.status)

        webDriver.quit()
        wdm.quit()
    }
}
