package org.example.pau3.external;

import java.util.*;

public class ClassEmployee
{
    //Attributes
    String workgroup;
    List<Employee> employeeList;
    int maxEmployees;

    //Constructor
    public ClassEmployee(String workgroup, int maxEmployees)
    {
        this.workgroup = workgroup;
        this.maxEmployees = maxEmployees;
        this.employeeList = new ArrayList<>();
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

    boolean addSalary(Employee tgt, double amount)
    {
        Employee found = findEmployee(tgt);
        if(found == null)
            return false;

        found.salary += amount;
        return true;
    }

    boolean removeEmployee(Employee tgt) { return employeeList.remove(tgt); }

    boolean changeCondition(Employee tgt, EmployeeCondition newCond)
    {
        Employee found = findEmployee(tgt);
        if( found == null)
            return false;

        found.employeeCondition = newCond;
        return true;
    }

    public void search(String surname) //input to surname???
    {
        Comparator<Employee> comparator = new Comparator<Employee>()
        {
            public int compare(Employee e1, Employee e2)
            {
                return e1.surname.compareTo(e2.surname);
            }
        };

        //kms
        Employee tgt = new Employee(null, surname, null, 0, 0);

        for(Employee employee : employeeList)
        {
            if(comparator.compare(employee, tgt) == 0)
            {
                employee.printing();
                return;
            }
        }
    }

    public List<Employee> searchPartial(String keyword)
    {
        keyword = keyword.toLowerCase();

        List<Employee> outputList = new ArrayList<>();
        for(Employee employee : this.employeeList)
        {
            if(employee.name.toLowerCase().contains(keyword) || employee.surname.toLowerCase().contains(keyword))
                outputList.add(employee);
        }

        return outputList;
    }

    int countByCondition (EmployeeCondition ec)
    {
        int count = 0;
        for(Employee employee : employeeList)
        {
            if(employee.employeeCondition == ec)
                count++;
        }

        return count;
    }

    void summary()
    {
        System.out.println("---------------------------------");
        System.out.println("Lista pracowników grupy: " + workgroup);

        for(Employee e : employeeList)
            e.printing();
    }

    List<Employee> sortByName()
    {
        Comparator<Employee> comparator = new Comparator<Employee>()
        {
            public int compare(Employee e1, Employee e2)
            {
                return e1.name.compareTo(e2.name);
            }
        };

        List<Employee> sortedList = new ArrayList<>(this.employeeList);
        sortedList.sort(comparator);
        return sortedList;
    }

    List<Employee> sortBySalary()
    {
        Comparator<Employee> comparator = new Comparator<Employee>()
        {
            public int compare(Employee e1, Employee e2) {
                return Double.compare(e1.salary, e2.salary);
            }
        };

        List<Employee> sortedList = new ArrayList<>(this.employeeList);
        sortedList.sort(comparator);
        return sortedList;
    }

    Employee maxSalary()
    {
        Employee s1 = Collections.max(employeeList, new Comparator< Employee >()
        {
            public int compare(Employee w1, Employee w2)
            {
                return Double.compare(w1.salary, w2.salary);
            }
        });

        return s1;
    }

    //getters
    public List<Employee> getEmployeeList() { return this.employeeList; }
    public int getMaxEmployees() { return this.maxEmployees; }
    public String getWorkgroup() { return this.workgroup; }

    //setters
    public void setWorkgroup(String w) { this.workgroup = w; }
    public void setMax(int m) { this.maxEmployees = m; }
}
