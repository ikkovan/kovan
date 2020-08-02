package com.ik.kovan.model;

import javax.persistence.*;

@Entity
@Table

public class Parameter {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parameters")
    private Payroll payroll;

    private String parameterName;

    private String parameterValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Payroll getPayroll() {
        return payroll;
    }

    public void setPayroll(Payroll payroll) {
        this.payroll = payroll;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }
}
