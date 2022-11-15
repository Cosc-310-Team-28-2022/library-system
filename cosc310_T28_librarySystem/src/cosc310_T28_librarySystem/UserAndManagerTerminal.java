package cosc310_T28_librarySystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class UserAndManagerTerminal extends Thread {
    LocalLibraryData localLibraryData;
    Console console;

    public UserAndManagerTerminal() {
	super();
    }

    /**
     * This thread will will keep asking the user for input and replying to all the
     * input.
     * 
     * This function is automatically run by the thread after start() is called.
     */
    public void run() {
	/*
	 * NOTE: when testing make all passwords "testpassword". First username should be "test"
	 */
		boolean finishedWithoutInterruption = false; // this boolean tells the try-finally clause whether
						     // scanner.hasNextLine()
						     // failed
	console = new Console();
	console.create();
    
	try (Scanner scanner = new Scanner(System.in)) {
        
        localLibraryData = loadSession("main.ser");
        if (localLibraryData == null) {
                localLibraryData = new LocalLibraryData();
        }

	    Account currentAccount=null;
	    System.out.println("Welcoming to Team 28's Library System (version 0.1).");
	    

        System.out.println("Library User please enter 1, Manager (Librarian) please enter 2.");
        boolean isManager=true;

        String AccountTypeSelection = scanner.nextLine(); //Judge user type
        while(!AccountTypeSelection.equals("1")&&!AccountTypeSelection.equals("2")){
            System.out.println("Please only enter 1 or 2.");
            AccountTypeSelection = scanner.nextLine();
        }
        switch (AccountTypeSelection) {
            case "1":
            isManager=false;
                break;
            case "2":
            isManager=true;
                break;
        }
        System.out.println("Log in to an account, please enter 1.\nCreate a new account, please enter 2.");
        String LogInTypeSelection = scanner.nextLine(); //Select to log in or create an account
        while(!LogInTypeSelection.equals("1")&&!LogInTypeSelection.equals("2")){
            System.out.println("Please only enter 1 or 2.");
            LogInTypeSelection = scanner.nextLine();
        }
        
        switch (LogInTypeSelection) {   
            case "1":
            if(isManager==true)
            currentAccount = tryLoggingInManager(scanner, localLibraryData);
            if(isManager==false)
            currentAccount = tryLoggingInUser(scanner, localLibraryData);
            if (currentAccount == null) {
                System.out.println("Login failed. Exiting.");
                        finishedWithoutInterruption = true;
                        return;
            }
                break;
            case "2":
            currentAccount = tryCreatingAccount(scanner, localLibraryData, isManager);
            if (currentAccount == null) {
                System.out.println("Account creation failed. Exiting.");
                        finishedWithoutInterruption = true;
                        return;
            }
            if(isManager)
            localLibraryData.managerAccounts.put(currentAccount.getUsername(), (Manager) currentAccount);
            else
            localLibraryData.userAccounts.put(currentAccount.getUsername(), (User)currentAccount);
            
                break;
            }
            
       
	    
	    //this while loop is the main loop for the user or manager to do any command
            UserOrManagerCommandResult userOrManagerCommandResult;
	    do {
		userOrManagerCommandResult = askAndDoNextUserOrManagerCommand(scanner, localLibraryData, currentAccount);	
	    } while (userOrManagerCommandResult != UserOrManagerCommandResult.EXIT);

	    finishedWithoutInterruption = true;
	} finally {
	    if (!finishedWithoutInterruption) {
		System.out.println("Program interrupted. Exiting.");
	    }else{System.out.println("Shut down the system");}
	}
    }
    // Create a new account 
    private Account tryCreatingAccount(Scanner scanner, LocalLibraryData localLibraryData, boolean isManager) {
        String username = null;
        while (username == null) {
            System.out.print("Username: ");
            if (!scanner.hasNextLine()) {
                return null;
            } 
            String usernameEntered = scanner.nextLine();
            if (!usernameEntered.matches("^[a-zA-Z0-9 _-]*$")) {
        	System.out.println("The username must contain only letters, numbers, _- symbols, or spaces.");
            } else if (!(1 <= usernameEntered.length() && usernameEntered.length() <= 99)) {
        	System.out.println("The username must be 1 to 99 characters long.");
            } else if (localLibraryData.managerAccounts.containsKey(usernameEntered) && localLibraryData.userAccounts.containsKey(usernameEntered)) {
        	System.out.println("The username already exists.");
            } else {
        	username = usernameEntered;
            }
        }
        String password = null;
        while (password == null) {
            System.out.print("Password: ");
            console.setPasswordMode(true);
            if (!scanner.hasNextLine()) {
                return null;
            }
            String passwordEntered = scanner.nextLine();
            console.setPasswordMode(false);
            if (!passwordEntered.matches("^[a-zA-Z0-9 _-`~!@#$%^&*()=+\\[{\\]}\\\\|;:'\",<.>/?]*$")) {
        	System.out.println("The password must contain only letters, numbers, _-`~!@#$%^&*()=+[{]}\\|;:'\",<.>/? symbols, or spaces.");
            } else if (!(1 <= passwordEntered.length() && passwordEntered.length() <= 99)) {
        	System.out.println("The password must be 1 to 99 characters long.");
            } else {
        	password = passwordEntered;
            }
        }
        Account newAccount;
        if (isManager) {
            newAccount = new Manager(username, password, 2); //name manager type to '2'
            System.out.println("The manager account " + username + " was created successfully.");
        } else {
            newAccount = new User(username, password, 1);    //name user type to '1'
            System.out.println("The user account " + username + " was created successfully.");
        }
        return newAccount;
    }
    private Account tryLoggingInUser(Scanner scanner, LocalLibraryData localLibraryData) {
        System.out.println("Please log in to an account.");
        Account accountToLogIn = null;
        while (accountToLogIn == null) {
            System.out.print("Username: ");
            if (!scanner.hasNextLine()) {
                return null;
            }
            String usernameEntered = scanner.nextLine();
            if (localLibraryData.userAccounts.containsKey(usernameEntered)) {
        	accountToLogIn = localLibraryData.userAccounts.get(usernameEntered);
            } else {
        	System.out.println("Account not found.");
            return null;
            }
        }
        while (true) {
            System.out.print("Password: ");
            console.setPasswordMode(true);
            if (!scanner.hasNextLine()) {
                return null;
            }
            String passwordEntered = scanner.nextLine();
            console.setPasswordMode(false);
            if (!accountToLogIn.passwordEquals(passwordEntered)) {
        	System.out.println("Password incorrect.");
            } else {
        	return accountToLogIn;
            }
        }
    }
    private Account tryLoggingInManager(Scanner scanner, LocalLibraryData localLibraryData) {
        System.out.println("Please log in to an account.");
        Account accountToLogIn = null;
        while (accountToLogIn == null) {
            System.out.print("Username: ");
            if (!scanner.hasNextLine()) {
                return null;
            }
            String usernameEntered = scanner.nextLine();
            if (localLibraryData.managerAccounts.containsKey(usernameEntered)) {
        	accountToLogIn = localLibraryData.managerAccounts.get(usernameEntered);
            } else {
        	System.out.println("Account not found.");
            return null;
            }
        }
        while (true) {
            System.out.print("Password: ");
            console.setPasswordMode(true);
            if (!scanner.hasNextLine()) {
                return null;
            }
            String passwordEntered = scanner.nextLine();
            console.setPasswordMode(false);
            if (!accountToLogIn.passwordEquals(passwordEntered)) {
        	System.out.println("Password incorrect.");
            } else {
        	return accountToLogIn;
            }
        }
    }

    //Input the corresponding number realization function
    private UserOrManagerCommandResult askAndDoNextUserOrManagerCommand(Scanner scanner, LocalLibraryData localLibraryData, Account currentAccount) {
        System.out.println("Welcome " + currentAccount.getUsername() + ". What would you like to do? Enter a number to make a selection.");
        if (currentAccount instanceof Manager) {
            System.out.println("1: search for a book");
            System.out.println("2: checkout book on hold to lend");
            System.out.println("3: save session");
            System.out.println("4: add a new book from library");
            System.out.println("5: delete a book");
            System.out.println("6: return a lended book");
            System.out.println("7: quit and save");
            System.out.println("8: quit without save");
            if (!scanner.hasNextLine()) {
                return null;
            }
            String selection = scanner.nextLine();
            switch (selection) {
                case "1":
                    currentAccount.searchForABook(scanner, localLibraryData, false);
                    break;
                case "2":
                    ((Manager) currentAccount).checkoutBook(scanner, localLibraryData);
                    break;
                case "3":
                    saveSession(scanner, localLibraryData, "main.ser");
                    break;
                case "4":
                    ((Manager) currentAccount).addBook(scanner, localLibraryData);
                    break;
                case "5":
                    ((Manager)currentAccount).delBook(scanner, localLibraryData);;
                    break;
                case "6":
                    ((Manager) currentAccount).returnBook(scanner, localLibraryData);
                    break;
                case "7":
                    saveSession(scanner, localLibraryData, "main.ser");
                    return UserOrManagerCommandResult.EXIT;
                case "8":
                    return UserOrManagerCommandResult.EXIT; 
        	default:
                    System.out.println("Selection unavailable");
                    break;
            }
        } else if (currentAccount instanceof User) {
            
            System.out.println("1: search for a book");
            System.out.println("2: apply to lend a book");
            System.out.println("3: save session");
            System.out.println("4: quit and save");
            System.out.println("5: quit without save");

            if (!scanner.hasNextLine()) {
                return null;
            }
            String selection = scanner.nextLine();
            switch (selection) {
                case "1":
                    currentAccount.searchForABook(scanner, localLibraryData, false);
                    break;
                case "2":
                    ((User) currentAccount).borrowBook(scanner, localLibraryData);
                    break;
                case "3":
                    saveSession(scanner, localLibraryData, "main.ser");
                    break;
                case "4":
                    saveSession(scanner, localLibraryData, "main.ser");
                    return UserOrManagerCommandResult.EXIT;
                case "5":
                    return UserOrManagerCommandResult.EXIT;   
                    
        	default:
                    System.out.println("Selection unavailable");
                    break;
            }
        } else {
            throw new IllegalStateException();
        }
        return UserOrManagerCommandResult.NEXT_COMMAND;
    }
    static enum UserOrManagerCommandResult {
	EXIT,
	NEXT_COMMAND,
	LOG_OUT;
    }


    /**
     * save data function
     * NOTE: this doesn't need to be encrypted since everyone can see the books, just not the passwords
     * @param name
     * @return
     */
    void saveSession(Scanner scanner, LocalLibraryData localLibraryData, String name) {
	try {
	    if (!Files.exists(Paths.get("cosc310_T28_library_system_saved_files/"))) {
		Files.createDirectories(Paths.get("cosc310_T28_library_system_saved_files/")); // does not overwrite anyways
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	try (
		FileOutputStream fileOut = new FileOutputStream("cosc310_T28_library_system_saved_files/" + name);
//		GZIPOutputStream zipOut = new GZIPOutputStream(fileOut);
		ObjectOutputStream out = new ObjectOutputStream(fileOut)
		) {
	    out.writeObject(localLibraryData);
	} catch (IOException i) {
	    i.printStackTrace();
	}
    }

    /**
     * load data function
     * NOTE: this doesn't need to be encrypted since everyone can see the books, just not the passwords
     * @param name
     * @return
     */
    LocalLibraryData loadSession(String name) {
	if (Files.exists(Paths.get("cosc310_T28_library_system_saved_files/" + name))) {
	    try (
		    FileInputStream fileIn = new FileInputStream("cosc310_T28_library_system_saved_files/" + name);
//		    GZIPInputStream zipIn = new GZIPInputStream(fileIn);
		    ObjectInputStream in = new ObjectInputStream(fileIn)
		    ) {
		LocalLibraryData localLibraryData = (LocalLibraryData) in.readObject();
		return localLibraryData;
	    } catch (IOException | ClassNotFoundException c) {
		c.printStackTrace();
		return null;
	    }	
	}
	return null;
    } 

}

       