package cosc310_T28_librarySystem;

import java.io.Serializable;

/**
 * 
 * @author Team 28
 * 
 *         Each BookGroup object is a subcategory which stores related books
 *         e.g. multiple volumes, and has no methods.
 *
 */
public class BookGroup implements Serializable {
    private int ISBN, date, num;
    private String name, author, cate;
    // private boolean borrow[];
    private Book books[];

    public BookGroup() {

    }

    public BookGroup(int ISBN, int date, int num, String name, String author, String cate) {
	super();
	this.ISBN = ISBN;
	this.date = date;
	this.num = num;
	this.name = name;
	this.author = author;
	this.cate = cate;
	books = new Book[num];
    }

    public String getTitle() {
	return name;
    }

}
