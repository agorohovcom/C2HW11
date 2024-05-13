package com.agorohov.employeebookwithmaven.controller;

import com.agorohov.employeebookwithmaven.model.Employee;
import com.agorohov.employeebookwithmaven.service.DepartmentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/department", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/all")
    public Map<Integer, List<Employee>> findAllEmployees() {
        return departmentService.findAllEmployees();
    }

    @GetMapping(value = "/all", params = "departmentId")
    public Collection<Employee> findAllEmployees(@RequestParam int departmentId) {
        return departmentService.findAllEmployees(departmentId);
    }

    @GetMapping(value = "/max-salary")
    public Employee getEmployeeWithMaxSalary(@RequestParam int departmentId) {
        return departmentService.getEmployeeWithMaxSalary(departmentId);
    }

    @GetMapping(value = "/min-salary")
    public Employee getEmployeeWithMinSalary(@RequestParam int departmentId) {
        return departmentService.getEmployeeWithMinSalary(departmentId);
    }

    @GetMapping(value = "/average-salary")
    public String getAverageSalary(@RequestParam int departmentId) {
        return "Средняя месячная ЗП среди всех сотрудников из отдела " + departmentId + ": "
                + departmentService.getAverageMonthSalary(departmentId);
    }

    @GetMapping(value = "/sum-salary")
    public String getSumSalary(@RequestParam int departmentId) {
        return "Сумма зарплат всех сотрудников отдела " + departmentId + " за месяц составляет "
                + departmentService.getSumMonthSalaries(departmentId);
    }
}
