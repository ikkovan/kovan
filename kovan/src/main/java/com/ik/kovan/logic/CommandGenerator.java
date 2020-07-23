package com.ik.kovan.logic;



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


    public double calculate(String command){
        this.result = LineReader.getFunction(command);
        return result;

    }

    public static void main(String[] args) {
    }

}
