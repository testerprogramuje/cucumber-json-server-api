package pl.akademiaqa.cucumber.steps.employee;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;
import pl.akademiaqa.api.employee.ReadEmployeeRequest;
import pl.akademiaqa.context.Context;
import pl.akademiaqa.handlers.employee.EmployeeResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ReadEmployeeSteps {

    private final ReadEmployeeRequest readEmployeeRequest;
    private final Context context;

    private List<EmployeeResponse> allEmployeesBeforeCreate;

    @Given("I read all employees")
    public void i_read_all_employees() {
        allEmployeesBeforeCreate = readEmployeeRequest.readAllEmployees();
    }

    @Then("I see created employee on employees list")
    public void i_see_created_employee_on_employees_list() {
        List<EmployeeResponse> allEmployeesAfterCreate = readEmployeeRequest.readAllEmployees();
        assertThat(allEmployeesAfterCreate).hasSizeGreaterThan(allEmployeesBeforeCreate.size());

        EmployeeResponse createEmployeeResponse = context.getEmployeeResponse();
        assertThat(allEmployeesAfterCreate).contains(createEmployeeResponse);
    }

    @Then("I should not see deleted employee on employees list")
    public void i_should_not_see_deleted_employee_on_employees_list() {
        List<EmployeeResponse> allEmployeesAfterDelete = readEmployeeRequest.readAllEmployees();
        assertThat(allEmployeesAfterDelete).doesNotContain(context.getEmployeeResponse());
    }
}
