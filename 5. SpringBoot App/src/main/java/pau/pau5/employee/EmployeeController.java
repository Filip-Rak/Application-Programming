package pau.pau5.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/employee")
public class EmployeeController
{
    // Attributes
    private final EmployeeService employeeService;

    // Constructor
    @Autowired
    public EmployeeController(EmployeeService employeeService)
    {
        this.employeeService = employeeService;
    }

    // Methods
    @GetMapping(path = "getAll")
    public List<Employee> getEmployees()
    {
        return employeeService.getEmployees();
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee)
    {
        Employee savedEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @DeleteMapping(path = ":{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id)
    {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}
