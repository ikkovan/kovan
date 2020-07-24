package com.ik.kovan.logic;


import java.util.HashMap;

/* takes the raw input from the Controller and parses the raw command using the LineReader Class  */
public class CommandGenerator {

    private HashMap<String, Double> commandAndResult;

    public CommandGenerator(){

    }

    public CommandGenerator(HashMap<String, Double> result){
        this.commandAndResult = result;
    }

    public HashMap<String, Double> calculate(String command){
        this.commandAndResult = LineReader.getFunction(command);
        return commandAndResult;

    }

    public HashMap<String, Double> getResult() {
        return commandAndResult;
    }

    public static void main(String[] args) {
    }

}
