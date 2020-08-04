package com.ik.kovan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table
@Component



public class Command {

    private int type; // 1 income, -1 outcome, 0 ara eleman

    @Id
    @GeneratedValue
    private int commandId;

    @Column(unique=true)
    private String commandName;


    @JsonIgnore
    @OneToMany(mappedBy = "command")
    private List<Variable> variables;
    @JsonIgnore
    @OneToMany(mappedBy = "command")
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Command{" +
                "type=" + type +
                ", commandName='" + commandName + '\'' +
                '}';
    }
}
