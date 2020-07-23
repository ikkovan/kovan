package com.ik.kovan.service.impl;

import com.ik.kovan.model.Employee;
import com.ik.kovan.model.Payroll;
import com.ik.kovan.repository.PayrollRepository;
import com.ik.kovan.service.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PayrollImpl implements PayrollService {

    @Autowired
    PayrollRepository payrollRepository;


    @Override
    public List<Payroll> listPayroll() {
        return payrollRepository.listPayrolls();
    }

    @Override
    public Payroll findPayrollByAccountIdAndPayrollType(long accountNumber, int type) {
        return payrollRepository.findPayrollByAccountIdAndPayrollType(accountNumber, type);
    }

    @Override
    public Payroll save(Payroll payroll) {
        return payrollRepository.save(payroll);
    }

    @Override
    public void delete(Payroll payroll) {
        payrollRepository.delete(payroll);
    }

    public Payroll create(Employee employee, int payrollType, double result){ // This will be hashmap
        System.out.println("This is Payroll Creation method.");
        Payroll payroll = new Payroll();

        payroll.setAGIValue(result);
        payroll.setAccountNumber(employee.getId());
        payroll.setPayrollType(payrollType);
        payroll.setFirstName(employee.getFirstName());
        payroll.setLastName(employee.getLastName());
        return save(payroll);

    }

}
