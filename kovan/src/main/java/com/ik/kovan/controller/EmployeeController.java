package com.ik.kovan.controller;


import com.ik.kovan.model.Employee;
import com.ik.kovan.service.impl.EmployeeImpl;
import com.ik.kovan.service.impl.VariableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.Valid;
/***
 * @author serkantan
 */

@RestController
@CrossOrigin(origins = "http://35.222.205.42")
public class EmployeeController {
    @Autowired
    private final EmployeeImpl employeeService;

    public EmployeeController(EmployeeImpl employeeService ) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> showEmployees(Model model){
        System.out.println("This is showEmployees Controller.");
        return employeeService.listEmployees();
    }

    @GetMapping("/old_employees")
    public List<Employee> showOldEmployees(Model model){
        System.out.println("This is showOldEmployees Controller.");
        return employeeService.listOldEmployees();
    }

    @GetMapping("/details/{id}")
    public Employee getEmployeeById(@PathVariable("id") Long id){
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
        System.out.println(employee.getFirstName());
        return employeeService.save(employee);
    }

    @DeleteMapping(value="/delete/{id}")
    public Employee deleteEmployee(@PathVariable("id") Long id) {
        System.out.println("This is deleteEmployee Controller.");
        Employee tempEmployee = employeeService.findById(id);
        employeeService.delete(employeeService.findById(id));
        return tempEmployee;

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") int id,
         @Valid @RequestBody Employee employeeDetails) {
    	System.out.println("This is updateEmployee Controller.");
        Employee employee = employeeService.findById(id);

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setCountChildren(employeeDetails.getCountChildren());
        employee.setIsMarried(employeeDetails.getIsMarried());
        employee.setIsSpouseWorking(employeeDetails.getIsSpouseWorking());
        employee.setSalaryTemplate(employeeDetails.getSalaryTemplate());
        employee.setTaxBands(employeeDetails.getTaxBands());
        final Employee updatedEmployee = employeeService.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }


}
