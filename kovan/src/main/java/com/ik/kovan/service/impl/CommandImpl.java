package com.ik.kovan.service.impl;

import com.ik.kovan.model.Command;
import com.ik.kovan.repository.CommandRepository;
import com.ik.kovan.service.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandImpl implements CommandService {

    @Autowired
    CommandRepository commandRepository;

    @Override
    public Command findByCommandId(int id) {
        return commandRepository.findByCommandId(id);
    }

    @Override
    public List<Command> listCommands() {
        return commandRepository.listCommands();
    }

    @Override
    public Command save(Command command) {
        return commandRepository.save(command);
    }

    @Override
    public String getRawCommand(Command command) {
        return commandRepository.getRawCommand(command);
    }

    @Override
    public void delete(Command command) {
        commandRepository.delete(command);
    }
}
