import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB {
	MyLinkedList<Genre> list;
    public MovieDB() {
        // FIXME implement this
    	this.list = new MyLinkedList<>();
    	// HINT: MovieDBGenre 클래스를 정렬된 상태로 유지하기 위한 
    	// MyLinkedList 타입의 멤버 변수를 초기화 한다.
    }

    public void insert(MovieDBItem item) {
        // FIXME implement this
        // Insert the given item to the MovieDB.

		//리스트에 요소가 한 번도 추가되지 않은 경우
		if(list.isEmpty()){
			Genre newGenre = new Genre(item.getGenre());
			newGenre.movies.add(item.getTitle());
			list.add(newGenre);
		}

		else{
			boolean isThereGenre = false;
			for (Genre genre : list) {
				//기존에 추가된 장르인 경우
				if (genre.getItem().equals(item.getGenre())) {
					isThereGenre = true;
					//장르가 같으면 해당 장르 안에서 같은 제목의 영화가 있는지 본다.
					boolean isThereTitle = false;
					for (String title : genre.movies) {
						if (title.equals(item.getTitle())) {
							isThereTitle = true;
						}
					}
					//없으면 해당 장르 안에 영화를 추가한다.
					if (isThereTitle) {

					} else {
						genre.movies.add(item.getTitle());
					}
				}
			}
			//기존에 추가된 장르가 아닌 경우 새롭게 리스트를 만들고, 거기 그 아이템을 넣어서 그 새로운 리스트를 리스트에 추가
			if (isThereGenre) {

			} else {
				//System.err.println(" new Genre ");
				Genre newGenre = new Genre(item.getGenre());
				newGenre.movies.add(item.getTitle());
				boolean inserted =false;
				MyLinkedListIterator<Genre> it = new MyLinkedListIterator<>(list);
				int i=0;

				while(it.hasNext()){
					//System.err.println(it.hasNext());
					if(it.next().compareTo(newGenre)<0){
						//뒤에 있어야 함
					}
					else{ //앞에 있어야 함
						//it.next();
						//System.err.println(" BEFORE" + it.getCurr().getItem() +", "+ it.getCurr().getNext().getItem());
//
						if(it.getCurr() == null){
							list.insertAtFirst(newGenre);
						}
						else
							list.insertAtMiddle(newGenre, it);
//						it.getCurr().setNext(newGenre);
						inserted = true;
						list.numItems++;
						break;
					}
					i++;
				}

				if(!inserted){
					list.add(newGenre);
				}
			}
		}

    }

    public void delete(MovieDBItem item) {
        // FIXME implement this
        // Remove the given item from the MovieDB.
		MyLinkedListIterator<Genre> it2 = new MyLinkedListIterator<>(list);
		while(it2.hasNext()){
			it2.next();
			//System.err.println(it2.next().first().getGenre());
			if(it2.getCurr().name.equals(item.getGenre())) {
				it2.getCurr().movies.remove(item.getTitle());
				if(it2.getCurr().movies.isEmpty()){
					it2.remove();
				}
				break;
			}
		}

    }

    public MyLinkedList<MovieDBItem> search(String term) {
        // FIXME implement this
        // Search the given term from the MovieDB.
        // You should return a linked list of MovieDBItem.
        // The search command is handled at SearchCmd class.

    	// Printing search results is the responsibility of SearchCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.
		MyLinkedList<MovieDBItem> searchResult = new MyLinkedList<>();

		for(Genre genre : list){
			for(String title : genre.movies) {
				if (title.contains(term)) {
					searchResult.add(new MovieDBItem(genre.name, title));
				}
			}
		}

    	
        // This tracing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
    	//System.err.printf("[trace] MovieDB: SEARCH [%s]\n", term);

        return searchResult;
    }
    
    public MyLinkedList<MovieDBItem> items() {
        // FIXME implement this
        // Search the given term from the MovieDatabase.
        // You should return a linked list of QueryResult.
        // The print command is handled at PrintCmd class.

    	// Printing movie items is the responsibility of PrintCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.
		MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
		for(Genre genre : list){
			for(String title : genre.movies) {
					results.add(new MovieDBItem(genre.name, title));

			}
		}

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
       // System.err.printf("[trace] MovieDB: ITEMS\n");

    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.
        
    	return results;
    }
}

class Genre extends Node<String> implements Comparable<Genre> {
	String name;
	MovieList movies;

	public Genre(String name) {
		super(name);
		this.name = name;
		this.movies = new MovieList();
	}
	
	@Override
	public int compareTo(Genre o) {
		return this.getItem().compareTo(o.getItem());
	}

	@Override
	public int hashCode() {
		//throw new UnsupportedOperationException("not implemented yet");
		//왜쓰는거냐? 걍 대충 했는데 이렇게 하는 게 맞냐
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getItem() == null) ? 0 : this.getItem().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		//throw new UnsupportedOperationException("not implemented yet");
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genre other = (Genre) obj;
		if (getItem() == null) {
			if (other.getItem() != null)
				return false;
		} else if (!getItem().equals(other.getItem()))
			return false;
		return true;
	}
}

class MovieList implements ListInterface<String> {
	MyLinkedList<String> movielist;

	public MovieList() {
		movielist = new MyLinkedList<>();
	}

	@Override
	public Iterator<String> iterator() {
		return movielist.iterator();
	}

	@Override
	public boolean isEmpty() {
		return movielist.isEmpty();
	}

	@Override
	public int size() {
		return movielist.numItems;
	}

	@Override
	public void add(String item) {
		Node<String > last = movielist.head;
		boolean inserted = false;
		boolean already_is = false;
		while (last.getNext() != null&&!already_is) {
			if(last.getNext().getItem().equals(item)){
				already_is = true;
				break;
			}
			if(last.getNext().getItem().compareTo(item)<0){
				//받은 아이템의 이름이 뒤에 위치해야 함
			}
			else if(last.getNext().getItem().compareTo(item)>0){
				//받은 아이템의 이름이 앞에 위치해야 함
				last.insertNext(item);
				inserted = true;
				break;
			}
			last = last.getNext();
		}
		if(!inserted&&!already_is) {
			last.insertNext(item);
		}
		if(already_is){
		}
		else
			movielist.numItems += 1;
	}

	public void remove(String item){
		movielist.remove(item);
	}


	@Override
	public String first() {
		return movielist.first();
	}

	@Override
	public void removeAll() {
		movielist.removeAll();
	}


}