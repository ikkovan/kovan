package com.ik.kovan.repository;

import com.ik.kovan.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @author serkantan
 */

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    //Payroll findByAccountNumberAAndPayrollType(long accountNumber);

    @Query(value = "select p from Payroll p")
    List<Payroll> listPayrolls();

    @Query(value = "select p from  Payroll p where p.accountNumber= :id and p.payrollType= :pType")
    Payroll findPayrollByAccountIdAndPayrollType(@Param("id") long accountNumber,
                                                 @Param("pType") int payrollType);

    Payroll save(Payroll payroll);


    void delete(Payroll payroll);

}
