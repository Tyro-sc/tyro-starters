package sc.tyro.starter.junit4

import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.openqa.selenium.firefox.FirefoxOptions
import org.testcontainers.containers.BrowserWebDriverContainer
import org.testcontainers.containers.DefaultRecordingFileFactory
import org.testcontainers.lifecycle.TestDescription

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL

class WebdriverExtension implements BeforeEachCallback, AfterEachCallback {
    static BrowserWebDriverContainer container

    @Override
    void beforeEach(ExtensionContext extensionContext) throws Exception {
        container = new BrowserWebDriverContainer()
                .withCapabilities(new FirefoxOptions())
                .withRecordingMode(RECORD_ALL, new File("./target/"))
                .withRecordingFileFactory(new DefaultRecordingFileFactory())

        container.start()
        extensionContext
                .getStore(GLOBAL)
                .put(BrowserWebDriverContainer.class.getSimpleName(), container)
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
            String getFilesystemFriendlyName() { return name; }
        }, Optional.empty());

        container.stop();
    }
}
