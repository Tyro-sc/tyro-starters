package sc.tyro.starter.junit4

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import org.testcontainers.containers.BrowserWebDriverContainer
import org.testcontainers.containers.DefaultRecordingFileFactory
import org.testcontainers.lifecycle.TestDescription
import sc.tyro.web.WebBundle

import static org.junit.runners.model.MultipleFailureException.assertEmpty
import static org.openqa.selenium.remote.BrowserType.CHROME
import static org.openqa.selenium.remote.BrowserType.FIREFOX
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL

class TyroClassRule implements TestRule {
    private static BrowserWebDriverContainer container
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
    Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            void evaluate() throws Throwable {
                before()

                List<Throwable> errors = new ArrayList<Throwable>()
                try {
                    base.evaluate()
                } catch (Throwable t) {
                    errors.add(t)
                } finally {
                    try {
                        after(description)
                    } catch (Throwable t) {
                        errors.add(t)
                    }
                }
                assertEmpty(errors)
            }
        }
    }

    private static void before() throws Throwable {
        container = new BrowserWebDriverContainer()
                .withRecordingMode(RECORD_ALL, new File("./target/"))
                .withRecordingFileFactory(new DefaultRecordingFileFactory())

        switch (browser) {
            case FIREFOX:
                container.withCapabilities(new FirefoxOptions())
                break
            case CHROME:
                container.withCapabilities(new ChromeOptions())
                break
        }

        container = new BrowserWebDriverContainer()
                .withCapabilities(new FirefoxOptions())
                .withRecordingMode(RECORD_ALL, new File("./target/"))
                .withRecordingFileFactory(new DefaultRecordingFileFactory())

        container.start()
        WebBundle.init(container.webDriver)
    }

    private static void after(Description description) {
        final String uniqueId = container.containerId

        container.afterTest(new TestDescription() {
            @Override
            String getTestId() { return uniqueId }

            @Override
            String getFilesystemFriendlyName() { return description.displayName }
        }, Optional.empty())

        container.webDriver.quit()
        container.stop()
    }
}
