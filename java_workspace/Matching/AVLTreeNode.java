import java.io.*;
import java.util.LinkedList;
import java.math.*;

public class AVLTreeNode<K extends Comparable<K>, V>{
	private K item;//key
	private LinkedList<V> valuelist;
	private AVLTreeNode<K,V> leftchild;
	private AVLTreeNode<K,V> rightchild;
	private int Height;
	
	public AVLTreeNode(){//convention
		
	}
	public AVLTreeNode (K key, V value){
		item = key;
		valuelist=new LinkedList<V>();
		valuelist.add(value);
		leftchild = null;
		rightchild = null;
		Height=1;
	}
	public K getkey(){
		return item;
	}
	public void setHeight(int height){
		Height=height;
	}
	public int getHeight(){
		return Height;
	}
	public void setRightchild(AVLTreeNode<K,V> node){
		this.rightchild=node;
	}
	public void setLeftchild(AVLTreeNode<K,V> node){
		this.leftchild=node;
	}
	public LinkedList<V> getvaluelist(){
		return valuelist;
	}
	public AVLTreeNode<K,V> getleftchild(){
		return leftchild;
	}
	public AVLTreeNode<K,V> getrightchild(){
		return rightchild;
	}
	
	public void setvalue(V value){
		valuelist.add(value);
	}
	public boolean isEmpty(){
		return getkey()==null;
	}
	
}
