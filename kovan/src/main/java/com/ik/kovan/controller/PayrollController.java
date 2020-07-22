package com.ik.kovan.controller;

import com.ik.kovan.logic.CommandPayroll;
import com.ik.kovan.model.Payroll;
import com.ik.kovan.service.impl.PayrollImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ik.kovan.logic.CommandPayroll.*;

@RestController
@RequestMapping("/payroll")
@CrossOrigin(origins = "http://localhost:4200")
public class PayrollController {

    @Autowired
    private final PayrollImpl payrollService;

    @Autowired
    private final CommandPayroll commandPayroll;

    public PayrollController(PayrollImpl payrollService, CommandPayroll commandPayroll) {
        this.payrollService = payrollService;
        this.commandPayroll = commandPayroll;
    }


    @GetMapping("/show")
    public Payroll showPayroll(long accountNumber, int payrollType){
        commandPayroll.runCommands(accountNumber); // making sure the payroll is initialized and up to date.
        return payrollService.findPayrollByAccountIdAndPayrollType(accountNumber, payrollType);
    }
}
