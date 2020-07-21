package com.ik.kovan.controller;


import com.ik.kovan.model.Employee;
import com.ik.kovan.service.impl.EmployeeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.Valid;
/***
 * @author serkantan
 */

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
    @Autowired
    private final EmployeeImpl employeeService;


    public EmployeeController(EmployeeImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    @CrossOrigin(origins = "localhost:4200")
    public List<Employee> showEmployees(Model model){
        System.out.println("This is showEmployees Controller.");
        return employeeService.listEmployees();
    }
    @GetMapping("/details/{id}")
    public Employee getEmployeeById(@PathVariable("id") double id){
       System.out.println("Employee received by its id.");
        return employeeService.findById(id);
    }

    @GetMapping("/add")
    public String getEmployee(Model model){
        model.addAttribute("employee", new Employee());
        return "registerUser";
    }

    @PostMapping("/add")
    public Employee addEmployee(@Valid @RequestBody Employee employee){
        System.out.println("This is Employee Registration Controller.");
        return employeeService.save(employee);
    }

    @DeleteMapping(value="/delete/{id}")
    public Employee deleteEmployee(@PathVariable("id") double id) {
        System.out.println("This is deleteEmployee Controller.");
        Employee tempEmployee = employeeService.findById(id);
        employeeService.delete(employeeService.findById(id));
        return tempEmployee;

    }


}
