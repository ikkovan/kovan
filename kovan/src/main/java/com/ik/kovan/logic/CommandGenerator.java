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


    public void calculate(List<Statement> statements){
        List<String> statementLines = new ArrayList<String>();
        for (Statement statement : statements){
            statementLines.add(statement.getLine());
        }
        Interpreter.readStatementLines(statementLines);

    }

    public static void main(String[] args) {
    }

}
