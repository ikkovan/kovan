package com.ik.kovan.repository;


import com.ik.kovan.model.Employee;
import com.ik.kovan.model.Variable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VariableRepository extends JpaRepository<Variable, Long> {
    @Query(value = "select v from Variable v ")
    List<Variable> listVariable();

}
