package sc.tyro.starter.junit4

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.rules.ExternalResource
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.BrowserType
import sc.tyro.web.WebBundle

import static org.openqa.selenium.remote.BrowserType.FIREFOX

class TyroClassRule extends ExternalResource {
    private static WebDriver webDriver
    private static String browser;

    TyroClassRule(String browser) {
        TyroClassRule.browser = browser
    }

    TyroClassRule() {
        // Add -DbrowserType=firefox/chrome/... to you VM Option to select the browser
        browser = System.getProperty("browserType")
        if (!browser) {
            println "No browser selected. Use Firefox"
            browser = FIREFOX
        }
    }

    @Override
    protected void before() throws Throwable {
        switch (browser) {
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup()
                webDriver = new FirefoxDriver()
                break
            case BrowserType.CHROME:
                WebDriverManager.chromedriver().setup()
                webDriver = new ChromeDriver()
                break
        }

        WebBundle.init(webDriver)
    }

    @Override
    protected void after() {
        webDriver.quit()
    }
}
