package com.ik.kovan.service.service;

import com.ik.kovan.model.Employee;

import java.util.List;

/***
 * @author serkantan
 */

public interface EmployeeService {
    Employee findByName(String name);

    //Iterable<Employee> findAll();
    Employee findById(double id);

    List<Employee> listEmployees();

    Employee save(Employee employee);

    void delete(Employee employee);
}
