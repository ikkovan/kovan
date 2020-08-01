package com.ik.kovan.service.service;

import com.ik.kovan.model.Command;
import com.ik.kovan.model.Variable;


import java.util.List;

public interface CommandService {

    Command findByCommandName(String commandName);

    List<Command> listCommands();

    Command save(Command command);

    void delete(Command command);

    void showCommands();



}
