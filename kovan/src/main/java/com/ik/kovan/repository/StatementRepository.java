package com.ik.kovan.repository;

import com.ik.kovan.model.Command;
import com.ik.kovan.model.Employee;
import com.ik.kovan.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {


    Statement findById(int id);


    @Query(value = "select p from Statement p")
    List<Statement> listStatement();

    @Query(value = "select p from Statement p where p.command = :command")
    List<Statement> listStatementbyCommand(@Param("command") Command command);


    Statement save(Statement statement);

    void delete(Statement statement);


}
