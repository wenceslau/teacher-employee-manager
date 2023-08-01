package br.com.ada;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws IOException {

        EmployeeManagementSystem managementSystem = new EmployeeManagementSystem();

        managementSystem.loadEmployeesFromFile("employees.txt");

        System.out.println("TODOS OS EMPREGADOS");
        List<Employee> employees = managementSystem.getEmployees();
        employees.stream().forEach(e -> System.out.println(e));


        System.out.println("EMPREGADOS CONTRATADOS APOS 01/01/2022");
        LocalDate dataFiltro  = LocalDate.of(2022,1,1);
        Predicate<Employee> filtro =  e ->  e.getHireDate().isAfter(dataFiltro) ;
        employees = managementSystem.filterEmployees(filtro);
        employees.stream().forEach(e -> System.out.println(e));


        employees = managementSystem.filterEmployees(e -> e.getSalary() >= 3500);
        System.out.println("Salario Maior que 3500");
        employees.stream().forEach(e -> System.out.println(e));

        double totalSalary = managementSystem.calculateTotalSalary();
        System.out.println("Total Salary: " + totalSalary);


        System.out.println("Maior Salario");
        Optional<Employee> optional = managementSystem.findEmployeeWithHighestSalary();
        if (optional.isPresent()){
            System.out.println(optional.get());
        }

        System.out.println("Menor Salario");
        Optional<Employee> optMin = employees.stream()
                .min(Comparator.comparingDouble(Employee::getSalary));
        if (optMin.isPresent()){
            System.out.println(optMin.get());
        }

        System.out.println("Salario Anual de cada Empregado");
        employees.stream().forEach(e -> {
            System.out.println(e.getName());
            System.out.println(managementSystem.calculateAnnualSalary(e));
        });

        System.out.println("Empregado matricula 25000");
        Optional<Employee> employee = managementSystem.getEmployee(e->e.getCode().equals("25000"));
        if (employee.isPresent()){
            System.out.println(employee.get());
        }

    }
}