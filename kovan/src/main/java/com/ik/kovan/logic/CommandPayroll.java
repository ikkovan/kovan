package com.ik.kovan.logic;

import com.ik.kovan.model.Command;
import com.ik.kovan.service.impl.CommandImpl;
import com.ik.kovan.service.impl.EmployeeImpl;
import com.ik.kovan.service.impl.PayrollImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandPayroll {

    @Autowired
    CommandImpl commandService;

    @Autowired
    PayrollImpl payrollService;

    @Autowired
    EmployeeImpl employeeService;

    CommandGenerator commandGenerator = new CommandGenerator();

    public CommandPayroll(CommandImpl commandService, PayrollImpl payrollService) {
        this.commandService = commandService;
        this.payrollService = payrollService;
    }

    public void runCommands(Long id) { // id : for whom the commands are calculated.
        List<Command> commandList = commandService.listCommands();
        for (Command c : commandList) {
            System.out.println(c.getRawCommand());
            commandGenerator.calculate(c.getRawCommand()); // Şu an için sadece double dönüyor ama bir hashmap döndürmeliyiz.
            payrollService.create(employeeService.findById(id), commandGenerator.getResult());
            System.out.println("payroll added!");
        }
        //String Command
    }

}
