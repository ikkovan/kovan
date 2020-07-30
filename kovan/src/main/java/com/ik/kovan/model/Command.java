package com.ik.kovan.model;

import org.aspectj.weaver.ast.Var;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Command {
    @Id
    @GeneratedValue
    private int commandId;
    private String rawCommand;

    @OneToMany(mappedBy = "command")
    private List<Variable> variables;

    @OneToMany(cascade= CascadeType.ALL)
    private List<Statement> statements;

    public int getCommandId() {
        return commandId;
    }

    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }

    public String getRawCommand() {
        return rawCommand;
    }

    public void setRawCommand(String rawCommand) {
        this.rawCommand = rawCommand;
    }


    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
        for (Variable var : variables){
            var.setCommand(this);
        }
    }

    @Override
    public String toString() {
        return "Command{" +
                "commandId=" + commandId +
                ", rawCommand='" + rawCommand + '\'' +
                ", variables=" + variables +
                ", statements=" + statements +
                '}';
    }
}
