package com.ik.kovan.controller;

import com.ik.kovan.logic.CommandCreation;
import com.ik.kovan.model.Command;
import com.ik.kovan.model.Statement;
import com.ik.kovan.model.Variable;
import com.ik.kovan.service.impl.CommandImpl;
import com.ik.kovan.service.impl.StatementImpl;
import com.ik.kovan.service.impl.VariableImpl;
import com.ik.kovan.service.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CommandController {
    @Autowired
    private final CommandImpl commandService;

    @Autowired
    private final VariableImpl variableService;

    @Autowired
    private final StatementImpl statementService;


    public CommandController(CommandImpl commandService, VariableImpl variableService, StatementImpl statementService) {
        this.commandService = commandService;
        this.variableService = variableService;
        this.statementService = statementService;
    }

    /*

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
    */

    @GetMapping("/getParameters")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<String> getParameters(){
        return variableService.showTablesAndColumns();
    }


    @PostMapping("getVars")
    @CrossOrigin(origins = "http://localhost:4200")
    public void getVariables(@Valid @RequestBody Variable variable){
        System.out.println("This is Variables Controller");
        System.out.println(variable);

    }

    @PostMapping("/defineCommand")
    @CrossOrigin(origins = "http://localhost:4200")
    public Command addCommand(@Valid @RequestBody CommandCreation commandCreation){

        System.out.println("This is Command Registration Controller.");
        Command command = commandCreation.getCommand();
        List<Variable> variables = commandCreation.getVariables();
        List<Statement> statements = commandCreation.getStatements();

        variableService.saveAll(variables);
        statementService.saveAll(statements);

        command.setVariables(variables);
        command.setStatements(statements);
        return commandService.save(command);

    }


}

