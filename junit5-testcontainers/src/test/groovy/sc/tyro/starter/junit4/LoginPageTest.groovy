package sc.tyro.starter.junit4

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import sc.tyro.web.WebBundle

import static sc.tyro.core.Tyro.*
import static sc.tyro.starter.junit4.ComponentFactory.*
import static sc.tyro.starter.junit4.WebdriverExtension.container

@ExtendWith(WebdriverExtension.class)
class LoginPageTest {
    @BeforeEach
    void setUp() {
        WebBundle.init(container.webDriver)
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