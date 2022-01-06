package sc.tyro.starter.junit4

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.rules.ExternalResource
import org.openqa.selenium.WebDriver
import sc.tyro.web.WebBundle

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver
import static io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver
import static org.openqa.selenium.remote.BrowserType.CHROME
import static org.openqa.selenium.remote.BrowserType.FIREFOX

class TyroClassRule extends ExternalResource {
    private static WebDriver webDriver
    private static WebDriverManager wdm
    private static String browser

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
                wdm = firefoxdriver()
                break
            case CHROME:
                wdm = chromedriver()
                break
        }

        webDriver = wdm.create()
        WebBundle.init(webDriver)
    }

    @Override
    protected void after() {
        webDriver.quit()
        wdm.quit()
    }
}
