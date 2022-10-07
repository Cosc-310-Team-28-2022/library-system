package cosc310_T28_librarySystem;

public class book{
private bookGroup bg;	
private int ISBN, date, ID;
private String name, author, cate;
private boolean borrow=false;

	public book() {
		
	}
	public book(bookGroup bg, int ISBN, int date, int ID, String name, String author, String cate, boolean borrow) {
		super();
		this.bg=bg;
		this.ISBN=ISBN;
		this.date=date;
		this.ID=ID;
		this.name=name;
		this.author=author;
		this.cate=cate;
		this.borrow=borrow;
	}
	
	
}
