package cosc310_T28_librarySystem;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Scanner;


import org.junit.Test;

public class AccountTest {
    
    @Test
    public void testSearchForABook() {
	Scanner scanner=new Scanner(System.in);
	LocalLibraryData localLibraryData = new LocalLibraryData();
	Book book1=new Book(null, 1234567890, null, 01, "book1", "author", "cate", false);
	localLibraryData.bookList.add(book1);
	Account account=new Account();
	ArrayList<Book> bookFoundlist= new ArrayList<Book>();
	bookFoundlist.add(book1);
	assertEquals(bookFoundlist,account.searchForABook(scanner, localLibraryData, true));
	
    }

}
