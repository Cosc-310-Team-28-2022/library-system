package cosc310_T28_librarySystem;

import java.util.Scanner;

public class UserAndManagerTerminal extends Thread {
    Account currentAccount = null;
    LocalLibraryData localLibraryData;

    public UserAndManagerTerminal(LocalLibraryData localLibraryData) {
	super();
	this.localLibraryData = localLibraryData;
    }

    /**
     * This thread will will keep asking the user for input and replying to all the
     * input.
     * 
     * This function is automatically run by the thread after start() is called.
     */
    public void run() {
	boolean finishedWithoutInterruption = false; // this boolean tells the try-finally clause whether
						     // scanner.hasNext()
						     // failed
	try (Scanner scanner = new Scanner(System.in)) {
	    Account currentAccount;
	    System.out.println("Welcoming to Team 28's Library System (version 0.1).");
	    if (localLibraryData.managerAccounts.isEmpty()) {
                System.out.println("Currently there are no manager (librarian) accounts. Please create a manager account.");
		currentAccount = tryCreatingAccount(scanner, true);
                localLibraryData.managerAccounts.add((Manager) currentAccount);
	    } else {
                System.out.println("Please log in to an account.");
	    }
	    finishedWithoutInterruption = true;
	} finally {
	    if (!finishedWithoutInterruption) {
		System.out.println("Program interrupted. Exiting.");
	    }
	}
    }

    private Account tryCreatingAccount(Scanner scanner, boolean isManager) {
        String username = null;
        while (username == null) {
            System.out.print("Username: ");
            if (!scanner.hasNext()) {
                return null;
            }
            String usernameEntered = scanner.nextLine();
            if (!usernameEntered.matches("[a-zA-Z0-9 _-]*")) {
        	System.out.println("The username must contain only letters, numbers, _- symbols, or spaces.");
            } else if (!(1 <= usernameEntered.length() && usernameEntered.length() <= 99)) {
        	System.out.println("The username must be 1 to 99 characters long.");
            } else {
        	username = usernameEntered;
            }
        }
        String password = null;
        while (password == null) {
            System.out.print("Password: ");
            if (!scanner.hasNext()) {
                return null;
            }
            String passwordEntered = scanner.nextLine();
            if (!passwordEntered.matches("[a-zA-Z0-9 _-`~!@#$%^&*()=+\\[{\\]}\\\\|;:'\",<.>/?]*")) {
        	System.out.println("The password must contain only letters, numbers, _-`~!@#$%^&*()=+[{]}\\|;:'\",<.>/? symbols, or spaces.");
            } else if (!(1 <= passwordEntered.length() && passwordEntered.length() <= 99)) {
        	System.out.println("The password must be 1 to 99 characters long.");
            } else {
        	password = passwordEntered;
            }
        }
        if (isManager) {
            return new Manager(username, password, 0); 
        } else {
            return new User(username, password, 0); 
        }
    }
}