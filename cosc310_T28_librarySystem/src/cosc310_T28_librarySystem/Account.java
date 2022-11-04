package cosc310_T28_librarySystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Team 28
 * 
 *         The Account class is the subclass for User and Manager. Any activity
 *         both library users and library managers can do, such as searching a
 *         book, is implemented by this class.
 */
public class Account implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String userName, password;
    private int type;

    public Account() {

    }

    public Account(String userName, String password, int type) {
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

    /**
     * Lists books which matches a search (if selectOneBook is false), or lists
     * books which matches a search and returns a single book (if selectOneBook is
     * true). Returning a single book is useful for checking out a book, etc.
     * 
     * @param scanner
     * @param localLibraryData
     * @param selectOneBook
     * @return
     */
    ArrayList<Book> searchForABook(Scanner scanner, LocalLibraryData localLibraryData, boolean selectOneBook) {
	System.out.print("Enter all or part of the title: ");
	if (!scanner.hasNextLine()) {
	    return null;
	}
	String titleFragment = scanner.nextLine();

	System.out.println("Books found:");

	int numberFound = 0;
	Book bookFound = null;
	ArrayList<Book> bookFoundlist = new ArrayList<>();
	int i=1;
	for (Book book : localLibraryData.bookList) {
	    if (book.title.contains(titleFragment)) {
		System.out.println(i+": "+book.title + " ISBN: " + book.iSBN);
		bookFound = book;
		bookFoundlist.add(bookFound);
		i++;
		numberFound++;
	    }
	}
	if (numberFound == 0) {
	    System.out.println("Book not found.");
		return null;
	} 
	else{
	return bookFoundlist;
	}
	/*else if (selectOneBook) {
	    if (numberFound == 1) {
		return bookFound;
	    } else {
		System.out.print("Enter the ISBN of the book to select: ");
		if (!scanner.hasNextLine()) {
		    return null;
		}
		String iSBNEntered = scanner.nextLine();
		iSBNEntered.replaceAll("-", ""); // delete dashes
		for (Book book : localLibraryData.bookList) {
		    if (iSBNEntered.equals("" + bookFound.iSBN)) {
			return book;
		    }
		}
		System.out.println("Book not found.");
	    }
	}*/
	
    }

}
