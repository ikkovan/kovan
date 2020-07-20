package com.ik.kovan.controller;


import com.ik.kovan.model.Employee;
import com.ik.kovan.service.impl.EmployeeImpl;
import com.ik.kovan.service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/***
 * @author serkantan
 */

@Controller
public class EmployeeController {
    @Autowired
    private final EmployeeImpl employeeService;


    public EmployeeController(EmployeeImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    @CrossOrigin(origins = "localhost:4200")
    public String showEmployees(Model model){
        model.addAttribute("employees", employeeService.listEmployees());
        System.out.println(model.getAttribute("id"));
        System.out.println(model.getAttribute("identityNumber"));
        System.out.println(model.getAttribute("firstName"));
        System.out.println(model.getAttribute("lastName"));
        System.out.println(model.getAttribute("isMarried"));
        System.out.println(model);

        System.out.println("Success!");

        return "employees";
    }


    @GetMapping("/register")
    public String employeeForm(){
        //model.addAttribute("greeting", new Employee());
        return "registerUser.html";
    }


    @PostMapping(value = "/mock_registration")
    public ResponseEntity<?> fakeSaveEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
        return new ResponseEntity("Employee added successfully", HttpStatus.OK);
    }
}
