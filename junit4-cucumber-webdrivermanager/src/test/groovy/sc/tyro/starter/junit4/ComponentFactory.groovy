package sc.tyro.starter.junit4


import sc.tyro.core.component.Form

import static sc.tyro.core.Tyro.$

class ComponentFactory {
    static Form getForm() {
        $('form:first') as sc.tyro.bundle.html5.Form
    }
}
