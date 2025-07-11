package sc.tyro.starter.junit5.steps

import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import sc.tyro.web.WebBundle

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver
import static io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver
import static java.lang.Boolean.valueOf
import static java.lang.System.getenv

class Hooks {
    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class)

    private static WebDriver webDriver
    private static WebDriverManager wdm
    private static boolean isCI = valueOf(getenv('CI'))
    private static String browser = getenv('browser')?.toLowerCase()

    @Before
    static void beforeScenario(Scenario scenario) {
        LOGGER.info("Scenario: " + scenario.getName() + " started")
        System.setProperty("webdriver.http.factory", "jdk-http-client")

        if (!browser) {
            println "No browser selected. Use Chrome"
            browser = "chrome"
        }

        switch (browser) {
            case "firefox":
                wdm = firefoxdriver()
                FirefoxOptions options = new FirefoxOptions()
                options.addArguments("--start-fullscreen")
                options.addArguments("--start-maximized")
                wdm.capabilities(options)
                break
            case "chrome":
                wdm = chromedriver()
                ChromeOptions options = new ChromeOptions()
                options.addArguments("--start-fullscreen")
                wdm.capabilities(options)
                break
            default:
                throw new IllegalStateException("Fail to set browser: " + browser)
        }

        if (isCI) {
            wdm.browserInDocker()
        }

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
