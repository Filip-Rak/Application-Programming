package pau.pau5.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pau.pau5.classEmployee.ClassEmployee;

import java.util.List;

@Service
public class EmployeeService
{
    // Attributes
    private final EmployeeRepository employeeRepository;


    // Constructor
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }

    // Methods
    public List<Employee> getEmployees()
    {
        return employeeRepository.findAll();
    }

    public Employee addEmployee(Employee employee)
    {
        if(employee.getClassEmployee() == null)
        {
            //ClassEmployee classEmployee = //find class employee
            //employee.setClassEmployee();
        }
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(int id)
    {
        employeeRepository.deleteById(id);
    }
}
