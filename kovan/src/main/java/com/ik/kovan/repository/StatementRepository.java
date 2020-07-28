package com.ik.kovan.repository;

import com.ik.kovan.model.Employee;
import com.ik.kovan.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {


    Statement findById(int id);


    @Query(value = "select p from Statement p")
    List<Employee> listStatement();


    Statement save(Statement statement);

    void delete(Statement statement);
}
