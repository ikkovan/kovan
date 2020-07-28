package com.ik.kovan.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Procedure {

    /* Algoritma kısmı için ayrı bir tablo açma sebebim burada yazan alanlardan daha fazla şey içerebileceğinden.*/

    @Id
    @GeneratedValue
    private int id;

    private String statement;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
