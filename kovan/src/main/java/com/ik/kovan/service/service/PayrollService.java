package com.ik.kovan.service.service;

import com.ik.kovan.logic.PayrollCreation;
import com.ik.kovan.model.Payroll;

import java.util.List;

public interface PayrollService {

    List<PayrollCreation> listPayroll();

    Payroll findPayrollByAccountIdAndPayrollType(long accountNumber, int type);

    Payroll save(Payroll payroll);

    void delete(Payroll payroll);

}
