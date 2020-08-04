package com.ik.kovan.repository;

import com.ik.kovan.model.Parameter;
import com.ik.kovan.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    @Query(value = "select par from Parameter par where employeeId=:id and payrollType=:type")
    List<Parameter> listParameter(@Param("id") Long id, @Param("type") int type);

}
