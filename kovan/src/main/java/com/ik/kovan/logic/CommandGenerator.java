package com.ik.kovan.logic;


import com.ik.kovan.model.Statement;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/* takes the raw input from the Controller and parses the raw command using the LineReader Class  */
public class CommandGenerator {

    private double result;

    public CommandGenerator(){

    }

    public double getResult() {
        return result;
    }

    public CommandGenerator(double result){
        this.result = result;
    }


    public String calculate(List<String> statements){
        return Interpreter.readStatementLines(statements); // bu bana key, value olarak geri dönsün <String, String>

    }

    public static void main(String[] args) {
    }

}
