package cosc310_T28_librarySystem;

/**2
 * 
 * @author Team 28
 * 
 *         The code starts running here. The main class currently just creates a
 *         UserAndManagerTerminal and starts it.
 *
 *         Credits: our program uses source code by Lawrence Dol from
 *         https://stackoverflow.com/questions/342990/create-java-console-inside-a-gui-panel
 *
 */
public class main {

    public static void main(String[] args) {

	UserAndManagerTerminal terminal = new UserAndManagerTerminal();
	terminal.start();

    }

}
