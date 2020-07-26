package com.ik.kovan.model;

import org.aspectj.weaver.ast.Var;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Command {
    @Id
    @GeneratedValue
    private int commandId;
    private String rawCommand;

    @OneToMany(mappedBy = "command")
    private List<Variable> variables;

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

}
