package cosc310_T28_librarySystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SaveTest {

    @Test
    void test() {
	LocalLibraryData savedLibraryData = new LocalLibraryData();
	savedLibraryData.bookList.add(new Book());

	UserAndManagerTerminal userAndManagerTerminal = new UserAndManagerTerminal();
	userAndManagerTerminal.saveSession(null, savedLibraryData, "save_test_1.ser");

	LocalLibraryData loadedLibraryData = userAndManagerTerminal.loadSession("save_test_1.ser");

	assertEquals(savedLibraryData.bookList, loadedLibraryData.bookList);
    }

}
