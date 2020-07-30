package com.ik.kovan.repository;


import com.ik.kovan.model.Variable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariableRepository extends JpaRepository<Variable, Long> {


}
