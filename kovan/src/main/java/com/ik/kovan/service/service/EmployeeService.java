package com.ik.kovan.service.service;

import com.ik.kovan.model.Employee;

import java.util.List;

/***
 * @author serkantan
 */

public interface EmployeeService {
    Employee findByName(String name);

    //Iterable<Employee> findAll();
    Employee findById(long id);

    List<Employee> listEmployees();

    List<Employee> listOldEmployees();


    Employee save(Employee employee);

    void delete(Employee employee);
}
