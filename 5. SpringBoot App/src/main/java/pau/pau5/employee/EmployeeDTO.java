package pau.pau5.employee;

public record EmployeeDTO(
        String name,
        String surname,
        EmployeeCondition employeeCondition,
        int birthYear,
        double salary,
        int classEmployeeId
) { }