package com.ik.kovan.model;

import javax.persistence.*;

@Entity
@Table
public class Variable {

    @Id
    private int variableId;

    private String locatedTable;
    private String locatedColumn;

    @ManyToOne
    @JoinColumn(name = "command_vars")
    private Command command;

    public int getVariableId() {
        return variableId;
    }

    public void setVariableId(int variableId) {
        this.variableId = variableId;
    }

    public String getLocatedTable() {
        return locatedTable;
    }

    public void setLocatedTable(String locatedTable) {
        this.locatedTable = locatedTable;
    }

    public String getLocatedColumn() {
        return locatedColumn;
    }

    public void setLocatedColumn(String locatedColumn) {
        this.locatedColumn = locatedColumn;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "variableId=" + variableId +
                ", locatedTable='" + locatedTable + '\'' +
                ", locatedColumn='" + locatedColumn + '\'' +
                ", command=" + command +
                '}';
    }
}
