package cosc310_T28_librarySystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Team 28
 *
 *         Each Manager object stores the username and password for one library
 *         manager account, and implements activities only managers can do, such
 *         as adding books and checking out books.
 */
public class Manager extends Account {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Manager() {
    }
	// add a book to library, only function for manager
    void addBook(Scanner scanner, LocalLibraryData localLibraryData) {
	String title = null;
	while (title == null) {
	    System.out.print("Title of book to add: ");
	    if (!scanner.hasNextLine()) {
		return;
	    }
	    String titleEntered = scanner.nextLine();
	    if (!titleEntered.matches("^[a-zA-Z0-9 _-`~!@#$%^&*()=+\\[{\\]}\\\\|;:'\",<.>/?]*$")) {
		System.out.println(
			"The title must contain only letters, numbers, _-`~!@#$%^&*()=+[{]}\\|;:'\",<.>/? symbols, or spaces.");
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
	    iSBNEntered.replaceAll("-", ""); // delete dashes
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
		// The only way to check if a format is valid is to catch exceptions.
		// See https://www.baeldung.com/java-string-valid-date
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
		System.out.println(
			"The author must contain only letters, numbers, _-`~!@#$%^&*()=+[{]}\\|;:'\",<.>/? symbols, or spaces.");
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
		System.out.println(
			"The category must contain only letters, numbers, _-`~!@#$%^&*()=+[{]}\\|;:'\",<.>/? symbols, or spaces.");
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
	// delete a book from library, only function for manager
    void delBook(Scanner scanner, LocalLibraryData localLibraryData) {
		ArrayList<Book> bookDeleteList = searchForABook(scanner, localLibraryData,true);
		
		if(bookDeleteList!=null){

		System.out.println("Choose the id(number before the book) of book you want to delete: ");

		int enter;
		while(true){
			if(scanner.hasNextInt()){
			   enter = scanner.nextInt();
				if(enter>=1&&enter<= bookDeleteList.size())
					break;
				else
				System.out.println("Please enter a right id: ");
			}else
				System.out.println("Please enter a number: ");
		}
		Book wantToDelete = bookDeleteList.get(Integer.valueOf(enter)-1);

		System.out.println("Sure to delete this book: Title = " + wantToDelete.title + " ISBN = " + wantToDelete.iSBN+"?");
		boolean df=false;
		String define = scanner.nextLine();
		while(!define.equals("Y")&& !define.equals("N")){
			System.out.println("Please enter 'Y' or 'N'");
			define = scanner.nextLine();
		}
		if(define.equals("Y"))
		df=true;
		else if(define.equals("N"))
		df=false;

		if(df==true){
			localLibraryData.bookList.remove(wantToDelete);
			System.out.println(wantToDelete.title+" is delete.");
		}else{
			System.out.println("Delete stop.");
		}
	}
    }

    void searchUser(Scanner scanner, LocalLibraryData localLibraryData) {
		
    }
	// Return a borrowed Book back to library, only function for manager
    void returnBook(Scanner scanner, LocalLibraryData localLibraryData) {
		if(localLibraryData.lended.isEmpty()){
			System.out.println("No book will be return.");
			}else{
			for(int i=1;i<=localLibraryData.lended.size();i++){
			System.out.println(i+": "+localLibraryData.lended.get(i-1).title+" "+localLibraryData.lended.get(i-1).iSBN+" "+localLibraryData.lended.get(i-1).holder.getUsername());
		}
			
		System.out.println("Choose the id(number before the book) of book you want to select: ");
	   
		int enter;
		while(true){
			if(scanner.hasNextInt()){
			   enter = scanner.nextInt();
				if(enter>=1&&enter<=localLibraryData.lended.size())
					break;
				else
				System.out.println("Please enter a right id: ");
			}else
				System.out.println("Please enter a number: ");
		}
		Book wantToReturn = localLibraryData.lended.get(Integer.valueOf(enter)-1);
		System.out.println("Sure to return this book: Title = " + wantToReturn.title + " ISBN = " + wantToReturn.iSBN+"?");
		boolean df=false;
		String define = scanner.nextLine();
		while(!define.equals("Y")&& !define.equals("N")){
			System.out.println("Please enter 'Y' or 'N'");
			define = scanner.nextLine();
		}
		if(define.equals("Y"))
		df=true;
		else if(define.equals("N"))
		df=false;
		if(df==true){
			localLibraryData.lended.remove(wantToReturn);
			wantToReturn.borrow=false;
			localLibraryData.freeToLend.add(wantToReturn);
			System.out.println("Checkout successful. Title = " + wantToReturn.title + " ISBN = " + wantToReturn.iSBN);
		}else{
			System.out.println("Return stop.");
		}
			}
    }
	//Through the application for borrowing books
    void checkoutBook(Scanner scanner, LocalLibraryData localLibraryData) {
		if(localLibraryData.readyToLend.isEmpty()){
		System.out.println("No book will be lend.");
		}else{
		for(int i=1;i<=localLibraryData.readyToLend.size();i++){
		System.out.println(i+": "+localLibraryData.readyToLend.get(i-1).title+" "+localLibraryData.readyToLend.get(i-1).iSBN+" "+localLibraryData.readyToLend.get(i-1).holder.getUsername());
	}
		
	System.out.println("Choose the id(number before the book) of book you want to select: ");
   
    int enter;
    while(true){
        if(scanner.hasNextInt()){
           enter = scanner.nextInt();
            if(enter>=1&&enter<=localLibraryData.readyToLend.size())
                break;
            else
            System.out.println("Please enter a right id: ");
        }else
            System.out.println("Please enter a number: ");
    }
	Book wantToLend = localLibraryData.readyToLend.get(Integer.valueOf(enter)-1);
	System.out.println("Sure to Lend out this book: Title = " + wantToLend.title + " ISBN = " + wantToLend.iSBN+"?");
    boolean df=false;
	String define = scanner.nextLine();
	while(!define.equals("Y")&& !define.equals("N")){
		System.out.println("Please enter 'Y' or 'N'");
		define = scanner.nextLine();
	}
	if(define.equals("Y"))
	df=true;
	else if(define.equals("N"))
	df=false;
	if(df==true){
		localLibraryData.readyToLend.remove(wantToLend);
		wantToLend.borrow=true;
		localLibraryData.lended.add(wantToLend);
		System.out.println("Checkout successful. Title = " + wantToLend.title + " ISBN = " + wantToLend.iSBN);
	}else{
		System.out.println("Lend stop.");
	}
		}
	
    }

    public Manager(String userName, String password, int type) {
	super(userName, password, type);
    }
}
