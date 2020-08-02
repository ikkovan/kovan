package com.ik.kovan.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table
@Component
public class Parameter {

    @Id
    @GeneratedValue
    private int id;

    private String parameterName;

    private String parameterValue;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumns({@JoinColumn(name = "payrollId"),
            @JoinColumn(name= "payrollType")})
    private Payroll payroll;

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

    @Override
    public String toString() {
        return "Parameter{" +
                "id=" + id +
                ", parameterName='" + parameterName + '\'' +
                ", parameterValue='" + parameterValue + '\'' +
                '}';
    }
}
