
import java.io.*;
import java.util.Stack;
import java.lang.Math;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CalculatorTest {
	
	static Stack<String> convertstack = new Stack<String>();
	static Stack<String> calculstack =new Stack<String>();

	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;

				command(input);
			}
			catch (Exception e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input)
	{
		try{
			String expression=convert(input);//convert to postfix
			
			expression = expression.substring(0, expression.length()-1);
			calcul(expression);//calculate the postfix expression
			
			//print
			System.out.println(expression);
			System.out.println(calculstack.pop());
		}catch(Exception e){
			System.out.println("ERROR");
		}

	}
	private static String convert(String input) throws  Exception{
		String result=new String();
		String inexpression = checking(input);//filter the wrong expression
		inexpression.replaceAll("\\s", "");
		
		result = stacking(inexpression, result);//change to postfix expression by stacking
		if(result.contains("(")) throw new Exception();
		else return result;
	}
	private static void calcul(String input)throws Exception {
		//calculate the result using stack and recursive call
		String temp=new String();
		long operand1, operand2;
		int i=0;

		if(!input.isEmpty()){
			if(input.charAt(0)>='0'&&input.charAt(0)<='9'){
				while(i<input.length()&&input.charAt(i)>='0'&&input.charAt(i)<='9'){
					temp+=input.charAt(i);
					i++;
				}
				calculstack.push(temp);
				input=input.substring(i);
				calcul(input);
			}
			else{
				if(input.charAt(0) != ' '){

				operand1 = Long.parseLong(calculstack.pop());

				switch(input.charAt(0)){
				case('~'): calculstack.push(String.valueOf(operand1*-1));
				break;
				case('^'): operand2 = Long.parseLong(calculstack.pop());
				if(operand2 == 0 && operand1 <0) throw new Exception();
					calculstack.push(String.valueOf((long)Math.pow(operand2, operand1)));
				break;
				case('*'): operand2 = Long.parseLong(calculstack.pop());
					calculstack.push(String.valueOf(operand2*operand1));
				break;
				case('/'):
				if(operand1==0) throw new Exception(); operand2 = Long.parseLong(calculstack.pop());
				calculstack.push(String.valueOf(operand2/operand1));
				break;
				case('%'):
				if(operand1==0) throw new Exception(); operand2 = Long.parseLong(calculstack.pop());
				calculstack.push(String.valueOf(operand2%operand1));
				break;
				case('+'): operand2 = Long.parseLong(calculstack.pop());
				calculstack.push(String.valueOf(operand2+operand1));
				break;
				case('-'): operand2 = Long.parseLong(calculstack.pop());
				calculstack.push(String.valueOf(operand2-operand1));
				break;
				default:
					throw new Exception();
				}
				}
				if(input.length()>1)
				{
				input=input.substring(1);
				calcul(input);
				}
			}
		}
	}

	private static String checking(String input) throws Exception{
		//Check the input string correct (otherwise throw exception)
		//& modify some factors, such as deleting tokens or whitespace, changing unary minus(-) to ~.
		String result=new String();
		int i=0;
		Pattern p1 = Pattern.compile("([0-9]+)");
		Matcher m1 = p1.matcher(input);
		if(!m1.find())	throw new Exception();
		String[] arr= (input+" ").split("\\d+");

		if(!arr[0].isEmpty()){
			arr[0] = arr[0].replaceAll("\\s",""); 
			Pattern p2 = Pattern.compile("^[(-]*$");
			Matcher m2 = p2.matcher(arr[0]);
			if(!m2.find()){
				throw new Exception();
			}
			if(arr[0].contains("-")) arr[0] = arr[0].replace('-','~');
			result += arr[0];
		}

		result+=m1.group(1);

		for(i=1;i<arr.length;i++){
			arr[i] = arr[i].replaceAll("\\s","");

			if(i==arr.length-1){

				if(!arr[i].isEmpty()){
				Pattern p3 = Pattern.compile("^\\)*$");
				Matcher m3 = p3.matcher(arr[i]);
				if(!m3.find()) throw new Exception();
				result += arr[i];
					}
				}

			else{
				if(!arr[i].isEmpty()){
					Pattern p4 = Pattern.compile("^(\\)*)([+*^/%-]{1})([(-]*)$");
					Matcher m4 = p4.matcher(arr[i]);
					if(!m4.find())
						throw new Exception();

				if(m4.group(3)!=null && m4.group(3).contains("-"))
					arr[i] = m4.group(1)+m4.group(2)+m4.group(3).replace('-', '~');
				result+=arr[i];
			}
				else throw new Exception();
			m1.find();
			result+=m1.group(1);
			}
		}
		return result;
	}
	private static String stacking(String input, String result) throws Exception{
		//recursive method. String result have the result string ordered as postfix  
		String temp=new String();
		int i=0;
		
		if(!input.isEmpty()){
			temp=new String();
			if(input.charAt(0)=='('){
				convertstack.push("(");
				result =stacking(input.substring(1),result);
			}
			else if(input.charAt(0)==')'){
				while(!convertstack.peek().equals("("))
					result += convertstack.pop()+" ";
				
				if(!convertstack.peek().equals("("))
					throw new Exception();
				else{
					convertstack.pop();
					result = stacking(input.substring(1), result);
				}
			} // "("
			
			else if(input.charAt(0)=='~'||input.charAt(0)=='+'||input.charAt(0)=='-'||input.charAt(0)=='*'||input.charAt(0)=='^'||input.charAt(0)=='%'||input.charAt(0)=='/'){
				switch(input.charAt(0)){
					
					case('^') :
					break;
					
					case('~') :
						while(!convertstack.isEmpty()){
							if(convertstack.peek().equals("^"))
								result += convertstack.pop()+" ";
						}
					break;
					
					case('*') :
					case('/') :
					case('%') :
						while(!convertstack.isEmpty()){
							if(convertstack.peek().equals("^")||convertstack.peek().equals("~"))
								result+=convertstack.pop()+" ";
					}
					
					break;
					case('+') :
					case('-') : while(!convertstack.isEmpty()){
						if(convertstack.peek()=="(") break;
						result+=convertstack.pop()+" ";
					}
				}
				Character cr =new Character(input.charAt(0));
				convertstack.push(cr.toString());
				input=input.substring(1);
				result = stacking(input,result);
			}
			else if(input.charAt(0)>='0'&&input.charAt(0)<='9'){//operands
				temp=new String();
				temp+=input.charAt(0);
				for(i=1;i<input.length();i++){
					if(input.charAt(i)>='0'&&input.charAt(i)<='9')	temp+=input.charAt(i);
					else break;
				}
				result += temp+" ";
				input = input.substring(i);
				result = stacking(input, result);
			}
			else throw new Exception();
			}
		else{//When the end of the string
			while(!convertstack.isEmpty()) {
			result += convertstack.pop()+" ";
			}
		}
			return result;
	}
}//end of class
