package br.com.ada;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeManagementSystem {

    private List<Employee> employees;

    public void loadEmployeesFromFile(String path) throws IOException {

        List<String> linhas = Files.readAllLines(Path.of(path));

        employees = linhas.stream()
                .map(l -> parseEmployee(l))
                .collect(Collectors.toList());

    }

    public Employee parseEmployee(String linha) {
        String[] fields = linha.split(";");

        LocalDate hireDate = LocalDate.parse(fields[2], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        double salary = Double.parseDouble(fields[3].replace(".", "")); //4040

        return new Employee(fields[0], fields[1], hireDate, salary);
    }

    public List<Employee> getEmployees() {
        if (employees == null){
            employees = new ArrayList<>();
        }
        return employees;
    }

    public List<Employee> filterEmployees(Predicate<Employee> predicate){
        return employees.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public Optional<Employee> getEmployee(Predicate<Employee> predicate){
        try {
            return employees.stream()
                    .filter(predicate)
                    .findFirst();
        }catch (Exception ex){
            throw new RuntimeException("O filtro retornou mais de um elemento");
        }
    }

    public double calculateTotalSalary(){
        return employees.stream()
                .mapToDouble(e-> e.getSalary())
                .sum();
    }

    public double calculateAnnualSalary(Employee employee){
        if(employee == null){
            throw new RuntimeException("Employee não pode ser nulo");
        }
        return employee.getSalary() * 12;
    }

    public Optional<Employee> findEmployeeWithHighestSalary(){
        return employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary));
    }

    public LocalDateTime getCurrentDateTimeInTimezone(String timeZone){
        return LocalDateTime.now(ZoneId.of(timeZone));
    }
}
