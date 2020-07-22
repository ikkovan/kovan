package com.ik.kovan.repository;

import com.ik.kovan.model.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

    Command findByCommandId(int id);

    @Query(value = "select c from Command c")
    List<Command> listCommands();

    @Query(value = "select r from Command  r where r = :command")
    String getRawCommand(@Param("command") Command command);

    Command save(Command command);

    void delete(Command command);


}
