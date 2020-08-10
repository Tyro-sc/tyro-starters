package sc.tyro.starter.junit4

import sc.tyro.bundle.html5.heading.H1
import sc.tyro.bundle.html5.input.InputTypeEmail
import sc.tyro.bundle.html5.input.InputTypePassword
import sc.tyro.bundle.html5.list.Select
import sc.tyro.core.component.Dropdown
import sc.tyro.core.component.Form
import sc.tyro.core.component.Heading
import sc.tyro.core.component.field.EmailField
import sc.tyro.core.component.field.PasswordField

import static sc.tyro.core.Tyro.$

class ComponentFactory {
    static Heading getTitle() {
        $('h1:first') as H1
    }

    static Form getForm() {
        $('form:first') as sc.tyro.bundle.html5.Form
    }

    static EmailField getEmail_field() {
        $('#email') as InputTypeEmail
    }

    static PasswordField getPassword_field() {
        $('[type=password]') as InputTypePassword
    }

    static Dropdown getLanguage_dropdown() {
        $('select:first') as Select
    }
}
