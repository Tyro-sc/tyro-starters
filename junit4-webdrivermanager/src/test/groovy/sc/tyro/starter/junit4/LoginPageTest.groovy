package sc.tyro.starter.junit4

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import sc.tyro.web.WebBundle

import static sc.tyro.core.Tyro.*
import static sc.tyro.starter.junit4.ComponentFactory.*

class LoginPageTest {
    private static WebDriver webDriver

    @BeforeClass
    static void setupClass() {
        WebDriverManager.firefoxdriver().setup()
        webDriver = new FirefoxDriver()
        WebBundle.init(webDriver)
    }

    @After
    void teardown() {
        webDriver.quit()
    }

    @Before
    void setUp() {
        visit 'https://tyro-sc.github.io/tyro-starters/'
    }

    @Test
    void form_test() {
        title.should { have text('Login Form') }

        form.should {
            be visible
            contain(email_field, password_field, language_dropdown)
        }

        email_field.should {
            be visible
            be empty
            have label('Email')
        }

        password_field.should {
            be visible
            be empty
            have label('Password')
        }

        language_dropdown.should {
            be visible
            have 2.items
            have items('EN', 'FR')
        }

        fill email_field with 'my@email.org'
        fill password_field with '123456'

        email_field.should {
            have value('my@email.org')
        }

        password_field.should {
            have value('123456')
        }
    }
}
