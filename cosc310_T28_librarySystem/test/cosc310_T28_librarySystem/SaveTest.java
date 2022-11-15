package cosc310_T28_librarySystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class SaveTest {

    @Test
    void test() {
	LocalLibraryData savedLibraryData = new LocalLibraryData();
	savedLibraryData.bookList.add(new Book(new BookGroup(), 1234567890L, LocalDate.MIN, 123, "Test Book", "Test Author", "Test Category", false));

	UserAndManagerTerminal userAndManagerTerminal = new UserAndManagerTerminal();
	userAndManagerTerminal.saveSession(null, savedLibraryData, "save_test_1.ser");

	LocalLibraryData loadedLibraryData = userAndManagerTerminal.loadSession("save_test_1.ser");
	LocalLibraryData differentLibraryData = new LocalLibraryData();
	differentLibraryData.bookList.add(new Book(new BookGroup(), 1234567890L, LocalDate.MIN, 123, "Test Different Book", "Test Author", "Test Category", false));

	assertEquals(savedLibraryData, loadedLibraryData);
	assertNotEquals(savedLibraryData, differentLibraryData);
	
	try {
	    Files.deleteIfExists(Paths.get("cosc310_T28_library_system_saved_files/save_test_1.ser"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	
    }

}
