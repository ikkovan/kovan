package com.ik.kovan.service.service;

import com.ik.kovan.model.Employee;
import com.ik.kovan.model.PayrollSchema;

import java.util.List;

/***
 * @author serkantan
 */

public interface PayrollSchemaService {
    PayrollSchema findBySchemaId(int schemaId);

    List<PayrollSchema> listPayrollSchema();

    PayrollSchema save(PayrollSchema payrollSchema);

}
