package cosc310_T28_librarySystem;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

public class UserTest {

    @Test
    public void testBorrowBook() {
	Scanner scanner=new Scanner(System.in);
	LocalLibraryData localLibraryData = new LocalLibraryData();
	Book book1=new Book(null, 1234567890, null, 01, "book1", "author", "cate", false);
	localLibraryData.bookList.add(book1);
	localLibraryData.freeToLend.add(book1);
	User user =new User();
	assertEquals(book1, user.borrowBook(scanner, localLibraryData));
	
    }

}
