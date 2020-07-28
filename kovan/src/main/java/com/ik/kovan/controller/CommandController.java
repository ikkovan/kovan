package com.ik.kovan.controller;

import com.ik.kovan.model.Command;
import com.ik.kovan.model.Procedure;
import com.ik.kovan.model.Variable;
import com.ik.kovan.service.impl.CommandImpl;
import com.ik.kovan.service.impl.VariableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CommandController {
    @Autowired
    private final CommandImpl commandService;

    @Autowired
    private final VariableImpl variableService;

    public CommandController(CommandImpl commandService, VariableImpl variableService) {
        this.commandService = commandService;
        this.variableService = variableService;
    }

    @PostMapping("/addCommand")
    @CrossOrigin(origins = "http://localhost:4200")
    public Command addCommand(@Valid @RequestBody Command command){
        System.out.println("This is Command Registration Controller.");
        return commandService.save(command);
    }


    
    @GetMapping("/ruleDetails/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    
    public Command getCommandById(@PathVariable("id") int id){
       System.out.println("Rule received by its id.");
        return commandService.findByCommandId(id);
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
    
    @PutMapping("/ruleUpdate/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    
    public ResponseEntity<Command> updateCommand(@PathVariable(value = "id") int id,
         @Valid @RequestBody Command commandDetail) {
    	System.out.println("This is updateCommand Controller.");
        Command command = commandService.findByCommandId(id);
        
        command.setRawCommand(commandDetail.getRawCommand());
       
        final Command updatedCommand = commandService.save(command);
        return ResponseEntity.ok(updatedCommand);
    }

    @GetMapping("/getParameters")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<String> getParameters(){
        return variableService.showTablesAndColumns();
    }


    @PostMapping(value="/addParameter")
    @CrossOrigin(origins = "http://localhost:4200")

    public void addParameter(@Valid @RequestBody Command command, @Valid @RequestBody List<Variable> variables){
        System.out.println("This is addParameter Controller.");

    }
    /*
    @GetMapping(value="/getCommands")
    @CrossOrigin(origins = "http://localhost:4200")


    public List<Procedure> showAlgorithm(){
        return
    }

     */





}

