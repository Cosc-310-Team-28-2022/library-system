package cosc310_T28_librarySystem;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Serializable {
    BookGroup bg;	
    long iSBN;
    LocalDate date;
    int id;
    String title, author, cate;
    User holder=null;
    boolean borrow=false;

	public Book() {
		
	}
	public Book(BookGroup bg, long iSBN, LocalDate date, int id, String name, String author, String cate, boolean borrow) {
		super();
		this.bg=bg;
		this.iSBN=iSBN;
		this.date=date;
		this.id=id;
		this.title=name;
		this.author=author;
		this.cate=cate;
		this.borrow=borrow;
	}
	
	
}
