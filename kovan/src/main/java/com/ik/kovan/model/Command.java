package com.ik.kovan.model;

import javax.persistence.*;
import java.util.List;

@Entity
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

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }
}
