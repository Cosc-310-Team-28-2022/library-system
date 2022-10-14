package cosc310_T28_librarySystem;

import java.io.Serializable;
import java.util.ArrayList;

public class LocalLibraryData implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  ArrayList<bookGroup> bookGroups;
  ArrayList<User> userAccounts;
  ArrayList<Manager> managerAccounts;

  public LocalLibraryData() {
    bookGroups = new ArrayList<>();
    userAccounts = new ArrayList<>();
    managerAccounts = new ArrayList<>();
  }

}
