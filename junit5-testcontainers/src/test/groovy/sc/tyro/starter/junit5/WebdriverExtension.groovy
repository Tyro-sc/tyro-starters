package sc.tyro.starter.junit5


import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.openqa.selenium.firefox.FirefoxOptions
import org.testcontainers.containers.BrowserWebDriverContainer
import org.testcontainers.containers.DefaultRecordingFileFactory
import org.testcontainers.lifecycle.TestDescription
import sc.tyro.web.WebBundle

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL

class WebdriverExtension implements BeforeAllCallback, AfterAllCallback, AfterEachCallback {
    static BrowserWebDriverContainer container

    @Override
    void beforeAll(ExtensionContext extensionContext) throws Exception {
        container = new BrowserWebDriverContainer()
                .withCapabilities(new FirefoxOptions())
                .withRecordingMode(RECORD_ALL, new File("./target/"))
                .withRecordingFileFactory(new DefaultRecordingFileFactory())

        container.start()
        extensionContext
                .getStore(GLOBAL)
                .put(BrowserWebDriverContainer.class.getSimpleName(), container)

        WebBundle.init(container.webDriver)
    }

    @Override
    void afterAll(ExtensionContext extensionContext) throws Exception {
        container.stop();
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
            String getTestId() { return uniqueId; }

            @Override
            String getFilesystemFriendlyName() { return name }
        }, Optional.empty());
    }
}
