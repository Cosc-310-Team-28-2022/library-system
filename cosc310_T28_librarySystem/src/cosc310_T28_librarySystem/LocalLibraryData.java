package cosc310_T28_librarySystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class LocalLibraryData implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  ArrayList<bookGroup> bookGroups;
  HashMap<String, User> userAccounts;
  HashMap<String, Manager> managerAccounts;

  public LocalLibraryData() {
    bookGroups = new ArrayList<>();
    userAccounts = new HashMap<>();
    managerAccounts = new HashMap<>();
  }

}
