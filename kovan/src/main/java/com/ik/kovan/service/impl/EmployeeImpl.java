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
    public Employee findById(long id) {
        return employeeRepository.findById(id);
    }

    //@Override
    //public Iterable<Employee> findAll() {return employeeRepository.findAll();}
    @Override
    public List<Employee> listEmployees() { return employeeRepository.listEmployees(); }

    @Override
    public Employee save(Employee employee) {
        employee.setWorking(true);
        employee.setStartToWork(java.sql.Date.valueOf(java.time.LocalDate.now()));
        return employeeRepository.save(employee);
    }
    @Override
    public void delete(Employee employee) {
        System.out.println("This is employee deletion service");
        employee.setWorking(false);
        employee.setLeaveFromWork(java.sql.Date.valueOf(java.time.LocalDate.now()));
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> listOldEmployees() {
        return employeeRepository.listOldEmployees();
    }
}

