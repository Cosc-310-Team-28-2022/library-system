package cosc310_T28_librarySystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

/**
 * 
 * @author lodeous and Team 28
 * copied from lodeous's Github at https://gist.github.com/lodeous/70e36c2ebed76f3efe257204d9cdc375
 * Edited to add different colours and output stream for System.setOut()
 *
 */
public class NewConsole {

    private JFrame consoleFrame;
    private JTextPane outputPane;
    private JTextField inputField;
    PipedOutputStream outputFromField;
    PipedInputStream inputFromField;
//    private Scanner fieldInput;
    private PrintStream fieldOutput;

    public void create() {
        //create components
        consoleFrame = new JFrame("Library System");
        outputPane = new JTextPane();
        inputField = new JTextField();
        
        

        //Make outputArea read-only
        outputPane.setEditable(false);

        //Set component backgrounds to BLACK and text color to WHITE to make it look more like a console
//        outputPane.setBackground(Color.BLACK);
//        inputField.setBackground(Color.BLACK);
//        outputPane.setForeground(Color.WHITE);
//        inputField.setForeground(Color.WHITE);

        //Setup Piped IO
        outputFromField = new PipedOutputStream();
        inputFromField = new PipedInputStream();
        try {
            outputFromField.connect(inputFromField);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
//        fieldInput = new Scanner(inputFromField);
        fieldOutput = new PrintStream(outputFromField);

        //Setup listeners

        //This listener listens for ENTER key on the inputField.
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputField.getText();
                jTextPaneOutputStream.writeString(text + "\n", Color.GREEN);
                fieldOutput.println(text);
                inputField.setText("");
                //Wake up the other thread for an immediate response.
                synchronized (inputFromField) {
                    inputFromField.notify();
                }
            }
        });

        //Setup Frame for display
        //Add components

        consoleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel consolePane = new JPanel();
        consolePane.setLayout(new BorderLayout());
        consolePane.add(outputPane,BorderLayout.CENTER);
        consolePane.add(inputField,BorderLayout.SOUTH);

	JScrollPane scroll = new JScrollPane() {
	    @Override
	    public Dimension getPreferredSize() {
		Dimension size = super.getPreferredSize();
		size.width = Math.max(size.width, 500);
		size.height = Math.max(size.height, 500);
		return size;
	    }
	};
	consolePane.addComponentListener(new ComponentListener() {
	    @Override
	    public void componentShown(ComponentEvent e) {
	    }

	    @Override
	    public void componentResized(ComponentEvent e) {
		// credit to
		// https://stackoverflow.com/questions/2483572/making-a-jscrollpane-automatically-scroll-all-the-way-down
		scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
		scroll.getHorizontalScrollBar().setValue(0);
	    }

	    @Override
	    public void componentMoved(ComponentEvent e) {
	    }

	    @Override
	    public void componentHidden(ComponentEvent e) {
	    }
	});
	scroll.setViewportView(consolePane);
	consoleFrame.add(scroll);
	consoleFrame.pack();

	// credit:
	// http://www.java2s.com/Code/Java/Event/SettingtheInitialFocusedComponentinaWindow.htm
	inputField.addHierarchyListener(new HierarchyListener() {

	    @Override
	    public void hierarchyChanged(HierarchyEvent e) {
		inputField.requestFocus();
	    }
	});


        consoleFrame.setLocationRelativeTo(null); // center of computer screen
        consoleFrame.setVisible(true);
        
        jTextPaneOutputStream = new JTextPaneOutputStream(outputPane);

        //set system in
        setSystemOutAndSystemIn();

    }

    JTextPaneOutputStream jTextPaneOutputStream;
    public void setSystemOutAndSystemIn() {
      System.setOut(new PrintStream(jTextPaneOutputStream));
      System.setIn(inputFromField);
    }


    public static void main(String[] args) {
        try {
            //Run GUI Creation code on the AWT Event dispatching thread.
            //Needs to use invoke and wait otherwise scanner created on System.in might fail
	    SwingUtilities.invokeAndWait(new Runnable() {
	        @Override
	        public void run() {
	            NewConsole console = new NewConsole();
	            console.create();
	        }
	    });
	} catch (InvocationTargetException | InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
        new Thread() {
            public void run() {
        	
        	try (Scanner s = new Scanner(System.in)) {
        	    while (s.hasNextLine()) {
        		String line = s.nextLine();
        		System.out.println("Program recieved input: "+line);
        	    }
        	}
            }
        }.start();
    }
}