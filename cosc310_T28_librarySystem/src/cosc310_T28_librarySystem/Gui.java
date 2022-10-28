package cosc310_T28_librarySystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Gui {
    public static void main(String[] args) {
	JFrame mainJFrame = new JFrame("Library System");
	mainJFrame.setLayout(new BorderLayout());
	JScrollPane scroll = new JScrollPane();
	Console console = new Console();
	console.setSystemOut();
	scroll.setViewportView(console);
	mainJFrame.add(scroll, BorderLayout.NORTH);
	mainJFrame.pack();
	mainJFrame.setLocationRelativeTo(null);
	mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	mainJFrame.setVisible(true);
	new Thread() {
	    public void start() {
		while (true) {
		    try {
			Thread.sleep(1000);
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		    System.out.println("testing");
		}
	    }
	}.start();
    }

    public void start() {
//	System.setOut(null);

    }
}

class Console extends JPanel {
    TextAreaOutputStream textAreaOutputStream;
    public Console() {
	setLayout(new BorderLayout());
	JTextArea textAreaOutput = new JTextArea();
	textAreaOutputStream = new TextAreaOutputStream(textAreaOutput);
	add(textAreaOutput, BorderLayout.CENTER);
	JTextArea textAreaInput = new JTextArea();
	add(textAreaInput, BorderLayout.SOUTH);
    }
    @Override
    public Dimension getPreferredSize() {
	Dimension size = super.getPreferredSize();
	size.width = Math.max(size.width, 200);
	size.height = Math.max(size.height, 200);
	return size;
    }
    public void setSystemOut() {
	System.setOut(new PrintStream(textAreaOutputStream));
    }
}