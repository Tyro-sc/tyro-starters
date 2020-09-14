package sc.tyro.starter.junit5

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.BrowserType
import org.testcontainers.containers.BrowserWebDriverContainer
import org.testcontainers.containers.DefaultRecordingFileFactory
import org.testcontainers.lifecycle.TestDescription
import sc.tyro.web.WebBundle

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL
import static org.openqa.selenium.remote.BrowserType.CHROME
import static org.openqa.selenium.remote.BrowserType.FIREFOX
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL

class TyroExtension implements BeforeAllCallback, AfterAllCallback, AfterEachCallback {
    static BrowserWebDriverContainer container

    @Override
    void beforeAll(ExtensionContext extensionContext) throws Exception {
        container = new BrowserWebDriverContainer()
                .withRecordingMode(RECORD_ALL, new File("./target/"))
                .withRecordingFileFactory(new DefaultRecordingFileFactory())

        // Add -DbrowserType=firefox/chrome/... to you VM Option to select the browser
        String browser = System.getProperty("browserType")
        if (!browser) {
            println "No browser selected. Use Firefox"
            browser = FIREFOX
        }

        switch (browser) {
            case FIREFOX:
                container.withCapabilities(new FirefoxOptions())
                break
            case CHROME:
                container.withCapabilities(new ChromeOptions())
                break
        }

        container.start()
        extensionContext
                .getStore(GLOBAL)
                .put(BrowserWebDriverContainer.class.getSimpleName(), container)

        WebBundle.init(container.webDriver)
    }

    @Override
    void afterAll(ExtensionContext extensionContext) throws Exception {
        container.webDriver.quit()
        container.stop()
    }

    @Override
    void afterEach(ExtensionContext extensionContext) throws Exception {
        final BrowserWebDriverContainer container = extensionContext
                .getStore(GLOBAL)
                .get(BrowserWebDriverContainer.class.getSimpleName(), BrowserWebDriverContainer.class)

        final String uniqueId = extensionContext.getUniqueId()
        final String name = extensionContext.getRequiredTestMethod().getName()

        container.afterTest(new TestDescription() {
            @Override
            String getTestId() { return uniqueId }

            @Override
            String getFilesystemFriendlyName() { return name }
        }, Optional.empty())
    }
}