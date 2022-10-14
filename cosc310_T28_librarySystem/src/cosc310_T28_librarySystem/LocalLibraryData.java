package cosc310_T28_librarySystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class LocalLibraryData implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  	public ArrayList<bookGroup> bookGroups;
  	public HashMap<String, User> userAccounts;
  	public HashMap<String, Manager> managerAccounts;
	public ArrayList<book> bookLst;
	public ArrayList<book> freeToLend;
	public ArrayList<book> ReadyToLend;
	public ArrayList<book> lended;
	
  public LocalLibraryData() {
    bookGroups = new ArrayList<>();
    userAccounts = new HashMap<>();
    managerAccounts = new HashMap<>();
    bookLst = new ArrayList<>();
    freeToLend = new ArrayList<>();
    ReadyToLend = new ArrayList<>();
    lended = new ArrayList<>();
  }

}
