package sc.tyro.starter.junit4

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import sc.tyro.bundle.html5.input.InputTypeEmail
import sc.tyro.core.component.Form
import sc.tyro.core.component.field.EmailField
import sc.tyro.web.WebBundle

import static sc.tyro.core.Tyro.*

class SampleTest {
    private WebDriver driver;

    @BeforeClass
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    void setupTest() {
        driver = new ChromeDriver();\
        WebBundle.init(driver)
    }

    @After
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Before
    void setUp() {
        visit 'https://tyro-sc.github.io/tyro-starters/'
    }

    @Test
    void form_test() {
        Form form = $('form:first') as sc.tyro.bundle.html5.Form
        EmailField email_field = $('#email') as InputTypeEmail

        form.should {
            be visible
            contains(email_field)
        }
    }
}
