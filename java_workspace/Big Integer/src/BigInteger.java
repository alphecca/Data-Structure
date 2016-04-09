import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
/*2015-10033 DaYun Kim
 * Java Practice #1_BigInteger.java
 * This calculates add, subtract, multiply of two operands of which ranges are too large above int.
 * The output is a result of string with a sign.
 */
public class BigInteger
{
	boolean sign=true;//stores a sign of the number
	String digit;//stores the numbers by String type
	
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("(.[0-9]*)([-+*])(.[0-9]*)");
    public static final int num_length=100;
     
    //##constructor
    public BigInteger(int[] num){//change an integer array(in reverse) to a string type(in right direction)
    	int i;
  		StringBuffer tmp=new StringBuffer();
     	for(i=0;i<num.length;i++) tmp.append(num[num.length-i-1]);
     	String input=tmp.toString();
     	Pattern p1= Pattern.compile("^0*$");
     	Matcher n= p1.matcher(input);
     	if(!n.find()){
     		Pattern p = Pattern.compile("(0*)(.*)");
     		Matcher m = p.matcher(input);
     		if(!m.find()) System.out.println("MSG_INVALID_INPUT\n");
     		String result=m.group(2);
     		this.digit=result;
     	}
     	else this.digit="0";
    }
    public BigInteger(String s)
    {
    	String regex = "(\\D?)([0-9]*)";
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(s);
    	if(!m.find()) System.out.println("MSG_INVALID_INPUT\n");
    	String temp=m.group(1);
    	if(temp.equals("-"))	this.sign=false;    	
    	else this.sign=true;
    	this.digit=m.group(2);
    }
    //##methods
    public BigInteger add(BigInteger big)
    {	  	
    	int len1=this.digit.length();
    	int len2=big.digit.length();
    	int[] num1=new int[num_length];
    	int[] num2=new int[num_length];
    	int[] num3=new int[num_length+1];
    	int[] cin=new int[num_length+1];
    	int i,len;
    	
    	//num1, num2 have digits in reverse
    	if(len1>=len2) len=len1;
    	else len=len2;
     	for(i=0;i<len1;i++)	num1[len1-1-i]=Character.getNumericValue(this.digit.toCharArray()[i]);
    	for(i=0;i<len2;i++)	num2[len2-1-i]=Character.getNumericValue(big.digit.toCharArray()[i]);
        //add operation
    	for(i=0;i<cin.length;i++)	cin[i]=0;
    	for(i=0;i<len;i++){
    		num3[i]=(num1[i]+num2[i]+cin[i])%10;
    		cin[i+1]=(num1[i]+num2[i]+cin[i])/10;
    	}
    	num3[len]=cin[len];
    	
    	BigInteger result=new BigInteger(num3);
    	//check sign
    	result.sign=this.sign;     	
    	return result;
    }
 
    public BigInteger subtract(BigInteger big)
    {
    	int[] num1=new int[num_length];
    	int[] num2=new int[num_length];
    	int[] num3=new int[num_length];
    	int i,j,k=0, len;
    	boolean sign=true;
    	//delete unnecessary zeros in front of significant digit. 
    	Pattern p = Pattern.compile("(0*)(.*)");
 		Matcher m1 = p.matcher(this.digit);
 		Matcher m2 = p.matcher(big.digit);
 		if(!m1.find()) System.out.println("MSG_INVALID_INPUT\n");
 		if(!m2.find()) System.out.println("MSG_INVALID_INPUT\n");
 		this.digit=m1.group(2);
 		big.digit=m2.group(2);
 		int len1=this.digit.length();
    	int len2=big.digit.length();
 
    	//num1 have bigger digits in reverse, and num2 have smaller digits in reverse.
    	if(len1>len2){
    		len=len1;
    		sign=this.sign;
    		for(i=0;i<len1;i++)	num1[len1-i-1]=Character.getNumericValue(this.digit.toCharArray()[i]);
        	for(i=0;i<len2;i++) num2[len2-i-1]=Character.getNumericValue(big.digit.toCharArray()[i]);
        }
    	else if(len1<len2){
    		len=len2;
    		len2=len1;
    		len1=len;
    		sign=!this.sign;
    		for(i=0;i<len1;i++)	num1[len1-i-1]=Character.getNumericValue(big.digit.toCharArray()[i]);
    		for(i=0;i<len2;i++)	num2[len2-i-1]=Character.getNumericValue(this.digit.toCharArray()[i]);
       	}
    	else{//len1==len2
    		len=len1;
    		sign=this.sign;
    		for(i=0;i<len;i++)	num1[len-i-1]=Character.getNumericValue(this.digit.toCharArray()[i]);
        	for(i=0;i<len;i++)	num2[len-i-1]=Character.getNumericValue(big.digit.toCharArray()[i]);
    		for(i=len-1;0<=i;i--){
    			if(num1[i]<num2[i]){
    					sign=!this.sign;
    					for(j=0;j<len2;j++) num1[len2-j-1]=Character.getNumericValue(big.digit.toCharArray()[j]);
	    		    	for(j=0;j<len1;j++)	num2[len1-j-1]=Character.getNumericValue(this.digit.toCharArray()[j]);
    		    		i=len-1;
    		    		break;
				}
    			else if(num1[i]>num2[i])	break;
    			else ;
			}
    		//to check exception case :result is zero
    		for(i=len-1;0<=i;i--){
    			if(num1[i]!=num2[i]) break;
    			else k++;
    		}
    	}
    	//subtract operation
    	if(k==len) for(i=0;i<len;i++){//exception case: result is zero
    		num3[i]=0;
    		sign = true;
    	}
    	else{//other cases
	    	for(i=0;i<len-1;i++){
	    		if(num1[i]>=num2[i]){
	    			num3[i]=num1[i]-num2[i];
	    		}
	    		else{
	    			num3[i]=10+num1[i]-num2[i];
	    			--num1[i+1];
	    		}
	    	}
	    	num3[len-1]=num1[len-1]-num2[len-1];
    	}
    	BigInteger result = new BigInteger(num3);
    	//check sign
    	result.sign = sign;
    	return result;
    }
 
