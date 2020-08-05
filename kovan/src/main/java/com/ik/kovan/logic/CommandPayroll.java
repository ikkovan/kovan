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
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue(String tableColumn){
        return variableService.showValueInterpreter(tableColumn, this.getId());
    }



    public PayrollCreation runCommands(Long id, int type) { // id : for whom the commands are calculated.
        this.setId(id);
        List<Command> commandList = commandService.listCommands();
        HashMap<String, String> payrollFields = new HashMap<>();
        for (Command c : commandList) {

            List<Statement> statementList = statementService.listStatementbyCommand(c);
            List<String> statementListString = new ArrayList<>();

            List<List<String>> variableList = variableService.tableColumn(c.getVariables());
            int index = 0;
            for (Statement statement : statementList){
                String[] body = statement.getLine().split(":");
                if (body[0].equals("PARAM")){
                    String newStatement = body[1] + " " + variableService.showValue(variableList.get(index), id);
                    index ++;
                    statementListString.add(newStatement);

                }
                else
                {
                    statementListString.add(statement.getLine());
                }
            }
            List<String> results = new ArrayList<>();
            for (List<String> variable : variableList){
                results.add(variableService.showValue(variable, id));
            }
            System.out.println("This is results!");
            System.out.println(results);

            String result = commandGenerator.calculate(statementListString); // Şu an için sadece double dönüyor ama bir hashmap döndürmeliyiz.
            if (result == "")
                result = "0";
            payrollFields.put(c.getCommandName(), result);
        }
        payrollFields.put("Salary", "2943");
        String netSalary = computeNetSalary(payrollFields);
        payrollFields.put("Net Salary", netSalary);



        Payroll existedPayroll = payrollService.findPayrollByAccountIdAndPayrollType(id, type);
        if (existedPayroll != null){
            payrollService.delete(existedPayroll);
            parameterService.delete(existedPayroll.getAccountId(), existedPayroll.getPayrollType());
        }
        Payroll payroll = payrollService.create(employeeService.findById(id), type);
        List<Parameter> parameters = parameterService.setParams(payrollFields, payroll.getAccountId(), payroll.getPayrollType());
        payrollService.save(payroll);

        System.out.println("payroll added!");
        PayrollCreation payrollCreation = new PayrollCreation();
        payrollCreation.setPayroll(payroll);
        payrollCreation.setParameterList(parameters);

        return payrollCreation;
    }

    public String computeNetSalary(HashMap<String, String> payrollFields){
        String netSalary;
        String grossSalary;

        double grossSalaryValue = 0;
        double currValue = 0;

        for (String key : payrollFields.keySet()){
            if (key.equals("Salary")){
                grossSalary = payrollFields.get(key);
                grossSalaryValue = Double.parseDouble(grossSalary);
            }
            else{
                double value = Double.parseDouble(payrollFields.get(key));
                int type;
                try{
                    type =  commandService.findCommandTypeByUniqueName(key);
                }
                catch (Exception e){
                    System.out.println("Type not found!");
                    type = 1;
                }
                currValue += value * type;
            }
        }

        netSalary = String.valueOf(grossSalaryValue + currValue);
        return netSalary;

    }
}
