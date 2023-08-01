package br.com.ada;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class EmployeeManagementSystemTest {

    @Test
    public void parseEmployee() {
        EmployeeManagementSystem managementSystem = new EmployeeManagementSystem();

        String linha = "0001;Joao Carlos;22/01/2021;4.040\n";
        Employee employee = managementSystem.parseEmployee(linha);

        Assert.assertEquals("0001", employee.getCode());
        Assert.assertEquals("Joao Carlos", employee.getName());
        Assert.assertEquals(LocalDate.of(2021, 1, 22), employee.getHireDate());
        System.out.println(employee.getSalary());
        Assert.assertEquals(new Double(4040),new Double(employee.getSalary()));


    }

    @Test
    public void loadEmployeesFromFile_success() throws IOException {

        EmployeeManagementSystem managementSystem = new EmployeeManagementSystem();

        managementSystem.loadEmployeesFromFile("employees.txt");

        List<Employee> list = managementSystem.getEmployees();

        Assert.assertEquals(15, list.size());

    }

    @Test
    public void filterEmployees() throws IOException {

        EmployeeManagementSystem managementSystem = new EmployeeManagementSystem();

        managementSystem.loadEmployeesFromFile("employees.txt");

        List<Employee> listFiltro = managementSystem.filterEmployees(e-> e.getName().startsWith("Ana"));

        Assert.assertEquals(listFiltro.get(2).getName(), "Ana Tavares");


    }

    @Test
    public void getEmployee() throws IOException {

        //0900;Lucia Santos;01/02/2023;3.000
        EmployeeManagementSystem managementSystem = new EmployeeManagementSystem();
        managementSystem.loadEmployeesFromFile("employees.txt");
        Optional<Employee> optional = managementSystem.getEmployee(e -> e.getName().equalsIgnoreCase("Lucia Santos"));

        Assert.assertEquals(true, optional.isPresent());
    }

    @Test
    public void calculateTotalSalary() throws IOException {
        EmployeeManagementSystem managementSystem = new EmployeeManagementSystem();
        managementSystem.loadEmployeesFromFile("employees.txt");
        double totalSalary = managementSystem.calculateTotalSalary();

        Assert.assertEquals(true, totalSalary > 0);

    }

    @Test
    public void calculateAnnualSalary() throws IOException {

        //3466;Luciano Santos;01/02/2023;3.000
        EmployeeManagementSystem managementSystem = new EmployeeManagementSystem();
        managementSystem.loadEmployeesFromFile("employees.txt");

        Optional<Employee> optional = managementSystem.getEmployee(e -> e.getName().equalsIgnoreCase("Luciano Santos"));

        double totalAno = managementSystem.calculateAnnualSalary(optional.get());

        Assert.assertEquals(new Double(36000), new Double(totalAno));


    }

    @Test
    public void findEmployeeWithHighestSalary() throws IOException {

        //0091;Maria Tavares;16/05/2023;5.500

        EmployeeManagementSystem managementSystem = new EmployeeManagementSystem();
        managementSystem.loadEmployeesFromFile("employees.txt");

        Optional<Employee> optional = managementSystem.findEmployeeWithHighestSalary();
        Employee employee = optional.get();

        System.out.println(employee.getName());

        Assert.assertEquals(new Double(5500),new Double(employee.getSalary()));

    }

    @Test
    public void getCurrentDateTimeInTimezone() {

        EmployeeManagementSystem managementSystem = new EmployeeManagementSystem();
        LocalDateTime now = LocalDateTime.now();

        System.out.println(now);

        LocalDateTime nowTimeZone = managementSystem.getCurrentDateTimeInTimezone("America/New_York");
        System.out.println(nowTimeZone);


    }
}
