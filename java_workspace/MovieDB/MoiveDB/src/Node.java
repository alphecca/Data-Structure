public class Node<T> {
    private T item;
    private Node<T> next;

    public Node(T obj) {
        this.item = obj;
        this.next = null;
    }
    
    public Node(T obj, Node<T> next) {
    	this.item = obj;
    	this.next = next;
    }
    
    public final T getItem() {
    	return item;
    }
    
    public final void setItem(T item) {
    	this.item = item;
    }
    
    public final void setNext(Node<T> next) {
    	this.next = next;
    }
    
    public Node<T> getNext() {
    	return this.next;
    }
    
    public final void insertNext(T obj) {//insert new node 'inserting' right after the instance.
    	Node<T> inserting = new Node<T>(obj);
    	
    	inserting.setNext(this.getNext());
    	this.setNext(inserting);
    }
    
    public final void removeNext() {//remove a node right after the instance.
    	if(this.getNext()==null) return;//there's no node to remove.
    	else  	this.setNext(this.getNext().getNext());
    }
}