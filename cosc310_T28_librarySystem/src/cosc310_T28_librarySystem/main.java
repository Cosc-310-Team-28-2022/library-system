package cosc310_T28_librarySystem;

public class main {

	public static void main(String[] args) {
	
	  UserAndManagerTerminal terminal = new UserAndManagerTerminal(new LocalLibraryData());
	  terminal.start();
		
	}

}
