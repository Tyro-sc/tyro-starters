package sc.tyro.starter.junit4

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import sc.tyro.core.component.Dropdown
import sc.tyro.core.component.field.EmailField
import sc.tyro.core.component.field.PasswordField
import sc.tyro.web.WebBundle

import static sc.tyro.core.Tyro.*
import static sc.tyro.starter.junit4.ComponentFactory.getForm

class LoginPageTest {
    private static WebDriver webDriver

    @BeforeClass
    static void setupClass() {
        WebDriverManager.firefoxdriver().setup()
        webDriver = new FirefoxDriver()
        WebBundle.init(webDriver)
    }

    @AfterClass
    static void teardown() {
        webDriver.quit()
    }

    @Before
    void setUp() {
        visit 'https://tyro-sc.github.io/tyro-starters'
    }

    @Test
    void form_test() {
        heading('Login Form').should { be visible }

        EmailField email = field('Email')
        email.should {
            be visible
            be empty
        }

        PasswordField password = field('Password')
        password.should {
            be visible
            be empty
        }

        Dropdown language = dropdown('Language')
        language.should {
            be visible
            have 2.items
            have items('EN', 'FR')
            have selectedItem('EN')
        }

        fill email with 'my@email.org'
        fill password with '123456'

        email.should {
            have value('my@email.org')
        }

        password.should {
            have value('123456')
        }

        form.should {
            be visible
            contain(email, password, language)
        }
    }
}
