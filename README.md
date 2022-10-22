# library-system
This is an integrated library system that a library can use to keep track of books and help users find books. It will keep track of every single book when it is lent out and returned. It will allow users to search for books by title, using only part of the title. It will allow librarians to create accounts. And it will generate reports. The main limitations are that the first version of the program will run on a single computer in a single library. The first version will also use a command line instead of a GUI. The first version will not encrypt the information and assume the computer it runs on is secure or under supervision.

Currently it is only a demo and not complete.

## Compiling and running
One way to compile it is to use Eclipse. First navigate to https://github.com/Cosc-310-Team-28-2022/library-system. Click on the "Code" button on Github and select "Download ZIP". Open Eclipse (install if necessary) and click "File", "Import", "General", "Projects from Folder or Archive". Make sure "Search for nested projects" is on. Click the "Archive..." not "Directory..." button, and find where you downloaded "library-system-main.zip" to. Maybe three projects will be listed. Uncheck all the boxes but check the box for project "library-system-main.zip_expanded/library-system-main/cosc310_T28_librarySystem". Click finish.

Now the project should be in Eclipse. In the Eclipse project explorer, find and open the file "cosc310_T28_librarySystem/src/cosc310_T28_librarySystem/main.java". While the file is open, click "Run" and "Run" or click the green (>) button. This will automatically compile the .class files inside "cosc310_T28_librarySystem/bin" which might not be visible in the Eclipse project explorer. For the current version, you can only interact with the program using a terminal, e.g. the Eclipse "Console" where you should see the program after you run it.

## Class structuring
Some classes like Account, Book, BookGroup, Manager, and User are Serializable. They make objects which are stored inside one instance of the Serializable LocalLibraryData. Overall, these classes serve to both store information, and provide methods for modifying information.

Other classes like main and UserAndManagerTerminal are not Serializable, but allow us to run code and call other methods.

### Serializable classes

The Account class is the subclass for User and Manager. Any activity both library users and library managers can do, such as searching a book, is implemented by this class.

Each Manager object stores the username and password for one library manager account, and implements activities only managers can do, such as adding books and checking out books.

Each User object stores the username and password and other information for one library user account, and has no methods.

Each Book object stores the information about one book, and has no methods.

Each BookGroup object is a subcategory which stores related books e.g. multiple volumes, and has no methods.

The LocalLibraryData stores the entire collection of these objects in ArrayList and HashMap collections, and currently has no methods.

### Other classes

The main class currently just creates a UserAndManagerTerminal and starts it.

The UserAndManagerTerminal is a Thread which communicates with the user using text through the standard input and standard output. It lets the person interacting with the program log in or create an account, and it goes into a loop asking him for the next action. When the program starts, it also loads the LocalLibraryData from a text file if avaiable and allows the person (user or manager) to save it to the text file any time.

![Image](https://github.com/Cosc-310-Team-28-2022/library-system/blob/main/code%20structure.png)
