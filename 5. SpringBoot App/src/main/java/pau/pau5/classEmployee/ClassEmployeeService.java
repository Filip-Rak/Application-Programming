package pau.pau5.classEmployee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pau.pau5.employee.Employee;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClassEmployeeService
{
    // Attributes
    private final ClassEmployeeRepository classEmployeeRepository;

    // Constructor
    @Autowired
    public ClassEmployeeService(ClassEmployeeRepository classEmployeeRepository)
    {
        this.classEmployeeRepository = classEmployeeRepository;
    }

    // Methods
    public List<ClassEmployee> getGroups()
    {
        return classEmployeeRepository.findAll();
    }

    public ClassEmployee addGroup(ClassEmployee classEmployee)
    {
        return classEmployeeRepository.save(classEmployee);
    }

    public void deleteGroup(int id)
    {
        classEmployeeRepository.deleteById(id);
    }

    public List<Employee> getEmployees(int id)
    {
        Optional<ClassEmployee> classEmployee = classEmployeeRepository.findById(id);
        return classEmployee.map(ClassEmployee::getEmployeeList).orElse(Collections.emptyList());
    }

    public double getUtilization(int id)
    {
        Optional<ClassEmployee> classEmployee =  classEmployeeRepository.findById(id);

        if(classEmployee.isEmpty()) return 0;

        double max = classEmployee.get().getMaxEmployees();
        double size = classEmployee.get().getEmployeeList().size();

        return size / max;
    }
}
