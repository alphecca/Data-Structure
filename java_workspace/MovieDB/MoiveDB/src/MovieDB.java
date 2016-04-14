
/***************************************************************************************
 * {@code class MovieDB} is one linked-list(called Biglist) which has {@code MovieLists} as its nodes.
 **/
public class MovieDB{
	MyLinkedList<MovieList> Biglist;
	
	public MovieDB(){
		Biglist = new MyLinkedList<MovieList>();
	}
	/**
	 * insert new {@code MovieDBItem} in {@code MovieList} of {@code Biglist} according to its genre&title
	 * @param MovieDBItem type
	 */
	public void insert(MovieDBItem item){
		String genrename = item.getGenre();
		String titlename = item.getTitle();
		Node<MovieList> now = Biglist.head;
		
		while(now.getNext()!=null){
			if(genrename.compareTo(now.getNext().getItem().genrename) < 0) break;
			else now = now.getNext();
		}
		
		if(now.getItem() == null || !genrename.equals(now.getItem().genrename)) {//new genre
			MovieList newmovielist = new MovieList(genrename);
			newmovielist.add(titlename);
			now.insertNext(newmovielist);
		}
		else	now.getItem().add(titlename);//same genre
		
	}
	/**
	 * delete entered {@code MovieDBItem} if it exists in {@code Biglist}
	 * @param MovieDBItem type
	 */
	public void delete(MovieDBItem item){
		String genrename = item.getGenre();
		String titlename = item.getTitle();
		Node<MovieList> now = Biglist.head;
		
		while(now.getNext()!=null){
			if(genrename.compareTo(now.getNext().getItem().genrename) < 0) break;
			else now = now.getNext();
		}
		
		if(now.getItem() == null || !genrename.equals(now.getItem().genrename)) ;//no such genre
		else {//such genre exists
			now.getItem().delete(titlename);
		}
	}
	/**
	 * search {@code MovieDBItem} which has entered {@code String}.
	 * @param targeted string type {@code String}
	 * @return MyLinkedList which has {@code MovieDBItem}.
	 */
	public MyLinkedList<MovieDBItem> search(String term){
		MyLinkedList<MovieDBItem> QueryResult=new MyLinkedList<MovieDBItem>();
	
		for(MovieList temp : Biglist){
	    		for(String temptitle: temp){
	    			if(temptitle.contains(term))	QueryResult.add(new MovieDBItem(temp.genrename, temptitle));
	    		}
		}
		
		return QueryResult;
	}
	/**
	 * gather all {@code MovieDBItem} in Biglist, in sorted state.
	 * @return MyLinkedList which has all {@code MovieDBItem} in {@code Biglist}.
	 */
	public MyLinkedList<MovieDBItem> items(){
		MyLinkedList<MovieDBItem> QueryResult=new MyLinkedList<MovieDBItem>();
		
		if(Biglist.isEmpty()) return QueryResult=null;//Big list is empty
		else{//Big list is not empty
	    	for(MovieList temp : Biglist){	
	    		for(String temptitle: temp){
	    			QueryResult.add(new MovieDBItem(temp.genrename, temptitle));
	    		}
			}
			return QueryResult;
		}
	}
}//class MovieDB
/***************************************************************************************
 * {@code MovieList} is a linked-list which constitutes a MovieDB.
 * @Detail
 * It has {@code genrename} and {@code node}s which contains {@code title} String.
 */
class MovieList extends MyLinkedList<String>{
	String genrename;
	
	public MovieList(String genre){
		genrename = genre;
	} 
	/**
	 * make new node which has String {@code title} and
	 * insert the node existing linked list "in order". 
	 * @param
	 */
	@Override
	public void add(String title) {
		Node<String> nowtitle = this.head;
		
		while(nowtitle.getNext()!=null){
			if(title.compareTo(nowtitle.getNext().getItem())<0) break;
			else nowtitle = nowtitle.getNext();
		}
		if(title.equals(nowtitle.getItem())) ;//same title
		else	nowtitle.insertNext(title);//new title
		
	}
	/**
	 * delete a node which has String {@code title} if linked-list has it
	 * otherwise, do nothing
	 * @param 
	 */
	public void delete(String title){
		Node<String> nowtitle= this.head;
		
		while(nowtitle.getNext()!=null){
			if(title.compareTo(nowtitle.getNext().getItem())<=0) break;
			else nowtitle=nowtitle.getNext();
		}
		if(nowtitle.getNext()!=null&&title.equals(nowtitle.getNext().getItem())) nowtitle.removeNext();//that title exists
		else ;//no such title
	}
}//class MovieList