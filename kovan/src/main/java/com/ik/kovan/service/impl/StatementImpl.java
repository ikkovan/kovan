package com.ik.kovan.service.impl;

import com.ik.kovan.model.Command;
import com.ik.kovan.model.Employee;
import com.ik.kovan.model.Statement;
import com.ik.kovan.repository.StatementRepository;
import com.ik.kovan.service.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatementImpl implements StatementService {

    @Autowired
    StatementRepository statementRepository;

    @Override
    public Statement findById(int id) {
        return statementRepository.findById(id);
    }

    @Override
    public List<Statement> listStatement() {
        return statementRepository.listStatement();
    }

    @Override
    public Statement save(Statement statement) {
        return statementRepository.save(statement);
    }

    @Override
    public void saveAll(List<Statement> statements) {
        statementRepository.saveAll(statements);
    }

    @Override
    public void delete(Statement statement) {
        statementRepository.delete(statement);
    }

    @Override
    public List<Statement> listStatementbyCommand(Command command) {
        return statementRepository.listStatementbyCommand(command);
    }
}
