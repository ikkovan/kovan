package com.ik.kovan.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Embeddable
class PayrollId implements Serializable {
    private Long accountNumber;
    private int payrollType;

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
}



@Entity
@Table
public class Payroll {

    @EmbeddedId
    private PayrollId payrollId;

    private String firstName;

    private String lastName;

    /*
    @OneToMany(mappedBy = "payroll")
    private List<Parameter> parameters;
*/

    public Payroll() {
    }

    public PayrollId getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(Long accountNumber, int payrollType) {
        PayrollId payrollId = new PayrollId();
        payrollId.setAccountNumber(accountNumber);
        payrollId.setPayrollType(payrollType);
        this.payrollId = payrollId;
    }
    public Long getAccountId(){
        return this.getPayrollId().getAccountNumber();
    }

    public int getPayrollType(){
        return  this.payrollId.getPayrollType();
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
/*
    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
        parameters.forEach(entity -> entity.setPayroll(this));
    }
*/
    @Override
    public String toString() {
        return "Payroll{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
