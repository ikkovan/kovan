package com.ik.kovan.service.impl;

import com.ik.kovan.logic.PayrollCreation;
import com.ik.kovan.model.Employee;
import com.ik.kovan.model.Parameter;
import com.ik.kovan.model.Payroll;
import com.ik.kovan.repository.ParameterRepository;
import com.ik.kovan.repository.PayrollRepository;
import com.ik.kovan.service.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PayrollImpl implements PayrollService {

    @Autowired
    PayrollRepository payrollRepository;

    @Autowired
    ParameterRepository parameterRepository;

    @Override
    public List<PayrollCreation> listPayroll() {
        List<PayrollCreation> payrollCreations = new ArrayList<>();
        List<Payroll> payrolls = payrollRepository.listPayrolls();
        for (Payroll payroll : payrolls){
            List<Parameter> parameters =  parameterRepository.listParameter(payroll.getAccountId(), payroll.getPayrollType());
            PayrollCreation payrollCreation = new PayrollCreation();
            payrollCreation.setPayroll(payroll);
            payrollCreation.setParameterList(parameters);

            payrollCreations.add(payrollCreation);
        }
        return payrollCreations;


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

    public Payroll create(Employee employee, int payrollType){ // This will be hashmap
        System.out.println("This is Payroll Creation method.");
        Payroll payroll = new Payroll();

        payroll.setPayrollId(employee.getId(), payrollType);

        payroll.setFirstName(employee.getFirstName());
        payroll.setLastName(employee.getLastName());
        return payroll;

    }

}
