import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        //Pracownicy
        Employee e1 = new Employee( //c1
                "Eliza",
                "Parowiec",
                EmployeeCondition.DELEGACJA,
                1998,
                2450
        );

        Employee e2 = new Employee( //c1
                "Magdalena",
                "Bugaj",
                EmployeeCondition.CHORY,
                2002,
                1890
        );

        Employee e3 = new Employee( //c1
                "Andrzej",
                "Niemic",
                EmployeeCondition.DELEGACJA,
                2001,
                3090
        );

        Employee e4 = new Employee( //c2
                "Antoni",
                "Wilno",
                EmployeeCondition.NIEOBECNY,
                2019,
                147
        );

        Employee e5 = new Employee( //c2
                "Wojciech",
                "Mozart",
                EmployeeCondition.OBECNY,
                1897,
                30459
        );

        Employee e6 = new Employee( //c3
                "Justyna",
                "Dziura",
                EmployeeCondition.DELEGACJA,
                2002,
                2060
        );

        Employee e7 = new Employee( //c3
                "Kazimierz",
                "Wielki",
                EmployeeCondition.NIEOBECNY,
                2004,
                5060
        );

        Employee e8 = new Employee( //c4
                "Rafal",
                "Zablotni",
                EmployeeCondition.OBECNY,
                1920,
                10450
        );

        //Dzialy pracownikow
        ClassEmployee c1 = new ClassEmployee("C1: HR", 4);
        ClassEmployee c2 = new ClassEmployee("C2: R&D", 4);
        ClassEmployee c3 = new ClassEmployee("C3: SALES", 8);
        ClassEmployee c4 = new ClassEmployee("C4: THE BIG BOOS", 1);
        ClassEmployee c5 = new ClassEmployee("C5: QUALITY ASSURANCE", 3);
        ClassEmployee c6 = new ClassEmployee("C6: EMPLOYEE RIGHTS", 4);

        //Dodawniae pracownikow do dzialow
        c1.addEmployee(e1);
        c1.addEmployee(e2);
        c1.addEmployee(e3);
        c2.addEmployee(e4);
        c2.addEmployee(e5);
        c3.addEmployee(e6);
        c3.addEmployee(e7);
        c4.addEmployee(e8);

        //Kontener dla dzialow
        ClassContainer cc = new ClassContainer();

        //Dodawanie dizalow do kontenera
        cc.addClass(c1.workgroup, c1);  //3 os
        cc.addClass(c2.workgroup, c2);  //2 os
        cc.addClass(c3.workgroup, c3);  //2 os
        cc.addClass(c4.workgroup, c4);  //1 os
        cc.addClass(c5.workgroup, c5);  //pusta grupa
        cc.addClass(c6.workgroup, c6);  //pusta grupa

        //2.1 Zwiekszenie wynagrodzenia pracownikowi w grupie
        System.out.println(2.1);
        c1.addSalary(e1, 800);
        e1.printing();

        //2.2 Usuniecie pracownika z grupy
        System.out.println(2.2);
        c3.removeEmployee(e7);
        c3.summary();

        //2.3 Zmiana stanu pracownika w grupie
        System.out.println(2.3);
        c2.changeCondition(e4, EmployeeCondition.OBECNY);
        e4.printing();

        //2.4 Szukanie pracownika w grupie
        System.out.println(2.4);
        c3.search(e6.surname);

        //2.5 Szukanie po znakach
        System.out.println(2.5);
        List<Employee> found = c2.searchPartial("W");
        for(Employee employee : found)
            employee.printing();

        //2.6 Zliczanie po stanie
        System.out.println(2.6);
        System.out.println("Na delegacji w grupie c1: " + c1.countByCondition(EmployeeCondition.DELEGACJA));

        //2.7 Sortowanie po imieniu
        System.out.println(2.7);
        found = c1.sortByName();
        for(Employee employee : found)
            employee.printing();

        //2.8 Sortowanie po wynagrodzeniu
        System.out.println(2.8);
        found = c1.sortBySalary();
        for(Employee employee : found)
            employee.printing();

        //2.9 Najwyzsze wynagrodzenie w grupie
        System.out.println(2.9);
        Employee max = c1.maxSalary();
        max.printing();

        //3.1 Podsumowanie kontenera cc (efektywnie wszystkich)
        System.out.println(3.1);
        cc.summary();

        //3.2 Wypisanie pustych grup
        System.out.println(3.2);
        List<ClassEmployee> empty = cc.findEmpty();
        for(ClassEmployee ce : empty)
            ce.summary();
    }
}
