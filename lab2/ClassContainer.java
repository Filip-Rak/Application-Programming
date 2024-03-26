import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassContainer
{
    //Attributes
    Map<String, ClassEmployee> workgroups;

    //Constructor
    ClassContainer()
    {
        workgroups = new HashMap<>();
    }

    //Methods
    void addClass(String name, double size)
    {
        ClassEmployee newClass = new ClassEmployee(name, (int)size);
        this.workgroups.put(name, newClass);
    }

    void addClass(String name, ClassEmployee ce) { this.workgroups.put(name, ce); }

    boolean removeClass(String name)
    {
        ClassEmployee removed =  workgroups.remove(name);
        return removed == null;
    }

    List<ClassEmployee> findEmpty()
    {
        List<ClassEmployee> empty = new ArrayList<>();

        workgroups.forEach((name, group) ->
                {
                    if(group.employeeList.isEmpty())
                        empty.add(group);
                }

        );

        return empty;
    }

    void summary()
    {
        workgroups.forEach((name, group) ->
                {
                    int currentSize = group.employeeList.size();
                    int maxSize = group.maxEmployees;

                    System.out.println("------------------------------");
                    System.out.println("Nazwa grupy: " + name);
                    System.out.print("Populacja: " + currentSize + " z " + maxSize);

                    if(maxSize != 0)
                    {
                        double percentage = (double)currentSize / (double)maxSize * 100;
                        System.out.print(" : " + percentage + "%\n");
                    }
                    else
                        System.out.print(": 0.0%\n");

                    System.out.println("Pracownicy grupy:");
                    for(Employee employee : group.employeeList)
                        employee.printing("\t");

                }

        );
    }
}
