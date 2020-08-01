package com.ik.kovan.repository;

import com.ik.kovan.model.Command;
import com.ik.kovan.model.Variable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

    Command findByCommandName(String commandName);

    @Query(value = "select c from Command c")
    List<Command> listCommands();

    @Query(value = "select c from Command c")
    void showCommands();

    @Query(value = "select r from Command  r where r.commandName=:command")
    String getCommandName(@Param("command") String id);


    Command save(Command command);

    void delete(Command command);


}
