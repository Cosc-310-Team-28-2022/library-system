package cosc310_T28_librarySystem;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * 
 * @author copied from Lawrence Dol, slight edits by Team 28 copied from
 *         https://stackoverflow.com/questions/342990/create-java-console-inside-a-gui-panel
 */
class JTextPaneOutputStream extends OutputStream {

// *************************************************************************************************
// INSTANCE MEMBERS
// *************************************************************************************************

    private byte[] oneByte; // array for write(int val);
    private Appender appender; // most recent action

    public JTextPaneOutputStream(JTextPane textPane) {
	this(textPane, 1000);
    }

    public JTextPaneOutputStream(JTextPane textPane, int maxlin) {
	if (maxlin < 1) {
	    throw new IllegalArgumentException(
		    "TextAreaOutputStream maximum lines must be positive (value=" + maxlin + ")");
	}
	oneByte = new byte[1];
	appender = new Appender(textPane, maxlin);
    }

    /** Clear the current console text area. */
    public synchronized void clear() {
	if (appender != null) {
	    appender.clear();
	}
    }

    public synchronized void close() {
	appender = null;
    }

    public synchronized void flush() {
    }

    public synchronized void write(int val) {
	oneByte[0] = (byte) val;
	write(oneByte, 0, 1);
    }

    public synchronized void write(byte[] ba) {
	write(ba, 0, ba.length);
    }

    public synchronized void write(byte[] ba, int str, int len) {
	if (appender != null) {
	    appender.append(bytesToString(ba, str, len), appender.textPane.getForeground());
	}
    }
    public synchronized void writeString(String s, Color c) {
	appender.append(s, c);
    }

//    @edu.umd.cs.findbugs.annotations.SuppressWarnings("DM_DEFAULT_ENCODING")
    static private String bytesToString(byte[] ba, int str, int len) {
	try {
	    return new String(ba, str, len, "UTF-8");
	} catch (UnsupportedEncodingException thr) {
	    return new String(ba, str, len);
	} // all JVMs are required to support UTF-8
    }

// *************************************************************************************************
// STATIC MEMBERS
// *************************************************************************************************

    static class Appender implements Runnable {
	private final JTextPane textPane;
	private final int maxLines; // maximum lines allowed in text area
	private final LinkedList<Integer> lengths; // length of lines within text area
	private final List<String> values; // values waiting to be appended
	private final List<Color> valueColors;

	private int curLength; // length of current line
	private boolean clear;
	private boolean queue;
	private boolean newLine; // instead of always having an empty line at the bottom, remember whether there
				 // is new line as boolean

	Appender(JTextPane textPane, int maxlin) {
	    this.textPane = textPane;
	    maxLines = maxlin;
	    lengths = new LinkedList<Integer>();
	    values = new ArrayList<String>();
	    valueColors = new ArrayList<Color>();

	    curLength = 0;
	    clear = false;
	    queue = true;
	    newLine = false;
	}

	synchronized void append(String val, Color color) {
	    values.add(val);
	    valueColors.add(color);
	    if (queue) {
		queue = false;
		EventQueue.invokeLater(this);
	    }
	}

	synchronized void clear() {
	    clear = true;
	    curLength = 0;
	    lengths.clear();
	    values.clear();
	    valueColors.clear();
	    newLine = false;
	    if (queue) {
		queue = false;
		EventQueue.invokeLater(this);
	    }
	}

	// MUST BE THE ONLY METHOD THAT TOUCHES textPane!
	public synchronized void run() {
	    if (clear) {
		textPane.setText("");
	    }
	    StyledDocument document = textPane.getStyledDocument();
	    for (int i=0; i<values.size(); i++) {
		String val = values.get(i);
		Color valColor = valueColors.get(i);
		try {
		    if (newLine) {
			if (lengths.size() >= maxLines) {
			    // textPane.replaceRange("", 0, lengths.removeFirst());
			    document.remove(0, lengths.removeFirst());
			}

			curLength += EOL1.length();
			// textPane.append(EOL1);
			document.insertString(document.getLength(), EOL1, null);

			lengths.addLast(curLength);
			curLength = 0;
			
			newLine = false;
		    }
		    if (val.endsWith(EOL1) || val.endsWith(EOL2)) {
			String newVal1 = val.replaceAll(EOL1 + "$", "");
			String newVal2 = val.replaceAll(EOL2 + "$", "");
			val = (newVal1.length() <= newVal2.length()) ? newVal1 : newVal2;
			newLine = true;
		    }
		    curLength += val.length();
		    // textPane.append(val);
		    SimpleAttributeSet attributeSet = new SimpleAttributeSet();
		    StyleConstants.setForeground(attributeSet, valColor);
		    document.insertString(document.getLength(), val, attributeSet);
		} catch (BadLocationException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	    values.clear();
	    valueColors.clear();
	    clear = false;
	    queue = true;
	}

	static private final String EOL1 = "\n";
	static private final String EOL2 = System.getProperty("line.separator", EOL1);
    }

} /* END PUBLIC CLASS */