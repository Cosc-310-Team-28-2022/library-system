package cosc310_T28_librarySystem;

import java.io.Serializable;
import java.util.Scanner;

public class Account implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String userName, password;
    private int type;
    public Account() {

    }
    public Account(String userName, String password,int type) {
	super();
	this.userName = userName;
	this.password = password;
	this.type = type;
    }
    public String getUsername() {
	return userName;
    }
    public boolean passwordEquals(String passwordEntered) {
	return password.equals(passwordEntered);
    }
    
    bookGroup searchForABook(Scanner scanner, LocalLibraryData localLibraryData) {
        System.out.print("Enter all or part of the title: ");
        if (!scanner.hasNextLine()) {
            return null;
        }
        String titleFragment = scanner.nextLine();
        for (bookGroup bookGroupToCompare : localLibraryData.bookGroups) {
            if (bookGroupToCompare.getTitle().contains(titleFragment)) {
        	return bookGroupToCompare;
            }
        }
        System.out.println("Book not found.");
        return null;
    }
 
}
