import java.io.*;

import javax.xml.crypto.Data;
////////////////////////////////1 Matching Method///////////////////////////////////////
public class Matching
{
	public static 	void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				command(input);
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}
	private static void command(String input) throws IOException
	{
		if(input.length()<3) throw new IOException();
		else{
			ConsoleCommand function = new ConsoleCommand();
			if(input.startsWith("< ")){
				function.Insertfun(input.substring(2));
			}	
			else if(input.startsWith("? ")){
				function.Searchfun(input.substring(2));
			}
			else if(input.startsWith("@ ")){
				function.Printfun(input.substring(2));
			}
		}
	}
}