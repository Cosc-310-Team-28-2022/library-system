package cosc310_T28_librarySystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Manager extends Account {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Manager() {}
    void addBook(Scanner scanner, LocalLibraryData localLibraryData) {
        String title = null;
        while (title == null) {
            System.out.print("Title of book to add: ");
            if (!scanner.hasNextLine()) {
                return;
            }
            String titleEntered = scanner.nextLine();
            if (!titleEntered.matches("^[a-zA-Z0-9 _-`~!@#$%^&*()=+\\[{\\]}\\\\|;:'\",<.>/?]*$")) {
        	System.out.println("The title must contain only letters, numbers, _-`~!@#$%^&*()=+[{]}\\|;:'\",<.>/? symbols, or spaces.");
            } else if (!(1 <= titleEntered.length() && titleEntered.length() <= 99)) {
        	System.out.println("The title must be 1 to 99 characters long.");
            } else {
        	title = titleEntered;
            }
        }
        long iSBN = -1;
        while (iSBN == -1) {
            System.out.print("Enter the ISBN or \"unknown\": ");
            if (!scanner.hasNextLine()) {
                return;
            }
            String iSBNEntered = scanner.nextLine();
            iSBNEntered.replaceAll("-", ""); //delete dashes
            if (iSBNEntered.matches("^unknown$")) {
        	iSBN = -1;
        	break;
            } else if (!iSBNEntered.matches("^[0-9]*$")) {
        	System.out.println("The ISBN must contain only numbers and dashes.");
            } else if (10 != iSBNEntered.length() && 13 != iSBNEntered.length()) {
        	System.out.println("The ISBN must be 10 or 13 digits long.");
            } else {
        	iSBN = Long.parseUnsignedLong(iSBNEntered);
            }
        }
        LocalDate dateOfPublication = null;
        while (dateOfPublication == null) {
            System.out.print("Date of publication in yyyy-mm-dd format (e.g. 2022-10-15), or \"unknown\": ");
            if (!scanner.hasNextLine()) {
                return;
            }
            String dateEntered = scanner.nextLine();
            if (dateEntered.matches("^unknown$")) {
                dateOfPublication = null;
                break;
            } else if (!dateEntered.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d$")) {
        	System.out.println("Not recognized as yyyy-MM-dd format.");
            } else {
                //The only way to check if a format is valid is to catch exceptions.
                //See https://www.baeldung.com/java-string-valid-date
        	try {
                    dateOfPublication = LocalDate.parse(dateEntered, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        	} catch (DateTimeParseException e) {
                    System.out.println("Not recognized as yyyy-MM-dd format.");
        	}
            }
        }
        int id = -1;
        while (id == -1) {
            System.out.print("Enter an id to give the book or \"unknown\": ");
            if (!scanner.hasNextLine()) {
                return;
            }
            String idEntered = scanner.nextLine();
            if (idEntered.matches("^unknown$")) {
        	id = -1;
        	break;
            } else if (!idEntered.matches("^[0-9]*$")) {
        	System.out.println("The id must contain only numbers.");
            } else if (!(1 <= idEntered.length() && idEntered.length() <= 9)) {
        	System.out.println("The id must be 1 to 9 digits long.");
            } else {
        	id = Integer.parseUnsignedInt(idEntered);
            }
        }
        String author = null;
        while (author == null) {
            System.out.print("Enter the author of book to add or \"unknown\": ");
            if (!scanner.hasNextLine()) {
                return;
            }
            String authorEntered = scanner.nextLine();
            if (authorEntered.matches("^unknown$")) {
        	author = null;
        	break;
            } else if (!authorEntered.matches("^[a-zA-Z0-9 _-`~!@#$%^&*()=+\\[{\\]}\\\\|;:'\",<.>/?]*$")) {
        	System.out.println("The author must contain only letters, numbers, _-`~!@#$%^&*()=+[{]}\\|;:'\",<.>/? symbols, or spaces.");
            } else if (!(1 <= authorEntered.length() && authorEntered.length() <= 99)) {
        	System.out.println("The author must be 1 to 99 characters long.");
            } else {
        	author = authorEntered;
            }
        }
        String category = null;
        while (category == null) {
            System.out.print("Enter the category of book to add or \"unknown\": ");
            if (!scanner.hasNextLine()) {
                return;
            }
            String categoryEntered = scanner.nextLine();
            if (categoryEntered.matches("^unknown$")) {
        	author = null;
        	break;
            } else if (!categoryEntered.matches("^[a-zA-Z0-9 _-`~!@#$%^&*()=+\\[{\\]}\\\\|;:'\",<.>/?]*$")) {
        	System.out.println("The category must contain only letters, numbers, _-`~!@#$%^&*()=+[{]}\\|;:'\",<.>/? symbols, or spaces.");
            } else if (!(1 <= categoryEntered.length() && categoryEntered.length() <= 99)) {
        	System.out.println("The category must be 1 to 99 characters long.");
            } else {
        	category = categoryEntered;
            }
        }
	Book book = new Book(null, iSBN, dateOfPublication, id, title, author, category, false);
        localLibraryData.bookList.add(book);
        localLibraryData.freeToLend.add(book);
        System.out.println("The book has been added successfully.");
    }
    void delBook() {}
    void searchUser() {}
    void changeStatus() {}
    void checkoutBook(Scanner scanner, LocalLibraryData localLibraryData) {
	Book bookToCheckout = searchForABook(scanner, localLibraryData, true);
	if (bookToCheckout == null) {
	    return;
	}
	if (localLibraryData.freeToLend.contains(bookToCheckout)) {
	    localLibraryData.freeToLend.remove(bookToCheckout);
	    localLibraryData.lended.add(bookToCheckout);
	    System.out.println("Checkout successful. Title = " + bookToCheckout.title + " ISBN = " + bookToCheckout.iSBN);
	} else if (localLibraryData.readyToLend.contains(bookToCheckout)) {
	    localLibraryData.readyToLend.remove(bookToCheckout);
	    localLibraryData.lended.add(bookToCheckout);
	    System.out.println("Checkout successful. Title = " + bookToCheckout.title + " ISBN = " + bookToCheckout.iSBN);
	} else {
	    System.out.println("Sorry, this book seems to be already lended. Maybe return it first.");
	}
    }
    public Manager(String userName, String password, int type) {
	super(userName, password, type);
    }
}
