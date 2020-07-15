package kovanikstaj;

import java.io.StreamCorruptedException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Stack;

public class LineReader {
	
	private static class Operand {
		private char operator;
		private int precedence;
		
		public Operand(String contents) {
		       switch(contents) {
		           case "+":
		               operator = contents.charAt(0);
		               precedence = 1;
		               break;
		           case "-":
		               operator = contents.charAt(0);
		               precedence = 1;
		               break;
		           case "*":
		               operator = contents.charAt(0);
		               precedence = 2;
		               break;
		           case "/":
		               operator = contents.charAt(0);
		               precedence = 2;
		               break;
		           case "(":
		        	   operator = contents.charAt(0);
		        	   precedence = -1;
		               break;
		           case ")":
		        	   operator = contents.charAt(0);
		        	   precedence = -2;
		               break;
		       }
		   }
		
		double getOperator() { return operator; }
		 int getPrecedence() { return precedence; }
		 
		 public double operate(double a,double b) {
		       double result = 0;
		       switch(operator) {
		           case '+':
		               result = a + b;
		               break;
		           case '-':
		               result = a - b;
		               break;
		           case '*':
		               result = a * b;
		               break;
		           case '/':
		               result = a / b;
		               break;
		       }
		       return result;
		   }

	}
	

	/*
	 * This function calculates the net minimum wage with inputs: gross minimum
	 * wage, ssk workers share(?), unemployement insurance bonus(?), stamp tax,
	 * income tax and the result from calculateAGI. double ssk_ip, double isp,
	 * double damga_vergisi, double gelir_vergisi are taken is percentage values.
	 */
	private static double netMinWage(double min_wage, double ssk_ip, double isp, double damga_vergisi,
			double gelir_vergisi, double agi) {
		double sigorta_payi = (min_wage * percent(ssk_ip)), isp_payi = (min_wage * percent(isp)),
				damga_vergisi_miktari = (min_wage * percent(damga_vergisi));
		double kgvm = min_wage - sigorta_payi - isp_payi;
		double gelir_vergisi_miktari = kgvm * percent(gelir_vergisi);
		double ele_gecen = min_wage + agi - sigorta_payi - isp_payi - gelir_vergisi_miktari - damga_vergisi_miktari;
		return ele_gecen;
	}

	private static double nmw(String[] s)
	{
		if(s.length != 6 ) {
			System.err.println("Wrong number of arguments for AGI");
			return -1;
		}
		try {
			double min_wage = Double.parseDouble(s[0]);
			double ssk_ip = Double.parseDouble(s[1]); 
			double isp = Double.parseDouble(s[2]); 
			double damga_vergisi = Double.parseDouble(s[3]);
			double gelir_vergisi = Double.parseDouble(s[4]); 
			double agi = Double.parseDouble(s[5]);
			return netMinWage(min_wage, ssk_ip, isp, damga_vergisi, gelir_vergisi, agi);
		} catch (Exception e) {
				System.err.println("Inputs can not be treated as numbers");
		}
		return -1;
	}
	
	private static double percent(double number) {
		return number / 100.0;
	}

	/*
	 * This function is for calculatingAGI with inputs: minimum wage, status of the
	 * workers partner, count of workers children,the tax bracket of the worker, max
	 * children count for applying discounts and the percentage of the discount for
	 * the worker him/herself 
	 * //TODO: Values of the partner status can change so it has to be obtained from a table according to its value in worker's table
	 * //TODO: Percentages for the tax bracket can change so it
	 * has to be obtained from a table according to its value in worker's(?) table
	 * //TODO: Calculations for the number of children is hard coded in this
	 * function. Since it can change in the future it is best to hold a table for
	 * percentages according to the number of children. 
	 * For example this table is being assumed for now: 
	 * th child ----> percentage 
	 * 	  1 	---->	 7.5 
	 * 	  2 	---->    7.5 
	 * 	  3     ---->    10 
	 * 	  * 	----> 	 5
	 --Note: it is assumed that if the value of the * status of partner is below 0 then the partner is
	 * working otherwise it means --partner is employed and the percentage we should
	 * is the value of the variable.
	 */
	private static double calculateAGI(double min_wage, double status_of_partner, int count_of_children,
			double tax_bracket, int max_children, double self_prcnt) {
		double gross_yearly_salary = 0.0, self = 0.0, from_partner = 0.0, from_children = 0.0;
		gross_yearly_salary = min_wage * 12;
		self = gross_yearly_salary * percent(self_prcnt);
		if (status_of_partner > 0) {
			from_partner = status_of_partner;
		}
		for (int th_child = 1; th_child <= count_of_children; th_child++) {
			if (th_child <= max_children) {
				if (th_child < 2) {
					from_children += gross_yearly_salary * percent(7.5);
				} else if (th_child == 3) {
					from_children += gross_yearly_salary * percent(10);
				} else {
					from_children += gross_yearly_salary * percent(5);
				}
			}
		}
		double tax_assesment = self + from_children + from_partner + from_children;
		tax_assesment = tax_assesment * percent(tax_bracket);
		return tax_assesment / 12;

	}

