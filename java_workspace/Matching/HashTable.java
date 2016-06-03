import java.util.ArrayList;
import java.io.*;
import java.lang.*;
import java.lang.*;//hashcode 만들어줄것
import java.util.StringTokenizer;
///////////////////////////////3 Hash Table///////////////////////////////////////
public class HashTable<E> {
	
	ArrayList<AVLTree<String, String>> hashing;
	
	HashTable(){
		hashing =new ArrayList<AVLTree<String, String>>();
		for(int i=0;i<100;i++)
			hashing.add(new AVLTree<String, String>());
	}
	/////////////////////////////
	void add(E word, E position){
		int ascii=hashcode(word.toString());
		AVLTree<String, String> tree=hashing.get(ascii);
		tree.root=tree.insert(word.toString(), position.toString());		
	}
	int hashcode(String word){//calculate hashing value
		int ascii=0;
		for(int i=0;i<word.length();i++)
			ascii+=word.toString().charAt(i);
		ascii%=100;
		return ascii;		
	}
	//////////////////////////
	void print(String indexnum) throws IOException{
		int index= Integer.parseInt(indexnum);
		if(index<0||index>99) throw new IOException();
		else{
			AVLTree<String, String> slot=hashing.get(index);
			if(slot.root==null) System.out.print("EMPTY");
			else{
				//System.out.println("**debug: slot.root="+slot.root.getkey());
				slot.preOrder(slot.root, true);
			}
			System.out.println();
		}
	}
	///////////////////
	boolean contains(String pattern) throws IOException{//search method
		
		boolean there = true;//if there's no search pattern, return false
		
		//1 split pattern into 6-character strings
		ArrayList<String> target = new ArrayList<String>();
		for(int i=0;i<=pattern.length()-6;i++) target.add(pattern.substring(i, i+6));
		
		//2 Get value_list of target[i]
		//*NOTE: I'll get value_list from the target[last], to the target[first]
		ArrayList<String> valuelist1;
	
		int ascii = hashcode(target.get(target.size()-1));
	
		valuelist1=hashing.get(ascii).search(target.get(target.size()-1));
	
		if(valuelist1==null|| valuelist1.isEmpty()) there=false;
		if(there&& target.size()>0){
			//3 copmare
			for(int i=target.size()-2;i>=0;i--){
				ArrayList<String> valuelist2=hashing.get(hashcode(target.get(i))).search(target.get(i));
	
				valuelist1 = checkseq(valuelist1, valuelist2);
				
				if(valuelist1==null||valuelist1.isEmpty()){
					there = false;
					break;
				}
			}
		}
		//4 Print if there==true
		if(valuelist1!=null&&!valuelist1.isEmpty()){
			System.out.print(valuelist1.get(0));
			if(valuelist1.size()>1&&!valuelist1.get(1).isEmpty()){
				for(int temp=1;temp<valuelist1.size();temp++){
					System.out.print(" "+valuelist1.get(temp));
				}
			}
		}
		return there;
	}
	ArrayList<String> checkseq(ArrayList<String> list1, ArrayList<String> list2){
		//compare method
		
		ArrayList<String> finlist=new ArrayList<String>();
		String position1 = new String();
		String position2 = new String();
		int x1,x2,y1,y2; 
		//IF x1==x2&y2=y1-1, return true;
		//Also copy x1 and y2 to final list(finlist)
		
		if(list1.isEmpty()|| list2.isEmpty()) return null;
		for(int i=0;i<list1.size();i++){
			position1= list1.get(i);
			String[] array;
			array=position1.split(",");
			x1=Integer.parseInt(array[0].substring(1));
			y1=Integer.parseInt(array[1].substring(1, array[1].length()-1));
			
			for(int k=0;k<list2.size();k++){
				position2=list2.get(k);
				array=position2.split("(,)");
				x2=Integer.parseInt(array[0].substring(1));
				y2=Integer.parseInt(array[1].substring(1, array[1].length()-1));
				
				if(x1==x2&&y2==(y1-1)){
					finlist.add("("+String.valueOf(x1)+", "+String.valueOf(y2)+")");
					break;				}
			}
		}
		return finlist;
	}
}