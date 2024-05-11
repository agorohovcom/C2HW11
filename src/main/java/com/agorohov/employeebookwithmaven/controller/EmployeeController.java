package com.agorohov.employeebookwithmaven.controller;

import com.agorohov.employeebookwithmaven.exception.EmployeeAlreadyAddedException;
import com.agorohov.employeebookwithmaven.exception.EmployeeNotFoundException;
import com.agorohov.employeebookwithmaven.exception.UnsupportedNameException;
import com.agorohov.employeebookwithmaven.model.Employee;
import com.agorohov.employeebookwithmaven.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/add")
    public Employee addEmployee(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam int salary,
                                @RequestParam int department) {
        return employeeService.addEmployee(firstName, lastName, salary, department);
    }

    @GetMapping("/remove")
    public Employee removeEmployee(@RequestParam String firstName,
                                   @RequestParam String lastName) {
        return employeeService.removeEmployee(firstName, lastName);
    }

    @GetMapping("/find")
    public Employee findEmployee(@RequestParam String firstName,
                                 @RequestParam String lastName) {
        return employeeService.findEmployee(firstName, lastName);
    }

    // Перехват указанных исключений с целью вывода в браузер сообщений из исключений
    // Перекрывает @ResponseStatus
    @ExceptionHandler({
            EmployeeAlreadyAddedException.class,
            EmployeeNotFoundException.class,
            UnsupportedNameException.class
    })
    public String handleEmployeeExceptions(RuntimeException e) {
        e.printStackTrace();
        return e.getMessage();
    }
}