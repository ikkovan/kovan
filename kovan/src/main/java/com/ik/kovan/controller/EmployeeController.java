package com.ik.kovan.controller;


import com.ik.kovan.model.Employee;
import com.ik.kovan.service.impl.EmployeeImpl;
import com.ik.kovan.service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.Valid;
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
    public String getEmployee(Model model){
        model.addAttribute("employee", new Employee());
        return "registerUser";
    }

    @PostMapping("/register")
    public String addEmployee(@Valid @ModelAttribute("employee")Employee employee, BindingResult result, Model model){
        System.out.println("This is Employee Registration Controller.");
        if (result.hasErrors())
        {
            System.out.println("Post method has some errors!");
            System.out.println(result);
            return "registerUser";
        }

        else
        {
            employeeService.save(employee);
            return "registerUser";
        }

    }


    @PostMapping(value = "/mock_registration")
    public ResponseEntity<?> fakeSaveEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
        return new ResponseEntity("Employee added successfully", HttpStatus.OK);
    }
}
