package com.ik.kovan.model;

import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Component
public class Command {

    @Id
    @GeneratedValue
    private int commandId;

    private String commandName;

    @OneToMany(mappedBy = "command")
    private List<Variable> variables;

    @OneToMany(cascade= CascadeType.ALL)
    private List<Statement> statements;

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
        statements.forEach(entity -> entity.setCommand(this));

    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
        variables.forEach(entity -> entity.setCommand(this));
    }

    @Override
    public String toString() {
        return "Command{" +
                "commandName='" + commandName + '\'' +
                ", variables=" + variables +
                ", statements=" + statements +
                '}';
    }
}
