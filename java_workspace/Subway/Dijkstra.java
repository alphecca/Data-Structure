import java.util.*;
import java.io.*;

public class Dijkstra {
	
	static private int INFINTE=Integer.MAX_VALUE;
	static private ArrayList<adjacency> AjList;
	int nodenum;
	private PriorityQueue<PQnode> PQ;
	private int[] distance;
	private int[] parent;
	private boolean[] visited;
	static private int[] walk;//shortest path sequence
	
	Dijkstra(ArrayList<adjacency> List, int nodenum){
		AjList=List;
		this.nodenum=nodenum;
	
	}
	
	public int dijkstra(Integer start, Integer destination){
		int src = start.intValue();
		int dst = destination.intValue();
		
		//initialize
		int min_distance=INFINTE;
		distance = new int[nodenum];
		parent = new int[nodenum];
		visited= new boolean[nodenum];
		for(int tmp=0;tmp<nodenum;tmp++) distance[tmp]=INFINTE;
		for(int tmp=0;tmp<nodenum;tmp++) parent[tmp]=-1;
		for(int tmp=0;tmp<nodenum;tmp++) visited[tmp]=false;
		
		//set distance of source
		distance[src]=0;
		visited[src]=true;
		parent[src]=-1;
		
		//priority queue
		PQ=new PriorityQueue<PQnode>(2,OrderByValue.INSTANCE);
		
		//dijkstra algorithm
		int vertex=src;
		
		while(vertex!=dst){
			//1.For vertex(the last visited node), relaxation its neighbors.
			for(int v=0;v<nodenum;v++){
				if(v!=vertex&&!visited[v]&&AjList.get(vertex).element.containsKey(v)){
					if(distance[v]>distance[vertex]+(AjList.get(vertex).element.get(Integer.valueOf(v)).intValue())){
						distance[v]=distance[vertex]+(AjList.get(vertex).element.get(Integer.valueOf(v)).intValue());
						parent[v]=vertex;
					}
					PQ.add(new PQnode(v, distance[v]));
				}
			}
			
			//2.get min_node and update
			if(!PQ.isEmpty()){
				int min;
				PQnode node=PQ.remove();
				while(!PQ.isEmpty()&&visited[node.key]) node=PQ.remove();
				min=node.key;
				visited[min]=true;
				min_distance=node.value;
				vertex = min;
			}
			else break;
			
		}//end of while
		
		
		//store path
		path(src, dst);
		
		
		return min_distance;//return shortest time
		
	}//end of dijkstra
	
	public int[] path(int src, int dst){
		walk=new int[nodenum];
		for(int tmp=0;tmp<nodenum;tmp++) walk[tmp]=-1;
		//return shortest path
		if(parent[dst]==-1){
			System.out.println("도달불가능 경로");
			return null;
		}
		else{
			int i=0;
			for(int tmp=dst;i<nodenum;tmp=parent[tmp]){
				
				if(tmp==src){
					walk[i]=tmp;
					break;
				}
				walk[i]=tmp;
				i++;
			}
			return walk;
		}
	}
	public String getwalk(HashMap<Integer,String> namelist, HashMap<String,Integer> numlist){
		//convert index of path to String
		String route=new String();

		int size =0;
		while(walk[size]!=-1) size++;
		if(size==2) route = namelist.get(Integer.valueOf(walk[1]))+" "+namelist.get(Integer.valueOf(walk[0]));
		
		else{
			int tmp=size-1;
			route =new String();
			String string1= namelist.get(Integer.valueOf(walk[tmp--]));
			String string2= new String();
			boolean flag=false;
			while(tmp>=0){
				string2=namelist.get(Integer.valueOf(walk[tmp--]));
				if(string2.equals(string1)){
					flag=true;
				}
				else{
					if(flag) route += " ["+string1+"]";
					else route += " "+string1;
					string1 = string2;
					flag=false;
				}
			}
			route +=" "+ string2;
			route=route.substring(1);
		}//end of else
		return route;
	}
}