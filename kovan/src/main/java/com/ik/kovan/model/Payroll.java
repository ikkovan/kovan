package com.ik.kovan.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

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

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy="payroll")
    private List<Parameter> parameters;

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

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
        parameters.forEach(entity -> entity.setPayroll(this));
    }
}
