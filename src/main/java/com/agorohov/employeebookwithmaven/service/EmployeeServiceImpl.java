package com.agorohov.employeebookwithmaven.service;

import com.agorohov.employeebookwithmaven.exception.EmployeeAlreadyAddedException;
import com.agorohov.employeebookwithmaven.exception.EmployeeNotFoundException;
import com.agorohov.employeebookwithmaven.exception.UnsupportedNameException;
import com.agorohov.employeebookwithmaven.model.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Map<String, Employee> employees;

    public EmployeeServiceImpl() {
        employees = new HashMap<>();
    }

    private String getFullName(Employee employee) {
        return employee.getFirstName() + ' ' + employee.getLastName();
    }

    private String getFullName(String firstName, String lastName) {
        return firstName + ' ' + lastName;
    }

    @Override
    public Collection<Employee> findAllEmployees() {
        Collection<Employee> result = Collections.unmodifiableCollection(employees.values());
        if (result.isEmpty()) {
            throw new EmployeeNotFoundException("Нет сотрудников");
        }
        return result;
    }

    @Override
    public Employee addEmployee(String firstName, String lastName, int salary, int department) {
        String finalFirstName = checkNameCharAndCapitalize(firstName);
        String finalLastName = checkNameCharAndCapitalize(lastName);
        Employee employee = new Employee(finalFirstName, finalLastName, salary, department);
        if (employees.containsKey(getFullName(employee))) {
            throw new EmployeeAlreadyAddedException("Сотрудник с именем " + finalFirstName + " и фамилией " + finalLastName + "уже есть, повторное добавление невозможно");
        }
        employees.put(getFullName(employee), employee);
        return employee;
    }

    @Override
    public Employee removeEmployee(String firstName, String lastName) {
        String finalFirstName = checkNameCharAndCapitalize(firstName);
        String finalLastName = checkNameCharAndCapitalize(lastName);
        Employee employee = Optional.ofNullable(employees.get(getFullName(finalFirstName, finalLastName)))
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Нет сотрудника с именем " + finalFirstName + " и фамилией " + finalLastName));
        employees.remove(getFullName(employee));
        return employee;
    }

    @Override
    public Employee findEmployee(String firstName, String lastName) {
        String finalFirstName = checkNameCharAndCapitalize(firstName);
        String finalLastName = checkNameCharAndCapitalize(lastName);
        return Optional.ofNullable(employees.get(getFullName(finalFirstName, finalLastName)))
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Нет сотрудника с именем " + finalFirstName + " и фамилией " + finalLastName));
    }

    // проверка имени на соответствие с таблицей символов,
    // форматирование - маленькими буквами, первая заглавная
    private static String checkNameCharAndCapitalize(String name) {
        if (!StringUtils.isAlpha(name)) {
            throw new UnsupportedNameException("В имени присутствует неподдерживаемый символ");
        }
        return StringUtils.capitalize(name.toLowerCase());
    }
}