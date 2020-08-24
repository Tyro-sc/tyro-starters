package sc.tyro.starter.junit5

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static sc.tyro.core.Tyro.*
import static sc.tyro.starter.junit5.ComponentFactory.*

@ExtendWith(WebdriverExtension.class)
class LoginPageTest {
    @BeforeEach
    void setUp() {
        visit 'https://tyro-sc.github.io/tyro-starters/'
    }

    @Test
    @DisplayName("Should set the form")
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

        dropdown('Language').should {
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