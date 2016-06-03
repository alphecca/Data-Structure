import java.io.*;
////////////////////////////////2 Console Command///////////////////////////////////////
public class ConsoleCommand {
	
	static HashTable<String> hashtable;
	
	public void Insertfun(String filename) throws IOException{
		BufferedReader br =new BufferedReader(new FileReader(filename));
		hashtable=new HashTable<String>();
		int j=0;
		while(true){
			String line = br.readLine();
			j++; 
			if(line==null) break;
			for(int i=0;i<=line.length()-6;i++){
				String substring = new String();
				substring = line.substring(i, i+6);
				String position = "("+String.valueOf(j)+", ";
				position+=String.valueOf(i+1)+")";
				hashtable.add(substring, position);
			}
			
		}
		
		br.close();
	}
	void Searchfun(String Pattern) throws IOException{
		boolean there = hashtable.contains(Pattern);
		if(!there) System.out.print("(0, 0)");
		System.out.println();
	}
	
	public void Printfun(String input) throws IOException{
		hashtable.print(input);
	}
}
