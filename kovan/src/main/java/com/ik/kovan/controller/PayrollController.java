package com.ik.kovan.controller;

import com.ik.kovan.logic.CommandPayroll;
import com.ik.kovan.logic.PayrollCreation;
import com.ik.kovan.model.Payroll;
import com.ik.kovan.service.impl.EmployeeImpl;
import com.ik.kovan.service.impl.PayrollImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ik.kovan.logic.CommandPayroll.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://35.222.205.42")
@RequestMapping("/payroll")
public class PayrollController {

    @Autowired
    private final PayrollImpl payrollService;

    @Autowired
    private final CommandPayroll commandPayroll;

    @Autowired
    EmployeeImpl employeeImpl;

    public PayrollController(PayrollImpl payrollService, CommandPayroll commandPayroll) {
        this.payrollService = payrollService;
        this.commandPayroll = commandPayroll;
    }


    @GetMapping("/show/{type}/{id}")
    @CrossOrigin(origins = "http://35.222.205.42")

    public Payroll showPayroll(@PathVariable("id") long accountNumber, @PathVariable("type") int payrollType){
        System.out.println("This is showPayroll Controller.");
        PayrollCreation payrollCreation = commandPayroll.runCommands(accountNumber, payrollType); // making sure the payroll is initialized and up to date.
        return payrollService.findPayrollByAccountIdAndPayrollType(accountNumber, payrollType);
    }

    @GetMapping("/show/new/{type}/{id}")
    @CrossOrigin(origins = "http://35.222.205.42")

    public PayrollCreation showPayrollUpdated (@PathVariable("id") long accountNumber, @PathVariable("type") int payrollType){
        System.out.println("This is showPayroll Controller.");
        PayrollCreation payrollCreationUpdated = commandPayroll.runCommands(accountNumber, payrollType); // making sure the payroll is initialized and up to date.
        return payrollCreationUpdated;
    }

    @GetMapping("payrolls")
    @CrossOrigin(origins = "http://35.222.205.42")

    public List<PayrollCreation> listPayrolls(){
        return payrollService.listPayroll();
    }
}
