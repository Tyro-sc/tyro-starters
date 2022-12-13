package sc.tyro.starter.junit5.steps

import io.cucumber.datatable.DataTable
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

import static sc.tyro.core.Tyro.*

class StepDefinitions {
    @Given("user visits our website")
    static void visit() {
        visit 'https://tyro-sc.github.io/tyro-starters'
    }

    @When("user gives '(.*)' as (.*)\$")
    static void fill(String value, String label) {
        fill field(label) with value
    }

    @And("user chooses '(.*)' as (.*)\$")
    static void select(String value, String label) {
        on dropdown(label) select value
    }

    @Then("filled values are:")
    static void values(DataTable table) {
        table.cells().each {
            if (it.get(0) == 'Language') {
                assert dropdown(it.get(0)).items().find { item -> item.selected() }.value() == it.get(1) != null ? it.get(1) : ''
            } else {
                assert field(it.get(0)).value() == (it.get(1) != null ? it.get(1) : '')
            }
        }
    }
}
