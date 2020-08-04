package com.ik.kovan.logic;

import com.ik.kovan.model.Command;

import com.ik.kovan.model.Statement;
import com.ik.kovan.model.Variable;
import com.ik.kovan.repository.CommandRepository;
import com.ik.kovan.service.impl.CommandImpl;
import com.ik.kovan.service.impl.VariableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandCreation {

    private Command command;

    private List<Variable> variables;

    private List<Statement> statements;

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

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

    public CommandCreation(){

    }

    public CommandCreation(Command command, List<Variable> variables, List<Statement> statements){
            this.command = command;
            this.variables = variables;
            this.statements = statements;
        }

}
