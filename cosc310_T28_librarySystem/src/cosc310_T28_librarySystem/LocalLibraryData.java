package cosc310_T28_librarySystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Team 28
 * 
 *         The LocalLibraryData stores the entire collection of these objects in
 *         ArrayList and HashMap collections, and currently has no methods.
 */
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
    public ArrayList<Book> readyToLend;
    public ArrayList<Book> lended;

    public LocalLibraryData() {
	bookGroups = new ArrayList<>();
	userAccounts = new HashMap<>();
	managerAccounts = new HashMap<>();
	bookList = new ArrayList<>();
	freeToLend = new ArrayList<>();
	readyToLend = new ArrayList<>();
	lended = new ArrayList<>();
    }

}
