package com.ik.kovan.service.service;

import com.ik.kovan.model.Payroll;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PayrollService {

    List<Payroll> listPayroll();

    Payroll findPayrollByAccountIdAndPayrollType(@Param("id") long accountNumber);

    Payroll save(Payroll payroll);

    void delete(Payroll payroll);

}
