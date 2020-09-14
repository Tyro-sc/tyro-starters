package sc.tyro.starter.junit4


import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.openqa.selenium.remote.BrowserType
import sc.tyro.core.component.Dropdown
import sc.tyro.core.component.field.EmailField
import sc.tyro.core.component.field.PasswordField

import static sc.tyro.core.Tyro.*
import static sc.tyro.starter.junit4.ComponentFactory.getForm

class LoginPageTest {
    @ClassRule
    public static TyroClassRule tyro = new TyroClassRule()

    @Before
    void setUp() {
        visit 'https://tyro-sc.github.io/tyro-starters/'
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
        on language select 'FR'

        email.should { have value('my@email.org') }
        password.should { have value('123456') }
        language.should { have selectedItem('FR') }

        form.should {
            be visible
            contain(email, password, language)
        }
    }
}
