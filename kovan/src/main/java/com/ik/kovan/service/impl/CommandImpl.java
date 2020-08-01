package com.ik.kovan.service.impl;

import com.ik.kovan.model.Command;
import com.ik.kovan.model.Variable;
import com.ik.kovan.repository.CommandRepository;
import com.ik.kovan.service.service.CommandService;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandImpl implements CommandService {

    @Autowired
    CommandRepository commandRepository;

    @Override
    public List<Command> listCommands() {
        return commandRepository.listCommands();
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
