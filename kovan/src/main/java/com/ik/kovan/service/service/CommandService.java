package com.ik.kovan.service.service;

import com.ik.kovan.model.Command;


import java.util.List;

public interface CommandService {

    Command findByCommandId(int id);

    List<Command> listCommands();

    Command save(Command command);

    String getRawCommand(int id);

    void delete(Command command);

}
