package sc.tyro.starter.junit4

import org.junit.Test
import sc.tyro.bundle.html5.input.InputTypeEmail
import sc.tyro.core.component.Form
import sc.tyro.core.component.field.EmailField

class SampleTest {

    @Test
    void form_test() {
        Form form = $('form:first') as sc.tyro.bundle.html5.Form

        EmailField email = $('[name=email]') as InputTypeEmail

        form.should {
            contains(

            )
        }
    }
}
