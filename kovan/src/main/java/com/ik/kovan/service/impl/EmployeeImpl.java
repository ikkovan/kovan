package com.ik.kovan.service.impl;


import com.ik.kovan.model.Employee;
import com.ik.kovan.repository.EmployeeRepository;
import com.ik.kovan.service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @author serkantan
 */

@Service
public class EmployeeImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee findByName(String name) {
        return employeeRepository.findByFirstName(name);
    }

    @Override
    public Employee findById(double id) {
        return employeeRepository.findById(id);
    }

    //@Override
    //public Iterable<Employee> findAll() {return employeeRepository.findAll();}
    @Override
    public List<Employee> listEmployees() { return employeeRepository.listEmployees(); }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
}

