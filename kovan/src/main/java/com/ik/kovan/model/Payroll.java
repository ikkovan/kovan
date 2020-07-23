package com.ik.kovan.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
class PayrollId implements Serializable {
    private Long accountNumber;
    private int payrollType;

    // default constructor

    // equals() and hashCode()
}



@Entity
@Table
@IdClass(PayrollId.class)
public class Payroll {
    @Id
    private Long accountNumber;

    @Id
    private int payrollType;

    private double AGIValue;

    private String firstName;

    private String lastName;

    // other fields


    public Payroll() {
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getPayrollType() {
        return payrollType;
    }

    public void setPayrollType(int payrollType) {
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
