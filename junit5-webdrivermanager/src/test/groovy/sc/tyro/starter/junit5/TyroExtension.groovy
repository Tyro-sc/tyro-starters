package sc.tyro.starter.junit5

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import sc.tyro.web.WebBundle

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver
import static io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver
import static java.lang.Boolean.valueOf
import static java.lang.System.getenv

class TyroExtension implements BeforeAllCallback, AfterAllCallback {
    private static WebDriver webDriver
    private static WebDriverManager wdm
    private boolean isCI = valueOf(getenv('CI'))
    private String browser = getenv('browser')?.toLowerCase()

    @Override
    void beforeAll(ExtensionContext extensionContext) throws Exception {
        // Add -DbrowserType=firefox/chrome/... to you VM Option to select the browser
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

    @Override
    void afterAll(ExtensionContext extensionContext) throws Exception {
        webDriver.quit()
        wdm.quit()
    }
}
