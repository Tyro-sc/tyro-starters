package sc.tyro.starter.junit4

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.rules.TestName
import org.openqa.selenium.firefox.FirefoxOptions
import org.testcontainers.containers.BrowserWebDriverContainer
import org.testcontainers.containers.DefaultRecordingFileFactory
import org.testcontainers.lifecycle.TestDescription
import sc.tyro.web.WebBundle

import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL

class BaseTest {
    @ClassRule
    public static TestName name = new TestName()

    static BrowserWebDriverContainer container

    @BeforeClass
    static void setUp() {
        container = new BrowserWebDriverContainer()
                .withCapabilities(new FirefoxOptions())
                .withRecordingMode(RECORD_ALL, new File("./target/"))
                .withRecordingFileFactory(new DefaultRecordingFileFactory())

        container.start()
        WebBundle.init(container.webDriver)
    }

    @AfterClass
    static void teardown() {
        final String uniqueId = container.containerId

        container.afterTest(new TestDescription() {
            @Override
            String getTestId() { return uniqueId }

            @Override
            String getFilesystemFriendlyName() { return name }
        }, Optional.empty());

        container.webDriver.quit()
        container.stop()
    }
}
