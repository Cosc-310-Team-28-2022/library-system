package cosc310_T28_librarySystem;

public class User extends Account{
/**
     * 
     */
    private static final long serialVersionUID = 1L;
	//private String name;
	private book borrow_books[];
	private int borrow_num;
	private final int max_borrow=3;
	
	public User(String userName, String password, int type) {
	    super(userName, password, type);

	    borrow_books=new book[max_borrow];
	}


	private void searchBook() {}


}
