package com.ik.kovan.controller;

import com.ik.kovan.logic.CommandGenerator;
import com.ik.kovan.model.Command;
import com.ik.kovan.model.Employee;
import com.ik.kovan.repository.CommandRepository;
import com.ik.kovan.service.impl.CommandImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CommandController {
    @Autowired
    private final CommandImpl commandService;
    public CommandController(CommandImpl commandService) {
        this.commandService = commandService;
    }


    @PostMapping("/addCommand")
    @CrossOrigin(origins = "http://localhost:4200")
    public Command addCommand(@Valid @RequestBody Command command){
        System.out.println("This is Command Registration Controller.");
        return commandService.save(command);
    }

    @DeleteMapping(value="/deleteCommand/{id}")
    @CrossOrigin(origins = "http://localhost:4200")

    public Command deleteCommand(@PathVariable("id") int id){
        System.out.println("This is deleteCommand Controller.");
        Command tempCommand = commandService.findByCommandId(id);
        commandService.delete(commandService.findByCommandId(id));
        return tempCommand;
    }

    @GetMapping("/commands")
    @CrossOrigin(origins = "http://localhost:4200")

    public List<Command> listCommands(Model model){
        System.out.println("This is listCommands Controller.");
        return commandService.listCommands();
    }





}

