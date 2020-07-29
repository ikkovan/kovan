package com.ik.kovan.logic;

import com.ik.kovan.model.Command;

import com.ik.kovan.model.Variable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandCreation {

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

    public void CommandCreationRequest(Command command, List<Variable> variables){
            this.command = command;
            this.variables = variables;
        }

}
