package cosc310_T28_librarySystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class LocalLibraryData implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  	public ArrayList<BookGroup> bookGroups;
  	public HashMap<String, User> userAccounts;
  	public HashMap<String, Manager> managerAccounts;
	public ArrayList<Book> bookList;
	public ArrayList<Book> freeToLend;
	public ArrayList<Book> ReadyToLend;
	public ArrayList<Book> lended;
	
  public LocalLibraryData() {
    bookGroups = new ArrayList<>();
    userAccounts = new HashMap<>();
    managerAccounts = new HashMap<>();
    bookList = new ArrayList<>();
    freeToLend = new ArrayList<>();
    ReadyToLend = new ArrayList<>();
    lended = new ArrayList<>();
  }

}