	private static double agi(String[] s) {
		if(s.length != 6 ) {
			System.err.println("Wrong number of arguments for AGI");
			return -1;
		}
		try {
			double mw,sop,tb,sp;
			int coc,mc;
			mw = Double.parseDouble(s[0]);
			sop = Double.parseDouble(s[1]);
			tb = Double.parseDouble(s[3]);
			sp = Double.parseDouble(s[5]);
			coc = Integer.parseInt(s[2]);
			mc = Integer.parseInt(s[4]);
			return calculateAGI(mw, sop, coc, tb, mc, sp);
		} catch (Exception e) {
			System.err.println("Inputs can not be treated as numbers");
		}
		return -1;
	}
	
	/*
	*	This doesn't work correctly
	*/
	private static void readLine(String s) {
		String[] args = s.split(" ");
		HashMap<Integer, String> function_locations = getFunctionLocations(s);
		HashMap<Integer, Double> function_returns = handleFunctions(function_locations);
		for(Entry<Integer,Double> e: function_returns.entrySet()) {
			args[e.getKey()] = e.getValue() +"";
		}
		HashMap<String, String>  local_variables = setLocalVaraibles(args);
		handleMaths(args, local_variables);
	}
	
	
	private static void handleLine(String s) {
		String[] args = s.split(" ");
		ArrayList<String> modified_args = new ArrayList<String>();
		HashMap<Integer, String> function_locations = getFunctionLocations(s);
		HashMap<String, String>  local_variables = new HashMap<String, String>();
		HashMap<Integer, Double> function_returns = new HashMap<Integer, Double>();
		
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("=") && i > 0 && i < args.length-1) {
				if(function_locations.get(i+1) != null)
				{
					double return_value = handleSingleFunction(function_locations.get(i+1), local_variables);
					local_variables.put(args[i-1], return_value + "");
					modified_args.remove(modified_args.size()-1);
					
				}
				else
				{
					local_variables.put(args[i-1],args[i+1]);
				}
				i++;
			}
			else if(function_locations.get(i) != null)
			{
				modified_args.add(handleSingleFunction(args[i],local_variables) + "");
			}
			else {
				modified_args.add(args[i]);
			}
		}
		double result = handleMaths(modified_args.toArray(new String[0]), local_variables);
	}
	
	
	
	
	
	

	/*
	 * Handles arithmetichs after return values from the functions are applied
	 * automatically replaces varibales with their values
	 */
	private static double handleMaths(String[] line,HashMap<String, String>  local_variables) {
		Stack<Double> numbers = new Stack<Double>();
		Stack<Operand> operands = new Stack<Operand>();
		for (int i = 0; i < line.length ; i++) 
		{
			if(line[i].matches("[A-Z]+"))//if it is a variable
			{
				String value = local_variables.get(line[i]);
				line[i] = value;
			}
			String s = line[i];
			if(s.matches("-*[0-9]+\\.*[0-9]*"))//if it is a number 
			{
				double temp = Double.parseDouble(s);
				numbers.push(temp);
			}else if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"))	{
				Operand op = new Operand(s);
				if (operands.isEmpty() || op.getPrecedence() > operands.peek().getPrecedence()) {
					operands.push(op);
				}
				else {
					while (!operands.isEmpty() && op.getPrecedence() <= op.precedence) {
						Operand process = operands.pop();
						double a = 0, b = 0;
						if (numbers.isEmpty())
							System.err.println("Expression error");
						else
							b = numbers.pop();
						if (numbers.isEmpty())
							System.err.println("Expression error");
						else
							a = numbers.pop();
						double temp_result = process.operate(a, b);
						numbers.push(temp_result);
					}
					operands.push(op);
				}
			} 
			else if (s.equals("(")) 
			{
				Operand op = new Operand(s);
				operands.push(op);
			}
			else if (s.equals(")")) 
			{
				Operand op = new Operand(s);
				while ( !operands.isEmpty() && (operands.peek().operator != '(' || operands.peek().operator != ')') )
				{
					Operand process = operands.pop();
					double a = 0, b = 0;
					if (numbers.isEmpty())
						System.err.println("Expression error");
					else
						b = numbers.pop();
					if (numbers.isEmpty())
						System.err.println("Expression error");
					else
						a = numbers.pop();
					double temp_result = process.operate(a, b);
					numbers.push(temp_result);
				}
				if( !operands.isEmpty() && operands.peek().operator == '(' ) {
					operands.pop();
				}else {
					System.out.println("Expression error");
				}
					
			}			
		}
		while( !operands.isEmpty() && (operands.peek().operator != '(' || operands.peek().operator != ')') )
		{
			Operand process = operands.pop();
			double a = 0, b = 0;
			if (numbers.isEmpty())
				System.err.println("Expression error");
			else
				b = numbers.pop();
			if (numbers.isEmpty())
				System.err.println("Expression error");
			else
				a = numbers.pop();
			double temp_result = process.operate(a, b);
			numbers.push(temp_result);
		}
		double resultante = numbers.pop();
		if( !operands.isEmpty() || !numbers.isEmpty()) {
			System.out.println("Expression error!");
		}else {
			System.out.println("Result: " + resultante);
		}
		return resultante;
	}

	/*
	 * With this function we can get functions(eg MOD(5,3) or GET_TABLE(1102)) from
	 * a string. It returns a hash map corresponding to ( (int) xth_word ,(String) function ) pair
	 */
	private static HashMap<Integer, String> getFunctionLocations(String s) {
		String[] input_args = s.split(" ");
		HashMap<Integer, String> function_locations = new HashMap<>();
		for (int i = 0; i < input_args.length; i++) {
			String current = input_args[i];
			if (current.matches("[a-zA-Z]+\\(.*\\)")) {
				function_locations.put(i, current);
				System.out.println("Function: " + current + " location: " + i);
			}
		}
		return function_locations;
	}
	

	
	private static HashMap<String, String> setLocalVaraibles(String[] input_args) {
		HashMap<String, String> variables = new HashMap<>();
		for (int i =  1 ; i < input_args.length - 1 ; i++) {
			if(input_args[i].equals("=")) {
				variables.put(input_args[i-1], input_args[i-1]);
				System.out.println("variable name: " + input_args[i-1] + ", value: "+ input_args[i+1]);
			}
		}
		return variables;
	}
	
	
	

	/*
	 * A function that gets hashmap of the functions and their places ( xth word), 
	 * Calls the appropriate function and stores its return value with its place and returns it
	*/
	private static HashMap<Integer, Double> handleFunctions(HashMap<Integer, String> funcs) {
		HashMap<Integer, Double> results = new HashMap<Integer, Double>();
		for (Entry<Integer, String> f : funcs.entrySet()) {
			String whole_function = f.getValue();
			int first_prn = whole_function.indexOf('('), second_prn = whole_function.indexOf(')');
			String function_name = whole_function.substring(0, first_prn);
			String[] function_args = whole_function.substring(first_prn + 1, second_prn).split(",");
			double result = 0;
			switch (function_name) {
			case "MOD":
				result = mod(function_args);
				results.put(f.getKey(), result);
				break;
			case "MIN":
				result = min(function_args);
				results.put(f.getKey(), result);
				break;
			case "MAX":
				result = max(function_args);
				results.put(f.getKey(), result);
				break;
			case "PRCNT":
				double arg = -1;
				try {
					arg = Double.parseDouble(function_args[0]);
					result = percent(arg);
				} catch (Exception e) {
					System.err.println(function_args.toString() + " cannot be treated as a value");
					result = arg;
				} finally {
					results.put(f.getKey(), result);
				}
				break;
			case "AGI":
				result = agi(function_args);
				results.put(f.getKey(), result);
				break;
			case "NMW":
				result = nmw(function_args);
				results.put(f.getKey(), result);
				break;
			default:
				System.out.println("Invalid or not yet implemented method call");
				result = -1;
				break;
			}
			System.out.println("Func_Name:" + function_name + ", args count:" + function_args.length + ", result: " + result);
		}
		return results;
	}

	
	
	/*
	 * A function that gets a function
	 *  Calls the appropriate function and returns its return value 
	*/
	private static double handleSingleFunction(String whole_function,HashMap<String, String>  local_variables) {
		int first_prn = whole_function.indexOf('('), second_prn = whole_function.indexOf(')');
		String function_name = whole_function.substring(0, first_prn);
		String[] function_args = whole_function.substring(first_prn + 1, second_prn).split(",");
		double result = 0;
		for (int i = 0; i < function_args.length; i++) {
			if(local_variables.containsKey(function_args[i]))
				function_args[i] = local_variables.get(function_args[i]);//wirte values of variables
		}
		switch (function_name) {
		case "MOD":
			result = mod(function_args);
			break;
		case "MIN":
			result = min(function_args);
			break;
		case "MAX":
			result = max(function_args);
			break;
		case "PRCNT":
			double arg = -1;
			try {
				arg = Double.parseDouble(function_args[0]);
				result = percent(arg);
			} catch (Exception e) {
				System.err.println(function_args.toString() + " cannot be treated as a value");
				result = arg;
			}
			break;
		case "AGI":
			result = agi(function_args);
			break;
		case "NMW":
			result = nmw(function_args);
			break;
		default:
			System.out.println("Invalid or not yet implemented method call");
			result = -1;
			break;
		}
		System.out.println("Func_Name:" + function_name + ", args count:" + function_args.length + ", result: " + result);
		return result;
	}
	
	
	private static double min(String[] args) {
		double minimum = Double.MAX_VALUE;
		try {
			for(int i = 0 ; i < args.length ; i++) {
				double temp = Double.parseDouble(args[i]);
				if(temp < minimum)
					minimum = temp;
			}	
		}catch (NumberFormatException e) {
			System.err.println(args.toString() + " cannot be treated as a value");
			minimum = Double.MAX_VALUE;
		}
		return minimum;
	}
	
	private static double max(String[] args) {
		double maximum = Double.MIN_VALUE;
		try {
			for(int i = 0 ; i < args.length ; i++) {
				double temp = Double.parseDouble(args[i]);
				if(temp > maximum)
					maximum = temp;
			}	
		}catch (NumberFormatException e) {
			System.err.println(args.toString() + " cannot be treated as a number");
			maximum = Double.MIN_VALUE;
		}
		return maximum;
	}

	/*
	 * A basic function to find modulus/remainder
	  -- Note: retruns -1 if user tried to send more than 2 arguments
	  -- Note: returns -2 if user entered invalid argument(s)
	 */
	private static double mod(String[] args) {
		if(args.length != 2)
			return -1;
		double arg1,arg2;
		try {
			arg1 = Double.parseDouble(args[0]);
			arg2 = Double.parseDouble(args[1]);
			return arg1 % arg2;
		} catch (NumberFormatException e) {
			System.err.println(args.toString() + " cannot be treated as a number");
			return -2;
		}
	}

	
	// For test purposes
	public static void main(String[] args) {
		calculations_tests();
		getting_functions_test();
	}

	public static void calculations_tests() {
		double agi = calculateAGI(2943, -1, 0, 15, 3, 50);
		System.out.println(agi);
		double net = netMinWage(2943, 14, 1, 0.759, 15, agi);
		System.out.println(net);
	}

	public static void getting_functions_test() {
		Scanner sc = new Scanner(System.in);
		System.out.print(">");
		String line = sc.nextLine();
		handleLine(line);
	}

}
