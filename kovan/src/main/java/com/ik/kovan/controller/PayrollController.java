package com.ik.kovan.controller;

import com.ik.kovan.logic.CommandPayroll;
import com.ik.kovan.model.Payroll;
import com.ik.kovan.service.impl.EmployeeImpl;
import com.ik.kovan.service.impl.PayrollImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ik.kovan.logic.CommandPayroll.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/payroll")
@CrossOrigin(origins = "http://localhost:4200")
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
    public Payroll showPayroll(@PathVariable("id") long accountNumber, @PathVariable("type") int payrollType){
        System.out.println("This is showPayroll Controller.");
        commandPayroll.runCommands(accountNumber, payrollType); // making sure the payroll is initialized and up to date.
        return payrollService.findPayrollByAccountIdAndPayrollType(accountNumber, payrollType);
    }

    @GetMapping("payrolls")
    public List<Payroll> listPayrolls(){
        return payrollService.listPayroll();
    }
}
