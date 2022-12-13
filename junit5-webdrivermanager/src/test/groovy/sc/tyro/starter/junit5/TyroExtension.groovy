package sc.tyro.starter.junit5

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.openqa.selenium.WebDriver
import sc.tyro.web.WebBundle

class TyroExtension implements BeforeAllCallback, AfterAllCallback {
    private static WebDriver webDriver
    private static WebDriverManager wdm

    @Override
    void beforeAll(ExtensionContext extensionContext) throws Exception {
        // Add -DbrowserType=firefox/chrome/... to you VM Option to select the browser
        String browser = System.getProperty("browserType")
        if (!browser) {
            println "No browser selected. Use Firefox"
            browser = "firefox"
        }

        switch (browser) {
            case "firefox":
                wdm = WebDriverManager.firefoxdriver()
                break
            case "chrome":
                wdm = WebDriverManager.chromedriver()
                break
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
