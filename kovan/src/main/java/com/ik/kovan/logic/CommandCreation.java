package com.ik.kovan.logic;

import com.ik.kovan.model.Command;

import com.ik.kovan.model.Variable;
import com.ik.kovan.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandCreation {

    @Autowired
    CommandRepository commandRepository;

    private Command command;
    private List<Variable> variables;

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public void save(CommandCreation commandCreation){
        commandCreation.getCommand().setVariables(variables);
        System.out.println(commandCreation.getCommand());
        commandRepository.save(command);
    }

    public void CommandCreationRequest(Command command, List<Variable> variables){
            this.command = command;
            this.variables = variables;
        }

}