    public BigInteger multiply(BigInteger big)
    {
    	int len1=this.digit.length();
    	int len2=big.digit.length();
    	int[] num1=new int[num_length];
    	int[] num2=new int[num_length];
    	int[] num3=new int[2*num_length];
    	int[] cin=new int[2*num_length];
    	int i,j,k,len;
    	
    	//num1 has longer digits in reverse, and num2 has shorter digits in reverse.
    	if(len1>=len2){
    		len =len1;
    		for(i=0;i<len1;i++)	num1[len1-i-1]=Character.getNumericValue(this.digit.toCharArray()[i]);
    		for(i=0;i<len2;i++)	num2[len2-i-1]=Character.getNumericValue(big.digit.toCharArray()[i]);
    	}
    	else{
    		len=len2;
    		len2=len1;
    		len1=len;
    		for(i=0;i<len1;i++)	num1[len1-i-1]=Character.getNumericValue(big.digit.toCharArray()[i]);
    		for(i=0;i<len2;i++)	num2[len2-i-1]=Character.getNumericValue(this.digit.toCharArray()[i]);
    	}
    	//multiply operation
    	for(i=0;i<len2;i++){
			for(k=0;k<2*len;k++)	cin[k]=0;
			for(j=0;j<len1;j++){
				k=i+j;
				num3[k]+=(num1[j]*num2[i]+cin[j])%10;
				if(j==len1-1) num3[k+1]+=(num1[j]*num2[i]+cin[j])/10;
				else cin[j+1]=(num1[j]*num2[i]+cin[j])/10;
				if(num3[k]>=10){
					num3[k+1]+=num3[k]/10;
					num3[k]=num3[k]%10;    					
				}
				else ;
			}		
		}
    	BigInteger result=new BigInteger(num3);
    	//check sign
    	if(this.sign==big.sign) result.sign=true; 
    	else result.sign=false;
      	return result;
    }
   
    public String toString()//override
    {
    	if(this.sign==false&&!this.digit.equals("0")) return "-"+this.digit;
    	else return this.digit;
    }
 
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {	
    	BigInteger result;
    	String arg1, arg2, operator;
    	
    	//parsing
    	String str = input.replaceAll("\\s", "");//delete all whitespace
    	Pattern p = EXPRESSION_PATTERN;
    	Matcher m = p.matcher(str);
    	if(!m.find()) System.out.println("MSG_INVALID_INPUT\n");
    	arg1 = m.group(1);
    	arg2 = m.group(3);
    	operator = m.group(2);
    	
     	BigInteger num1 = new BigInteger(arg1);
        BigInteger num2 = new BigInteger(arg2);
    	//to decide which operation to do
        if(operator.equals("*")){
         result= num1.multiply(num2); 
        }
        else if(operator.equals("+")){
        	if(num1.sign==num2.sign)	 result=num1.add(num2);
        	else result=num1.subtract(num2);
        }
        else if(operator.equals("-")){
        	if(num1.sign !=num2.sign) result=num1.add(num2);
        	else  result=num1.subtract(num2);
        }
        else result=new BigInteger("MSG_INVALID_INPUT\n");
        
        return result;
    }
  
    public static void main(String[] args) throws Exception
    {
    	//InputStream = byte, InputStreamReader = character, BufferReader = string;
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }

    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
        if (quit)
        {
        	System.out.println("\n");
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
            return false;
        }
    }
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}//end of class BigInteger.