package com.ik.kovan.repository;

import com.ik.kovan.model.Employee;
import com.ik.kovan.model.PayrollSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @author serkantan
 */

@Repository
public interface PayrollSchemaRepository extends JpaRepository<PayrollSchema, Long> {

    @Query(value = "select p from PayrollSchema p")
    List<PayrollSchema> listPayrollSchemas();

    PayrollSchema findBySchemaId(int schemaId);

    PayrollSchema save(PayrollSchema payrollSchema);

}
