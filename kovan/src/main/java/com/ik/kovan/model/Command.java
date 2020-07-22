package com.ik.kovan.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Command {
    @Id
    @GeneratedValue
    private int commandId;
    private String rawCommand;

}
