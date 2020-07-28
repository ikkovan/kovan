package com.ik.kovan.model;


import javax.persistence.*;

@Entity
@Table
public class Statement {

    /* Algoritma kısmı için ayrı bir tablo açma sebebim burada yazan alanlardan daha fazla şey içerebileceğinden.*/

    @Id
    @GeneratedValue
    private int id;

    private String line;

    @ManyToOne
    @JoinColumn(name = "statement_id")
    private Command command;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
