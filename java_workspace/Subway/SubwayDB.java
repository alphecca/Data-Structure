import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.*;

public class SubwayDB {
	
	static private ArrayList<adjacency> AjList;
	static private HashMap<Integer,String> namelist;//matrixIndex(key) : stationName(value);
	static private HashMap<String,Integer> numlist;//stationNum(key) : matrixIndex(value);
	static private int nodenum;
	
	SubwayDB(String data) throws IOException{
		
		namelist=new HashMap<Integer,String>();
		numlist= new HashMap<String,Integer>();
		
	
	Integer foo = Integer.valueOf(0);
	//System.out.println("Integer.valueOf(0) :"+Integer.valueOf(0));
	//System.out.println("Integer foo:"+foo);
		BufferedReader br = new BufferedReader(new FileReader(data));
		int matrixnum=0;
		nodenum=0;
		
		while(true){
			
			String line = br.readLine();
			String[] arg=line.split(" ");
			if(arg.length<=1) break;
			else{
	//System.out.println("arg[0]is"+arg[0]+"\targ[1]is:"+arg[1]);
				insertVertex(arg[0], arg[1], matrixnum);
				
		/*System.out.println("\tkey:"+Integer.valueOf(nodenum));
		System.out.println("[namelist] "+namelist.get(Integer.valueOf(nodenum)));
		System.out.println("\tkey:"+arg[0]);
		System.out.println("[numlist] "+numlist.get(arg[0]));
			*/	
		
				nodenum=++matrixnum;
			
			}
		}
		
		//Do NOT erase the following code:
		AjList = new ArrayList<adjacency>();
		for(int tmp=0;tmp<nodenum;tmp++) AjList.add(new adjacency());

		while(true){
			String line= br.readLine();
	//System.out.println("line is :"+line);
			if(line==null) break;
//for(String key: numlist.keySet()){
//	System.out.println("key is "+key+"#1#"+numlist.get(key));
//}
			String[] arg= line.split(" ");
//			String num = arg[0];
			Integer src= numlist.get(arg[0]);
//for(String key: numlist.keySet()){
//	System.out.println("key is "+key+"#2#"+numlist.get(key));
//}
	//System.out.print("arg[0] is "+arg[0]+"\n\t**src is ");
	//System.out.println("\t\t\t"+numlist.get(num));
			Integer dst= numlist.get(arg[1]);
	
			insertEdge(src, dst, arg[2]);
		}
		
		br.close();
		
		//if same station NAME, put edge=5;
		for(int i=0;i<nodenum;i++){
			Integer I=new Integer(i);
			for(int j=0;j<nodenum;j++){
				Integer J=new Integer(j);
				if(i!=j)
					if(namelist.get(I).equals(namelist.get(J))){//same station
						AjList.get(I).element.put(J, Integer.valueOf(5));
						//AjList.get(J).element.put(I, Integer.valueOf(5));
					}
			}
		}
		
		
	}
	public void insertVertex(String num, String name, int indexnum){
		
		namelist.put(Integer.valueOf(indexnum), name);
		numlist.put(num, Integer.valueOf(indexnum));
		
		
	}
	public void insertEdge(Integer src, Integer dst, String edge){
	//System.out.println("Put where? :"+src.intValue());
	//System.out.println("\n\tthis means "+AjList.get(src.intValue()));
		AjList.get(src.intValue()).element.put(dst, Integer.valueOf(edge));
		AjList.get(dst.intValue()).element.put(src, Integer.valueOf(edge));
		
	}
	public String shortest(String input){
		//input = start + destination
		String[] arg=input.split(" ");
		String start=arg[0];
		ArrayList<Integer> startlist=new ArrayList<Integer>();
		String dest=arg[1];
		ArrayList<Integer> destlist=new ArrayList<Integer>();
		
		if(start.equals(dest)) return arg[0]+"\n0";//start == dest.
		
		for(Integer key:namelist.keySet()){
			if(namelist.get(key).equals(start)) startlist.add(key);
			if(namelist.get(key).equals(dest)) destlist.add(key);
		}
		
		Dijkstra shortpath = new Dijkstra(AjList, nodenum);
		
		int time=Integer.MAX_VALUE;
		String paths= new String();
		
		for(int i=0;i<startlist.size();i++){
			
			Integer src=startlist.get(i);
			for(int j=0;j<destlist.size();j++){
				
				Integer dst=destlist.get(j);
				int time2=shortpath.dijkstra(src, dst);
				String walk2 = shortpath.getwalk(namelist, numlist);
				if(time2<time){
					time=time2;
					paths=walk2;
				}
			}
		}
		return paths+"\n"+time;
	
	}

}