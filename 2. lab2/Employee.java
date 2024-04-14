public class Employee implements Comparable<Employee>
{
    //Attributes
    String name, surname;
    EmployeeCondition employeeCondition;
    int birth_year;
    double salary;

    //Constructor
    Employee(String name, String surname, EmployeeCondition ec, int birth_year, double salary)
    {
        this.name = name;
        this.surname = surname;
        this.employeeCondition = ec;
        this.birth_year = birth_year;
        this.salary = salary;
    }

    //Methods
    void printing()
    {
        System.out.println("-------------------------------------------");
        System.out.println("Imie: " + name + ", nazwisko: " + surname);
        System.out.println("Stan: " + employeeCondition);
        System.out.println("Rok urodzenia: " + birth_year);
        System.out.println("Wynagrodzenie: " + salary);
    }

    void printing(String prefix)
    {
        System.out.println(prefix + "-------------------------------------------");
        System.out.println(prefix + "Imie: " + name + ", nazwisko: " + surname);
        System.out.println(prefix + "Stan: " + employeeCondition);
        System.out.println(prefix + "Rok urodzenia: " + birth_year);
        System.out.println(prefix + "Wynagrodzenie: " + salary);
    }

    public int compareTo(Employee o) { return this.surname.compareTo(o.surname); }
}
