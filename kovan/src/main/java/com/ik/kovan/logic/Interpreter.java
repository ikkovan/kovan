package com.ik.kovan.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StreamCorruptedException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.springframework.beans.factory.annotation.Autowired;

public class Interpreter {

	private static boolean read_return;
	private static String final_return;
	private static HashMap<Integer, String> function_locations;
	private static HashMap<String, String> local_variables;
	private static ArrayList<String> lines;
	@Autowired
	static CommandPayroll commandPayroll;

	public Interpreter(CommandPayroll commandPayroll) {
		this.read_return = false;
		this.final_return = "";
		this.local_variables = new HashMap<String, String>();
		this.lines = new ArrayList<String>();
		this.commandPayroll = commandPayroll;
	}
	
	public Interpreter() {
		this.read_return = false;
		this.final_return = "";
		this.local_variables = new HashMap<String, String>();
		this.lines = new ArrayList<String>();
	}
	
	public static HashMap<String, String> getLocal_variables() {
		return local_variables;
	}


	private static class Operand {
		private char operator;
		private int precedence;

		public Operand(String contents) {
			if (contents.equals("+")) {
				operator = contents.charAt(0);
				precedence = 1;
			} else if (contents.equals("-")) {
				operator = contents.charAt(0);
				precedence = 1;
			} else if (contents.equals("*")) {
				operator = contents.charAt(0);
				precedence = 2;
			} else if (contents.equals("/")) {
				operator = contents.charAt(0);
				precedence = 2;
			} else if (contents.equals("(")) {
				operator = contents.charAt(0);
				precedence = -1;
			} else if (contents.equals(")")) {
				operator = contents.charAt(0);
				precedence = -2;
			}
		}

		double getOperator() {
			return operator;
		}

		int getPrecedence() {
			return precedence;
		}

