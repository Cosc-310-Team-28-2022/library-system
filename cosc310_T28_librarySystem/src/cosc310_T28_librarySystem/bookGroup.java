package cosc310_T28_librarySystem;

public class bookGroup {
	private int ISBN, date, num;
	private String name, author, cate;
	//private boolean borrow[];
	private book books[];
public bookGroup() {
		
	}
	public bookGroup(int ISBN, int date, int num, String name, String author, String cate) {
		super();
		this.ISBN=ISBN;
		this.date=date;
		this.num=num;
		this.name=name;
		this.author=author;
		this.cate=cate;
		books=new book[num];
		}
	
	
	
}
