import java.io.*;
import java.math.*;
import java.util.ArrayList;

public class AVLTree<K extends Comparable<K>, V>{
	
	protected AVLTreeNode<K,V> root; // the root node
	
	public AVLTree(){
		this.root =null;
	}
	int getHeight(AVLTreeNode<K,V> node){
		if(node ==null) return 0;
		else return node.getHeight();
	}
	//////////////////////////////////////INSERT
	public AVLTreeNode<K,V> insert(K key, V value){
		root = insertNode(root, key, value);
		return root;
	}
	
	////////////////////////////////////SEARCH
	 public ArrayList<V> search(K key){
		 String newkey = key.toString();
		 ArrayList<V> valuelist = new ArrayList<V>();
		 AVLTreeNode<K,V> oldnode = root;
		 while(oldnode!=null){ 
			 valuelist = new ArrayList<V>();
			 String oldkey =oldnode.getkey().toString();
			 if(newkey.compareTo(oldkey)==0){
				 int listsize = oldnode.getvaluelist().size();
				 for(int i=0;i<listsize;i++)
					 valuelist.add(oldnode.getvaluelist().get(i));
				 break;
			 }
			 
			 else if(newkey.compareTo(oldkey)>0) oldnode=oldnode.getrightchild();
			 else oldnode=oldnode.getleftchild();
		 }
		 return valuelist;
	 }
	 ///////////////////////////////////////
	 public AVLTreeNode<K,V> insertNode(AVLTreeNode<K,V> node, K key, V value) {
		 //recursive method
		//1.Perform insertion
		if(node==null){
			return new AVLTreeNode<K,V>(key,value);
		}
		else{
			if(key.toString().compareTo(node.getkey().toString())<0){
				node.setLeftchild(insertNode(node.getleftchild(), key, value));
			}
			else if(key.toString().compareTo(node.getkey().toString())>0){
				node.setRightchild(insertNode(node.getrightchild(), key, value));
			}
			else node.setvalue(value);
		}
		
		//2.Update parent's height of this node
		node.setHeight(1+Math.max(getHeight(node.getleftchild()), getHeight(node.getrightchild())));

		int balance = balanceFactor(node);
		
		//3.check balance and call rotation methods
		if(balance>1&&key.toString().compareTo(node.getleftchild().getkey().toString())<0){
			return Rrotate(node);
		}
		if(balance<-1&&key.toString().compareTo(node.getrightchild().getkey().toString())>0){
			return Lrotate(node);
		}
		if(balance>1&&key.toString().compareTo(node.getleftchild().getkey().toString())>0){
			node.setLeftchild(Lrotate(node.getleftchild()));
			return Rrotate(node);
		}
		if(balance<-1 && key.toString().compareTo(node.getrightchild().getkey().toString())<0){
			node.setRightchild(Rrotate(node.getrightchild()));
			return Lrotate(node);
		}
		//3.check balance and return itself(if don't need to rotate)
		
		return node;
		
	}
	 
 	 void preOrder(AVLTreeNode<K,V> node, boolean foo){
	
		 if(node != null){
			 
			 if(!foo) System.out.print(" ");
			 System.out.print(node.getkey().toString());
			 preOrder(node.getleftchild(), false);
			 preOrder(node.getrightchild(), false);
		 }
	 }	 
	
 	 public AVLTreeNode<K,V> Rrotate(AVLTreeNode<K,V> node2){
			 AVLTreeNode<K,V> node1 = node2.getleftchild();
			 AVLTreeNode<K,V> temp = node1.getrightchild();
			 
			 node1.setRightchild(node2);
			 node2.setLeftchild(temp);
			 
			 //update hegiht
			 node2.setHeight(1+Math.max(getHeight(node2.getleftchild()), getHeight(node2.getrightchild())));
			 node1.setHeight(1+Math.max(getHeight(node1.getleftchild()), getHeight(node1.getrightchild())));
			 
			 return node1;
		}
		
	public AVLTreeNode<K,V> Lrotate(AVLTreeNode<K,V> node1){
			 
			 AVLTreeNode<K,V> node2= node1.getrightchild();
			 AVLTreeNode<K,V> temp=node2.getleftchild();
			 
			 node2.setLeftchild(node1);
			 node1.setRightchild(temp);
			 
			 //update height
			 node1.setHeight(1+Math.max(getHeight(node1.getleftchild()), getHeight(node1.getrightchild())));
			 node2.setHeight(1+Math.max(getHeight(node2.getleftchild()), getHeight(node2.getrightchild())));
			 
			 return node2;
		 }
		
	int balanceFactor(AVLTreeNode<K,V> node){	
			if(node==null) return 0;
			else return getHeight(node.getleftchild())-getHeight(node.getrightchild());
		}	
}
