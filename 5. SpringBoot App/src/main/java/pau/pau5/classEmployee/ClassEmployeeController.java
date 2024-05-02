package pau.pau5.classEmployee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pau.pau5.employee.Employee;

import java.util.List;

@RestController
@RequestMapping(path = "api/group")
public class ClassEmployeeController
{
    // Attributes
    private final ClassEmployeeService classEmployeeService;

    // Constructor
    @Autowired
    public ClassEmployeeController(ClassEmployeeService classEmployeeService)
    {
        this.classEmployeeService = classEmployeeService;
    }

    // Methods
    @GetMapping
    public List<ClassEmployee> getGroups()
    {
        return classEmployeeService.getGroups();
    }

    @PostMapping
    public ResponseEntity<ClassEmployee> addGroup(@RequestBody ClassEmployee group)
    {
        ClassEmployee savedGroup = classEmployeeService.addGroup(group);
        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }

    @DeleteMapping(path = ":{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable int id)
    {
        classEmployeeService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = ":{id}/employee")
    public List<Employee> getEmployees(@PathVariable int id)
    {
        return classEmployeeService.getEmployees(id);
    }

    @GetMapping(path = ":{id}/fill")
    public double getUtilization(@PathVariable int id)
    {
        return classEmployeeService.getUtilization(id);
    }
}
