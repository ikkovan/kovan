package com.ik.kovan.logic;

import com.ik.kovan.model.*;
import com.ik.kovan.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class CommandPayroll {

    @Autowired
    CommandImpl commandService;

    @Autowired
    PayrollImpl payrollService;

    @Autowired
    ParameterImpl parameterService;

    @Autowired
    EmployeeImpl employeeService;

    @Autowired
    StatementImpl statementService;

    @Autowired
    VariableImpl variableService;


    CommandGenerator commandGenerator = new CommandGenerator();

    public CommandPayroll(CommandImpl commandService, PayrollImpl payrollService) {
        this.commandService = commandService;
        this.payrollService = payrollService;
    }

    public PayrollCreation runCommands(Long id, int type) { // id : for whom the commands are calculated.
        List<Command> commandList = commandService.listCommands();
        HashMap<String, String> payrollFields = new HashMap<>();
        for (Command c : commandList) {

            List<Statement> statementList = statementService.listStatementbyCommand(c);
            List<List<String>> variableList = variableService.tableColumn(c.getVariables());
            List<String> results = new ArrayList<>();
            for (List<String> variable : variableList){
                results.add(variableService.showValue(variable));
            }
            System.out.println(results);

            String result = commandGenerator.calculate(statementList); // Şu an için sadece double dönüyor ama bir hashmap döndürmeliyiz.
            if (result == "")
                result = "0";
            payrollFields.put(c.getCommandName(), result);
        }
        payrollFields.put("Salary", "3000");


        Payroll existedPayroll = payrollService.findPayrollByAccountIdAndPayrollType(id, type);
        if (existedPayroll != null)
            payrollService.delete(existedPayroll);
        Payroll payroll = payrollService.create(employeeService.findById(id), type);
        List<Parameter> parameters = parameterService.setParams(payrollFields, payroll.getAccountId(), payroll.getPayrollType());
        payrollService.save(payroll);

        System.out.println("payroll added!");
        PayrollCreation payrollCreation = new PayrollCreation();
        payrollCreation.setPayroll(payroll);
        payrollCreation.setParameterList(parameters);

        return payrollCreation;
    }
}