		public double operate(double a, double b) {
			double result = 0;
			switch (operator) {
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

	private static double nmw(String[] s) {
		if (s.length != 6) {
			System.err.println("Wrong number of arguments for NMW");
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
	 * the worker him/herself //TODO: Values of the partner status can change so it
	 * has to be obtained from a table according to its value in worker's table
	 * //TODO: Percentages for the tax bracket can change so it has to be obtained
	 * from a table according to its value in worker's(?) table //TODO: Calculations
	 * for the number of children is hard coded in this function. Since it can
	 * change in the future it is best to hold a table for percentages according to
	 * the number of children. For example this table is being assumed for now: th
	 * child ----> percentage 1 ----> 7.5 2 ----> 7.5 3 ----> 10 * ----> 5 --Note:
	 * it is assumed that if the value of the * status of partner is below 0 then
	 * the partner is working otherwise it means --partner is employed and the
	 * percentage we should is the value of the variable.
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
		double tax_assesment = self + from_children + from_partner;
		tax_assesment = tax_assesment * percent(tax_bracket);
		return tax_assesment / 12;

	}

	/*
	 * Calculate how much more we need to pay to the worker for the overtime he
	 * worked returns 0 if the worker did not work more than the limit
	 */
	private static double calculateOvertime(double gross_wage, double total_work_time, double overtime_limit,
			double average_daily_work_time) {
		if (total_work_time <= overtime_limit)
			return 0.0;
		double overtime = total_work_time - overtime_limit;
		double hourly_wage = gross_wage / (average_daily_work_time * 30);
		double overtime_wage = hourly_wage * 1.5;
		return overtime * overtime_wage;
	}

	private static double otw(String[] s) {
		if (s.length != 4) {
			System.err.println("Wrong number of arguments for OTW");
			return 0;
		}
		try {
			double gross_wage = Double.parseDouble(s[0]);
			double total_work_time = Double.parseDouble(s[1]);
			double overtime_limit = Double.parseDouble(s[2]);
			double average_daily_work_time = Double.parseDouble(s[3]);
			return calculateOvertime(gross_wage, total_work_time, overtime_limit, average_daily_work_time);
		} catch (Exception e) {
			System.err.println("Inputs can not be treated as numbers");
		}
		return 0;
	}

	/*
	 * Substring function works same as Java String function
	 */
	private static String subs(String s[]) {
		if (s.length < 2 && s.length > 3) {
			System.err.println("Wrong number of arguments for Substring");
			return null;
		}
		try {
			int start_index = Integer.parseInt(s[1]);
			String original_string = s[0];
			int end_index = original_string.length();
			if (s.length == 3)
				end_index = Integer.parseInt(s[2]);
			String to_return = original_string.substring(start_index, end_index);
			return to_return;

		} catch (NumberFormatException e) {
			System.err.println("Inputs can not be treated as integers");
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Input numbers are out of bounds for the specified string");
		}
		return null;
	}

	private static double abs(String s) {
		try {
			double to_return = Double.parseDouble(s);
			return Math.abs(to_return);
		} catch (Exception e) {
			System.err.println("Input can not be treated as number");
		}
		return 0;
	}

	private static double sign(String s) {
		try {
			double num = Double.parseDouble(s);
			if (num < 0)
				return -1;
			if (num > 0)
				return 1;
			else
				return 0;
		} catch (Exception e) {
			System.err.println("Input can not be treated as number");
		}
		return 0;
	}

	private static double ceil(String s) {
		try {
			double num = Double.parseDouble(s);
			return Math.ceil(num);
		} catch (Exception e) {
			System.err.println("Input can not be treated as number");
		}
		return 0;
	}

	private static double floor(String s) {
		try {
			double num = Double.parseDouble(s);
			return Math.floor(num);
		} catch (Exception e) {
			System.err.println("Input can not be treated as number");
		}
		return 0;
	}

	private static double frac(String s) {
		try {
			int decimal = Integer.parseInt(s.substring(s.indexOf('.')));
			return decimal * sign(s);
		} catch (Exception e) {
			System.err.println("Input can not be treated as number");
		}
		return 0;
	}

	private static int find(String s[]) {
		if (s.length != 2) {
			System.err.println("Wrong number of arguments for FIND");
			return -2;
		}
		return s[0].indexOf(s[1]);
	}

	private static int lng(String s) {
		return s.length();
	}

	private static double hwf(String[] s) {
		if (s.length != 2) {
			System.err.println("Wrong number of arguments for HWF");
			return 0;
		}
		try {
			double gross_wage = Double.parseDouble(s[0]);
			double average_working_hours = Double.parseDouble(s[1]);
			return hourly_fee(gross_wage, average_working_hours);
		} catch (Exception e) {
			System.err.println("Inputs can not be treated as numbers");
		}
		return 0;
	}

	private static double agi(String[] s) {
		if (s.length != 6) {
			System.err.println("Wrong number of arguments for AGI");
			return -1;
		}
		try {
			double mw, sop, tb, sp;
			int coc, mc;
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
	
	private static boolean isVariable(String s) {
		return s.matches("[a-zA-z]+(_*[a-zA-Z]+)*");
	}
	
	/*
	 * Reseting function_locations and function_returns for each line But
	 * local_varibales are held globally
	 */
	private static int handleLine(int index) {
		int next_line = index + 1;
		function_locations = new HashMap<Integer, String>();
		String s = lines.get(index).trim();
		getFunctionLocations(s);
		String[] expressions = s.split(" ");
		for (int i = 0; i < expressions.length; i++) {
			if (expressions[i].equals("=")) {
				String var_name = expressions[i - 1];
				if (!isVariable(var_name)) {
					System.out.println("INVALID VARIABLE NAME");
					return next_line;
				} else if ((i + 2) < expressions.length && !expressions[i + 2].equals(",")) {
					ArrayList<String> temp = new ArrayList<String>();
					i++;
					while (i < expressions.length && !expressions[i].equals(",")) {
						if (function_locations.get(i) != null) {

							double resultante_double = handleArithmeticFunction(expressions[i]);
							if (resultante_double != Double.NEGATIVE_INFINITY)
								temp.add(resultante_double + "");
							else
								System.out.println("INVALID FUNCTION : " + expressions[i]);

						} else {
							temp.add(expressions[i]);
						}
						i++;
					}
					local_variables.put(var_name, handleMaths(temp.toArray(new String[0])) + "");
				} else if (function_locations.get(i + 1) != null) {
					double resultante_double = handleArithmeticFunction(expressions[i + 1]);
					if (resultante_double != Double.NEGATIVE_INFINITY)
						local_variables.put(var_name, resultante_double + "");
					else
						local_variables.put(var_name, handleStringFunction(expressions[i + 1]));

					i++;
				} else if (local_variables.containsKey(expressions[i + 1])) {
					local_variables.put(var_name, local_variables.get(expressions[i + 1]));
					i++;
				} else {
					local_variables.put(var_name, expressions[i + 1]);
					i++;
				}
			} else if (expressions[i].contains("(") && expressions[i].contains(")")) {
				handleStringFunction(expressions[i]);
			} else if (expressions[i].equals("IF") || expressions[i].contains("ELSE") || expressions[i].equals("WHILE") ) {// branching problem
				next_line = branching(index);
				i = expressions.length;
			} else if (expressions[i].matches("[a-zA-z]+(_*[a-zA-Z]+)*\\+\\+")) {
				int first_plus = expressions[i].indexOf('+');
				String key = expressions[i].substring(0, first_plus);
				String val = local_variables.get(key);
				double to_increment = Double.parseDouble(val);
				to_increment += 1;
				local_variables.put(key, to_increment + "");
			} else if (expressions[i].matches("[a-zA-z]+(_*[a-zA-Z]+)*--")) {
				int first_plus = expressions[i].indexOf('-');
				String key = expressions[i].substring(0, first_plus);
				String val = local_variables.get(key);
				double to_decrement = Double.parseDouble(val);
				to_decrement -= 1;
				local_variables.put(key, to_decrement + "");
			} else if (expressions[i].equals("+=")) {
				String var_name = expressions[i - 1];
				String val = local_variables.get(var_name);
				double to_increment = Double.parseDouble(val);
				double how_much = Double.parseDouble(expressions[i+1]); 
				to_increment += how_much;
				local_variables.put(var_name, to_increment + "");
				i++;
			} else if (expressions[i].equals("-=")) {
				String var_name = expressions[i - 1];
				String val = local_variables.get(var_name);
				double to_decrement = Double.parseDouble(val);
				double how_much = Double.parseDouble(expressions[i+1]); 
				to_decrement -= how_much;
				local_variables.put(var_name, to_decrement + "");
				i++;
			}
		}
		return next_line-1;
	}

	/*
	 * works same as handleLine but takes line itself as a parameter instead of index
	 * since branching is working only in multilines branching is not working in this function
	 * so it is commented out
	 */
	private static void handleSingleLine(String line) {
		function_locations = new HashMap<Integer, String>();
		String s = line.trim();
		getFunctionLocations(s);
		String[] expressions = s.split(" ");
		for (int i = 0; i < expressions.length; i++) {
			if (expressions[i].equals("=")) {
				String var_name = expressions[i - 1];
				if (!isVariable(var_name)) {
					System.out.println("INVALID VARIABLE NAME");
					return ;
				} else if ((i + 2) < expressions.length && !expressions[i + 2].equals(",")) {
					ArrayList<String> temp = new ArrayList<String>();
					i++;
					while (i < expressions.length && !expressions[i].equals(",")) {
						if (function_locations.get(i) != null) {

							double resultante_double = handleArithmeticFunction(expressions[i]);
							if (resultante_double != Double.NEGATIVE_INFINITY)
								temp.add(resultante_double + "");
							else
								System.out.println("INVALID FUNCTION : " + expressions[i]);

						} else {
							temp.add(expressions[i]);
						}
						i++;
					}
					local_variables.put(var_name, handleMaths(temp.toArray(new String[0])) + "");
				} else if (function_locations.get(i + 1) != null) {
					double resultante_double = handleArithmeticFunction(expressions[i + 1]);
					if (resultante_double != Double.NEGATIVE_INFINITY)
						local_variables.put(var_name, resultante_double + "");
					else
						local_variables.put(var_name, handleStringFunction(expressions[i + 1]));

					i++;
				} else if (local_variables.containsKey(expressions[i + 1])) {
					local_variables.put(var_name, local_variables.get(expressions[i + 1]));
					i++;
				} else {
					local_variables.put(var_name, expressions[i + 1]);
					i++;
				}
			} else if (expressions[i].contains("(") && expressions[i].contains(")")) {
				handleStringFunction(expressions[i]);
			} /*else if (expressions[i].equals("IF") || expressions[i].contains("ELSE") || expressions[i].equals("WHILE") ) {// branching problem
				next_line = branching(index);
				i = expressions.length;
			}*/ else if (expressions[i].matches("[a-zA-z]+(_*[a-zA-Z]+)*\\+\\+")) {
				int first_plus = expressions[i].indexOf('+');
				String key = expressions[i].substring(0, first_plus);
				String val = local_variables.get(key);
				double to_increment = Double.parseDouble(val);
				to_increment += 1;
				local_variables.put(key, to_increment + "");
			} else if (expressions[i].matches("[a-zA-z]+(_*[a-zA-Z]+)*--")) {
				int first_plus = expressions[i].indexOf('-');
				String key = expressions[i].substring(0, first_plus);
				String val = local_variables.get(key);
				double to_decrement = Double.parseDouble(val);
				to_decrement -= 1;
				local_variables.put(key, to_decrement + "");
			} else if (expressions[i].equals("+=")) {
				String var_name = expressions[i - 1];
				String val = local_variables.get(var_name);
				double to_increment = Double.parseDouble(val);
				double how_much = Double.parseDouble(expressions[i+1]); 
				to_increment += how_much;
				local_variables.put(var_name, to_increment + "");
				i++;
			} else if (expressions[i].equals("-=")) {
				String var_name = expressions[i - 1];
				String val = local_variables.get(var_name);
				double to_decrement = Double.parseDouble(val);
				double how_much = Double.parseDouble(expressions[i+1]); 
				to_decrement -= how_much;
				local_variables.put(var_name, to_decrement + "");
				i++;
			}
		}
		return ;
	}
	
	
	/*
	 * A function to return line index after the else and else if parts when an if or else if part as executed
	 * TR: "IF " veya "ELSE IF "lerden biri true döndürdüğünde içine girip içindeki kodları çalıştırdıktan sonra gelen "ELSE " 	ve "ELSE IF "
	 * blokları (içinie girilmemesi gerekenler) atladıktan sonra hangi line indexten devam edileceğini döndürür
	 */
	private static int skipElses(int i) {
		Stack<String> nest = new Stack<String>();
		ArrayList<String> block = new ArrayList<String>();
		if(lines.get(i).startsWith("ENDIF"))
			return i;
		nest.push( lines.get(i));
		block.add( lines.get(i));
		i++;
		while (!nest.isEmpty() && i < lines.size()) {
			String current_line = lines.get(i);
			current_line = current_line.trim();
			if (current_line.startsWith("IF")) {
				i = skipElses(i);
			} else {
				if (current_line.equals("ENDIF")) {
					nest.pop();
				}
				block.add(lines.get(i));
				i++;
			}
		}
		return i;
	}
	
	/*
	 * A function to return line index of the else or else if part, 
	 * after if or else if block is not executed (since expression of that part returned false)
	 * TR: "IF " veya "ELSE IF "lerden biri false döndürdüğünde içine girmeden sıradaki "ELSE " veya "ELSE IF " line index'ini döndürüyor
	 */
	private static int goToElse(int i) {
		Stack<String> nest = new Stack<String>();
		ArrayList<String> block = new ArrayList<String>();
		nest.push( lines.get(i));
		block.add( lines.get(i));
		i++;
		while (!nest.isEmpty() && i < lines.size()) {
			String current_line = lines.get(i);
			current_line = current_line.trim();
			if (current_line.startsWith("IF")) {
				i = skipElses(i);
			} else {
				if (current_line.startsWith("ENDIF")) {
					nest.pop();
				}else if(current_line.startsWith("ELSE") && nest.size() == 1) {
					block.add(lines.get(i));
					i++;
					return i;
				}
				block.add(lines.get(i));
				i++;
			}
		}
		return i;
	}

	/*
	 * A function to handle if-else if branchings
	 * it uses nashorn script engine (js) to evaluate the expressions
	 * !! there may be some problems with String functions !!
	 * it returns line index to continue from after all if-else-else if's are finished even the nested ones
	 * EXAMPLE OF IF-ELSE STRUCTURE:
	 * IF A > B :
	 * --SOME CODES
	 * ELSE IF A == B :
	 * ---SOME OTHER CODES
	 * ELSE :
	 * ---- SOME SOME OTHER OTHER CODES
	 * ENDIF
	 * --------------END OF EXAMPLE ----------------------
	 * NOTE: THERE CAN BE NESTED IF-ELSE'S
	 * NOTE 2: EVERY IF-ELSE BLOCK SHOULD END WITH "ENDIF" LINE
	 * NOTE 3: IF THERE ARE CODES ON THE SAME LINE WITH KEYWORDS ( e.g."IF ", "ELSE " , "ENDIF" , etc) THEY WILL NOT BE EXECUTED
	 * TODO: SWITCH CASES ARE TO BE ADDED
	 */
	private static int branching(int index) {
		String whole_line = lines.get(index);
		int start_point = 0;
		if (whole_line.indexOf("IF ") >= 0) {
			start_point += whole_line.indexOf("IF ") + 3;
			int end_point = whole_line.indexOf(":");
			String to_evaluate = whole_line.substring(start_point, end_point);
			String[] temp = to_evaluate.split(" ");
	        SimpleBindings variables = new SimpleBindings();
			for (int i = 0; i < temp.length; i++) {
				if (temp[i].matches("[a-zA-Z]+\\(.*\\)")) {
					double result = handleArithmeticFunction(temp[i]);
					String sonuc = handleStringFunction(temp[i]);
					if (result != Double.NEGATIVE_INFINITY) {
						variables.put(temp[i], result);
					} else if (!sonuc.equals("Double.NEGATIVE_INFINITY")) {
						variables.put(temp[i], sonuc);
					}
				} else if (local_variables.containsKey(temp[i])) {
					variables.put(temp[i],local_variables.get(temp[i]));
				}
			}
			to_evaluate = "";
			for (int i = 0; i < temp.length; i++) {
					to_evaluate += " " + temp[i] ;
			}
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
			try {
				String evaluate = "eval('" + to_evaluate + "');";
				Object result = engine.eval(evaluate,variables);
				index++;
				String current_line = lines.get(index);
				if (Boolean.TRUE.equals(result)) {
					while (!current_line.contains("ELSE") && !current_line.startsWith("ENDIF")) {
						index = handleLine(index);
						index++;
						current_line = lines.get(index);
					}
					index = skipElses(index);
				}else {//if false go to next else
					index = goToElse(index-1);
					if(index < lines.size() && lines.get(index-1).contains("ELSE")) {
						index = branching(index);
					}
				}

			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (whole_line.startsWith("ELSE")) {
			index++;
			String current_line = lines.get(index);
			while (!current_line.contains("ENDIF")) {
				index = handleLine(index);
				current_line = lines.get(index);
			}
		} else if( whole_line.startsWith("WHILE")) {
			String whl = "WHILE ";
			start_point += whole_line.indexOf(whl) + whl.length();
			int end_point = whole_line.indexOf(":");
			String to_evaluate = whole_line.substring(start_point, end_point);
			String[] temp = to_evaluate.split(" ");
	        SimpleBindings variables = new SimpleBindings();
			for (int i = 0; i < temp.length; i++) {
				if (temp[i].matches("[a-zA-Z]+\\(.*\\)")) {
					double result = handleArithmeticFunction(temp[i]);
					String sonuc = handleStringFunction(temp[i]);
					if (result != Double.NEGATIVE_INFINITY) {
						variables.put(temp[i], result);
					} else if (!sonuc.equals("Double.NEGATIVE_INFINITY")) {
						variables.put(temp[i], sonuc);
					}
				} else if (local_variables.containsKey(temp[i])) {
					variables.put(temp[i],local_variables.get(temp[i]));
				}
			}
			to_evaluate = "";
			for (int i = 0; i < temp.length; i++) {
					to_evaluate += " " + temp[i] ;
			}
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
			String evaluate = "eval('" + to_evaluate + "');";
			try {
				Object result = engine.eval(evaluate,variables);
				int begin_while = index;
				index++;
				String current_line = lines.get(index);
				if (Boolean.TRUE.equals(result)) {
					while (!current_line.startsWith("ENDWHILE")) {
						index = handleLine(index);
						index++;
						current_line = lines.get(index);
					}
					index = handleLine(begin_while);
				}else {
					while (!current_line.startsWith("ENDWHILE")) {
						index++;
						current_line = lines.get(index);
					}
				}
				return index+1;
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return index;
	}

	/*
	 * Handles arithmetichs after return values from the functions are applied
	 * automatically replaces varibales with their values
	 */
	private static double handleMaths(String[] line) {
		Stack<Double> numbers = new Stack<Double>();
		Stack<Operand> operands = new Stack<Operand>();
		for (int i = 0; i < line.length; i++) {
			if (isVariable(line[i]))// if it is a variable
			{
				String value = local_variables.get(line[i]);
				line[i] = value;
			}
			String s = line[i];
			if (s.matches("-*[0-9]+\\.*[0-9]*"))// if it is a number
			{
				double temp = Double.parseDouble(s);
				numbers.push(temp);
			} else if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
				Operand op = new Operand(s);
				if (operands.isEmpty() || op.getPrecedence() > operands.peek().getPrecedence()) {
					operands.push(op);
				} else {
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
			} else if (s.equals("(")) {
				Operand op = new Operand(s);
				operands.push(op);
			} else if (s.equals(")")) {
				Operand op = new Operand(s);
				while (!operands.isEmpty() && (operands.peek().operator != '(' || operands.peek().operator != ')')) {
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
				if (!operands.isEmpty() && operands.peek().operator == '(') {
					operands.pop();
				} else {
					System.out.println("Expression error");
				}

			}
		}
		while (!operands.isEmpty() && (operands.peek().operator != '(' || operands.peek().operator != ')')) {
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
		if (!operands.isEmpty() || !numbers.isEmpty()) {
			System.out.println("Expression error!");
		}
		return resultante;
	}

	/*
	 * With this function we can get functions(eg MOD(5,3) or GET_TABLE(1102)) from
	 * a string. It returns a hash map corresponding to ( (int) xth_word ,(String)
	 * function ) pair
	 */
	private static void getFunctionLocations(String s) {
		String[] input_args = s.split(" ");
		for (int i = 0; i < input_args.length; i++) {
			String current = input_args[i];
			if (current.matches("[a-zA-Z]+\\(.*\\)")) {
				function_locations.put(i, current);
				//System.out.println("Function: " + current + " location: " + i);
			}
		}
	}

	/*
	 * A function that gets a function Calls the appropriate function and returns
	 * its return value
	 */
	private static double handleArithmeticFunction(String whole_function) {
		int first_prn = whole_function.indexOf('('), second_prn = whole_function.indexOf(')');
		if(first_prn < 0 || second_prn < 0){
			return Double.NEGATIVE_INFINITY;
		}
		String function_name = whole_function.substring(0, first_prn);
		String[] function_args = whole_function.substring(first_prn + 1, second_prn).split(",");
		double result = 0;
		for (int i = 0; i < function_args.length; i++) {
			if (local_variables.containsKey(function_args[i]))
				function_args[i] = local_variables.get(function_args[i]);// wirte values of variables
		}
		if (function_name.equalsIgnoreCase("MOD")) {
			result = mod(function_args);
		} else if (function_name.equalsIgnoreCase("MIN")) {
			result = min(function_args);
		} else if (function_name.equalsIgnoreCase("MAX")) {
			result = max(function_args);
		} else if (function_name.equalsIgnoreCase("PERCENT")) {
			double arg = -1;
			if(isVariable(function_args[0])) {
				function_args[0] = local_variables.get(function_args[0]);
			}
			else if (function_locations.containsValue(function_args[0])){
				double resultante_double = handleArithmeticFunction(function_args[0]);
				String resultante_string = handleStringFunction(function_args[0]);
				if (resultante_double != Double.NEGATIVE_INFINITY)
					function_args[0] = resultante_double + "";
				else
					function_args[0] = resultante_string;
			}
			try {
				arg = Double.parseDouble(function_args[0]);
				result = percent(arg);
			} catch (Exception e) {
				System.err.println(function_args[0].toString() + " cannot be treated as a value");
				result = arg;
			}
		} else if (function_name.equalsIgnoreCase("AGI")) {// minimum wage discount
			result = agi(function_args);
		} else if (function_name.equalsIgnoreCase("NMW")) {// net minimum wage
			result = nmw(function_args);
		} else if (function_name.equalsIgnoreCase("OTW")) {// overtime wage
			result = otw(function_args);
		} else if (function_name.equalsIgnoreCase("HWF")) {// hourly working fee
			result = hwf(function_args);
		} else if (function_name.equalsIgnoreCase("ABS")) {// absolute value
			result = abs(function_args[0]);
		} else if (function_name.equalsIgnoreCase("SIGN")) {// returns sign of value (-1, 0 or +1)
			result = sign(function_args[0]);
		} else if (function_name.equalsIgnoreCase("CEIL")) {// ceiling of number
			result = ceil(function_args[0]);
		} else if (function_name.equalsIgnoreCase("FLOOR")) {// floor of number
			result = floor(function_args[0]);
		} else if (function_name.equalsIgnoreCase("FRAC")) {// decimal part of number
			result = frac(function_args[0]);
		} else if (function_name.equalsIgnoreCase("FIND")) {// index of second argument in first argument
			result = find(function_args);
		} else if (function_name.equalsIgnoreCase("LNG")) {// length of string
			result = lng(function_args[0]);
		} else {
			result = Double.NEGATIVE_INFINITY;

		}
		return result;
	}

	/*
	 * A function that gets a function of strings Calls the appropriate function and
	 * returns its return value
	 */
	private static String handleStringFunction(String whole_function) {
		int first_prn = whole_function.indexOf('('), second_prn = whole_function.indexOf(')');
		if(first_prn < 0 || second_prn < 0){
			return "Double.NEGATIVE_INFINITY";
		}
		String function_name = whole_function.substring(0, first_prn);
		String[] function_args = whole_function.substring(first_prn + 1, second_prn).split(",");
		String result = "";
		if(function_name.equals("RETURN")) {
			read_return = true;
			if(local_variables.containsKey(function_args[0])) {
				final_return = local_variables.get(function_args[0]);
			}
			else {
				Double result_dbl = handleArithmeticFunction(function_args[0]);
				if (result_dbl != Double.NEGATIVE_INFINITY){
					final_return = result_dbl + "";
				}else {
					String result_str = handleStringFunction(function_args[0]);
					if(!result_str.equals("Double.NEGATIVE_INFINITY"))
						final_return = result_str;
					else
						final_return = function_args[0];
				}
			}
			result = final_return;
		} else {
			for (int i = 0; i < function_args.length; i++) {
				if (local_variables.containsKey(function_args[i]))
					function_args[i] = local_variables.get(function_args[i]);// wirte values of variables
			}

			if (function_name.equals("SUBS")) {
				result = subs(function_args);
			} else if (function_name.equals("PRINT")) {// print args
				for (String string : function_args) {
					System.out.print(string + " ");
					System.out.println();
				}
			}else if(function_name.equals("GET")) {
				result = commandPayroll.getValue(function_args[0]);
				if(result == "") {
					result = "Double.NEGATIVE_INFINITY";
				}
			}else{
				System.out.println("Invalid or not yet implemented method call");
				result = "Double.NEGATIVE_INFINITY";
			}
		}
		return result;
	}

	private static double hourly_fee(double gross_wage, double average_working_hours) {
		return gross_wage / (30 * average_working_hours);
	}

	private static double min(String[] args) {
		double minimum = Double.MAX_VALUE;
		try {
			for (int i = 0; i < args.length; i++) {
				double temp = Double.parseDouble(args[i]);
				if (temp < minimum)
					minimum = temp;
			}
		} catch (NumberFormatException e) {
			System.err.println(args.toString() + " cannot be treated as a value");
			minimum = Double.MAX_VALUE;
		}
		return minimum;
	}

	private static double max(String[] args) {
		double maximum = Double.MIN_VALUE;
		try {
			for (int i = 0; i < args.length; i++) {
				double temp = Double.parseDouble(args[i]);
				if (temp > maximum)
					maximum = temp;
			}
		} catch (NumberFormatException e) {
			System.err.println(args.toString() + " cannot be treated as a number");
			maximum = Double.MIN_VALUE;
		}
		return maximum;
	}

	/*
	 * A basic function to find modulus/remainder -- Note: retruns -1 if user tried
	 * to send more than 2 arguments -- Note: returns -2 if user entered invalid
	 * argument(s)
	 */
	private static double mod(String[] args) {
		if (args.length != 2)
			return -1;
		double arg1, arg2;
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
		testMultiline();
	}

	public static void calculations_tests() {
		double agi = calculateAGI(2943, -1, 0, 15, 3, 50);
		System.out.println("agi " + agi);
		double net = netMinWage(2943, 14, 1, 0.759, 15, agi);
		System.out.println("net " + net);
	}

	public static void getting_functions_test() {
		Scanner sc = new Scanner(System.in);
		System.out.print(">");
		String line = sc.nextLine();
		handleSingleLine(line);
		sc.close();
	}

	
	//Works with the lines in multiline.txt
	//tests if-else situations
	public static void testMultiline() {
		File input = new File("agi_commands.txt");
		List<String> all_lines = new ArrayList<String>();
		try {
			Scanner sc = new Scanner(input);
			while (sc.hasNextLine()) {
				String ln = sc.nextLine();
				all_lines.add(ln);
				// System.out.println(ln);
			}
			String returned = readStatementLines(all_lines);
			System.out.println("STATEMENT RETURNED =>" + returned);
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ArrayList<String> getLines() {
		return lines;
	}

	public static void setLines(ArrayList<String> lines) {
		Interpreter.lines = lines;
	}

	public static String readStatementLines(List<String> statements) {
		Interpreter in = new Interpreter();
		in.setLines((ArrayList<String>) statements);

		for (int i = 0; i < lines.size() && !in.read_return; i++) {
			i = handleLine(i);
		}
		
		return Interpreter.final_return;

	}
}
