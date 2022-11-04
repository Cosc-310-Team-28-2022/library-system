package cosc310_T28_librarySystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Gui {
    public static void main(String[] args) {
	JFrame mainJFrame = new JFrame("Library System");
	mainJFrame.setLayout(new BorderLayout());
	JScrollPane scroll = new JScrollPane() {
	    @Override
	    public Dimension getPreferredSize() {
		Dimension size = super.getPreferredSize();
		size.width = Math.max(size.width, 200);
		size.height = Math.max(size.height, 200);
		return size;
	    }
	};
	try {
	    Console console = new Console();
	    console.addComponentListener(new ComponentListener() {
		@Override
		public void componentShown(ComponentEvent e) {
		}

		@Override
		public void componentResized(ComponentEvent e) {
		    // credit to
		    // https://stackoverflow.com/questions/2483572/making-a-jscrollpane-automatically-scroll-all-the-way-down
		    scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
		}

		@Override
		public void componentMoved(ComponentEvent e) {
		}

		@Override
		public void componentHidden(ComponentEvent e) {
		}
	    });
	    console.setSystemOut();
	    scroll.setViewportView(console);
	    mainJFrame.add(scroll, BorderLayout.NORTH);
	    mainJFrame.pack();
	    mainJFrame.setLocationRelativeTo(null);
	    mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    mainJFrame.setVisible(true);
	    new Thread() {
		public void start() {
		    try (Scanner s = new Scanner(System.in)){
//		    try (Scanner s = new Scanner(console.myInputStream)){
			while (s.hasNextLine()) {
			    System.out.println("echo " + s.nextLine());
			}
		    }
		}
	    }.start();
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
    }

    public void start() {
//	System.setOut(null);

    }
}

class Console extends JPanel {
    private JTextArea textAreaOutput;
    private TextAreaOutputStream textAreaOutputStream;
    MyInputStream myInputStream;

    public Console() throws IOException {
	setLayout(new BorderLayout());
	textAreaOutput = new JTextArea();
	textAreaOutputStream = new TextAreaOutputStream(textAreaOutput);
	add(textAreaOutput, BorderLayout.CENTER);
	JTextField textFieldInput = new JTextField();
	myInputStream = new MyInputStream(textFieldInput, null);
	add(textFieldInput, BorderLayout.SOUTH);

	// credit:
	// http://www.java2s.com/Code/Java/Event/SettingtheInitialFocusedComponentinaWindow.htm
	this.addHierarchyListener(new HierarchyListener() {
	    
	    @Override
	    public void hierarchyChanged(HierarchyEvent e) {
		textFieldInput.requestFocus();
	    }
	});
    }

    public void setSystemOut() {
	System.setOut(new PrintStream(textAreaOutputStream));
	System.setIn(myInputStream);
    }
}