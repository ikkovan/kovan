package com.ik.kovan.service.impl;

import com.ik.kovan.model.PayrollSchema;
import com.ik.kovan.repository.PayrollSchemaRepository;
import com.ik.kovan.service.service.PayrollSchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollSchemaImpl implements PayrollSchemaService {

    @Autowired
    PayrollSchemaRepository payrollSchemaRepository;


    @Override
    public PayrollSchema findBySchemaId(int schemaId) {
        return payrollSchemaRepository.findBySchemaId(schemaId);
    }

    @Override
    public List<PayrollSchema> listPayrollSchema() {
        return payrollSchemaRepository.listPayrollSchemas();
    }

    @Override
    public PayrollSchema save(PayrollSchema payrollSchema) {
        return payrollSchemaRepository.save(payrollSchema);
    }
}
