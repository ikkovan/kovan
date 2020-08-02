package com.ik.kovan.logic;

import com.ik.kovan.model.Command;
import com.ik.kovan.model.Payroll;
import com.ik.kovan.model.Statement;
import com.ik.kovan.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


    CommandGenerator commandGenerator = new CommandGenerator();

    public CommandPayroll(CommandImpl commandService, PayrollImpl payrollService) {
        this.commandService = commandService;
        this.payrollService = payrollService;
    }

    public void runCommands(Long id, int type) { // id : for whom the commands are calculated.
        List<Command> commandList = commandService.listCommands();
        HashMap<String, String> payrollFields = new HashMap<>();
        for (Command c : commandList) {

            List<Statement> statementList = statementService.listStatementbyCommand(c);
            String result = commandGenerator.calculate(statementList); // Şu an için sadece double dönüyor ama bir hashmap döndürmeliyiz.
            payrollFields.put(c.getCommandName(), result);
        }
        try {
            Payroll existedPayroll = payrollService.findPayrollByAccountIdAndPayrollType(id, type);
            if (existedPayroll != null)
                payrollService.delete(existedPayroll);
            Payroll payroll = payrollService.create(employeeService.findById(id), type);
            payroll.setParameters(parameterService.setParams(payrollFields));
            payrollService.save(payroll);


        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("Employee Does Not Exist, Payroll Could Not Be Created");
            return;
        }
        System.out.println("payroll added!");
        //String Command
    }
}
