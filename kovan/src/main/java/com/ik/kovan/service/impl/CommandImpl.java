package com.ik.kovan.service.impl;

import com.ik.kovan.logic.CommandCreation;
import com.ik.kovan.model.Command;
import com.ik.kovan.model.Statement;
import com.ik.kovan.model.Variable;
import com.ik.kovan.repository.CommandRepository;
import com.ik.kovan.repository.StatementRepository;
import com.ik.kovan.repository.VariableRepository;
import com.ik.kovan.service.service.CommandService;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommandImpl implements CommandService {

    @Autowired
    CommandRepository commandRepository;

    @Autowired
    VariableRepository variableRepository;

    @Autowired
    StatementRepository statementRepository;


    @Override
    public List<Command> listCommands() {
        return commandRepository.listCommands();
    }

    public List<CommandCreation> listCommandsForAll() {
        List<Command> commands = commandRepository.listCommands();

        List<CommandCreation> commandCreations = new ArrayList<>();
        for (Command command : commands){
            List<Variable> variables = variableRepository.listVariableByCommand(command);
            List<Statement> statements = statementRepository.listStatementbyCommand(command);
            CommandCreation commandCreation = new CommandCreation();
            commandCreation.setCommand(command);
            commandCreation.setVariables(variables);
            commandCreation.setStatements(statements);
            commandCreations.add(commandCreation);

        }

        return commandCreations;
    }

    @Override
    public Command save(Command command) {
        return commandRepository.save(command);
    }

    @Override
    public Command findByCommandName(String commandName) {
        return commandRepository.findByCommandName(commandName);
    }

    @Override
    public void delete(Command command) {
        commandRepository.delete(command);
    }

    @Override
    public void showCommands() {
        List<Command> commands = commandRepository.listCommands();
        for (Command command : commands){
            System.out.println(command);
        }
    }


}
