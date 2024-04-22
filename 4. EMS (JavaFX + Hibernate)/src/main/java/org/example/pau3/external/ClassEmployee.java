package org.example.pau3.external;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table (name = "classemployee")
public class ClassEmployee
{
    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "workgroup")
    private String workgroup;

    @Column(name = "maxEmployees")
    private int maxEmployees;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "classEmployee", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Employee> employeeList;

    //Constructor
    public ClassEmployee(String workgroup, int maxEmployees)
    {
        this.workgroup = workgroup;
        this.maxEmployees = maxEmployees;
        this.employeeList = new ArrayList<>();
    }

    public ClassEmployee()
    {
        employeeList = new LinkedList<>();
    }

    //Methods
    public boolean addEmployee(Employee tgt)
    {
        if(employeeList.size() >= maxEmployees)
            return false;

        for (Employee e : employeeList)
        {
            if(e == tgt)
                return false;
        }

        tgt.setClassEmployee(this);
        employeeList.add(tgt);
        return true;
    }

    Employee findEmployee(Employee tgt)
    {
        for (Employee employee : employeeList)
        {
            if (employee == tgt)
                return employee;
        }

        return null;
    }

    public List<Employee> searchPartial(String keyword)
    {
        keyword = keyword.toLowerCase();

        List<Employee> outputList = new ArrayList<>();
        for(Employee employee : this.employeeList)
        {
            if(employee.getName().toLowerCase().contains(keyword) || employee.getSurname().toLowerCase().contains(keyword))
                outputList.add(employee);
        }

        return outputList;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassEmployee that = (ClassEmployee) o;
        return id == that.id; // Equality based on ID
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    //getters
    public List<Employee> getEmployeeList() { return this.employeeList; }
    public int getMaxEmployees() { return this.maxEmployees; }
    public int getId() { return this.id; }
    public String getWorkgroup() { return this.workgroup; }

    //setters
    public void setWorkgroup(String w) { this.workgroup = w; }
    public void setMax(int m) { this.maxEmployees = m; }
}
