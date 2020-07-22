package com.ik.kovan.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;


class PayrollId implements Serializable {
    private Long accountNumber;
    private int payrollType;

    // default constructor

    public PayrollId(Long accountNumber, int payrollType) {
        this.accountNumber = accountNumber;
        this.payrollType = payrollType;
    }

    // equals() and hashCode()
}



@Entity
@IdClass(PayrollId.class)
public class Payroll {
    @Id
    private Long accountNumber;

    @Id
    private Long payrollType;

    private double AGIValue;

    private String firstName;

    private String lastName;

    // other fields


    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getPayrollType() {
        return payrollType;
    }

    public void setPayrollType(Long payrollType) {
        this.payrollType = payrollType;
    }

    public double getAGIValue() {
        return AGIValue;
    }

    public void setAGIValue(double AGIValue) {
        this.AGIValue = AGIValue;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
