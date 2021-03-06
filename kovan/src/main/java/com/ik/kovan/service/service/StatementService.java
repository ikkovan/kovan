package com.ik.kovan.service.service;

import com.ik.kovan.model.Command;
import com.ik.kovan.model.Employee;
import com.ik.kovan.model.Statement;


import java.util.List;

public interface StatementService {

    Statement findById(int id);

    List<Statement> listStatement();

    Statement save(Statement statement);

    void delete(Statement statement);

    void saveAll(List<Statement> statements);

    List<Statement> listStatementbyCommand(Command command);

}
