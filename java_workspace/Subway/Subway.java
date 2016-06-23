import java.io.*;
import java.util.*;

public class Subway {
	public static void main(String args[]) throws IOException {
		
		if(args.length!=1){
			System.out.println("올바르지 않은 데이터 입력입니다.");
			throw new IOException(); 
		}
		else{
			//get data text file
			SubwayDB subway = new SubwayDB(args[0]);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
			while(true){
				try{
					String input = br.readLine();
					if(input.compareTo("QUIT")==0) break;
					
					String result= subway.shortest(input);
					System.out.println(result);
					
				}catch(IOException e){
					System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
				}
			}		
		}//end of else
	}//end of main


	
}
