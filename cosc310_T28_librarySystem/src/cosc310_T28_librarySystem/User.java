package cosc310_T28_librarySystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Team 28
 *
 *         Each User object stores the username and password and other
 *         information for one library user account, and has no methods.
 */
public class User extends Account {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // private String name;
    private final int max_borrow = 3;
    private int borrow_num;
    private Book borrow_books[]=new Book[max_borrow];
    public User(String userName, String password, int type) {
	super(userName, password, type);

	borrow_books = new Book[max_borrow];
    }

    Book borrowBook(Scanner scanner, LocalLibraryData localLibraryData) {
    ArrayList<Book> bookFoundlist = searchForABook(scanner,localLibraryData,true);
    System.out.println("Choose the id(number before the book) of book you want to select: ");
    if (!scanner.hasNextLine()) {
	    return null;
	} 
    int enter;
    while(true){
        if(scanner.hasNextInt()){
           enter = scanner.nextInt();
            if(enter>=1&&enter<=bookFoundlist.size())
                break;
            else
            System.out.println("Please enter a right id: ");
        }else
            System.out.println("Please enter a number: ");
    }
    
    Book wantToBorrow = bookFoundlist.get(Integer.valueOf(enter)-1);
    System.out.println("Sure to borrow book: Title = " + wantToBorrow.title + " ISBN = " + wantToBorrow.iSBN+"?");
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

   /* while(true){
        String define = scanner.nextLine();
        if(define.equals(String.valueOf('Y'))||define.equals(String.valueOf('N'))){
            if(define.equals(String.valueOf('Y'))){
                df=true;
                break;
        }
        else{
            df=false;
            break;
        }
    }else{
        System.out.println("Please enter 'Y' or 'N'");
    }
    }*/
    if(df==true){
        if(borrow_num>=3){
            System.out.println("You have already borrow "+borrow_num+" book(s)! Cannot borrow more.");
            return null;
        }else{
        if (localLibraryData.freeToLend.contains(wantToBorrow)) {
            localLibraryData.freeToLend.remove(wantToBorrow);
            wantToBorrow.holder=User.this;
            localLibraryData.readyToLend.add(wantToBorrow);
            System.out.println("You order to borrow book: Title = " + wantToBorrow.title + " ISBN = " + wantToBorrow.iSBN+" is received.\nPlease Please go to the library to get it later");
            for(int i=0;i<max_borrow;i++){
            if(borrow_books[i]==null){
                borrow_books[i]=wantToBorrow;
                borrow_num++;
                break;
            }
            }
            return wantToBorrow;
    }else{
            System.out.println("Sorry, this book is not free.");
        return null;
    }
    }
    }else{
        System.out.println("Borrow stop.");
    }
    return null;
    }

    void orderList(){
        for(int i=0;i<borrow_books.length;i++){
        if(borrow_books[i]!=null)
            System.out.print(borrow_books[i].title);
        }
    }
}