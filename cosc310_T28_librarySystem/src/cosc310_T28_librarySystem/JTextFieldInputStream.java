package cosc310_T28_librarySystem;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.swing.JTextField;

/**
 * 
 * @author user1406062
 * https://stackoverflow.com/questions/12669368/java-how-to-extend-inputstream-to-read-from-a-jtextfield
 *
 */
public class JTextFieldInputStream extends InputStream {
    byte[] contents = new byte[0];
    int pointer = 0;

    public JTextFieldInputStream(final JTextField text) {

        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    contents = text.getText().getBytes();
                    pointer = 0;
                    text.setText("");
                }
                super.keyReleased(e);
            }
        });
    }

    @Override
    public int read() throws IOException {
        if(pointer >= contents.length) return -1;
        return this.contents[pointer++];
    }
    
}
class MyInputStream extends BufferedInputStream {
    PipedInputStream inputStreamToClose;
    LinkedList<byte[]> pastLines = new LinkedList<byte[]>(); //simplifies removing first?
    int readerPosition;
    JTextField textField;
    PrintWriter printWriterToClose;
    PipedOutputStream outputStreamToClose;
    public MyInputStream(final JTextField textField, PipedInputStream nullParameter) throws IOException {
	//somehow have to use a null parameter because not allowed to write anything before super()
	super(nullParameter = new PipedInputStream());
	inputStreamToClose = nullParameter;
	//inputStreamToClose is essentially the same input stream as this input stream, except not buffered
	this.textField = textField;
	outputStreamToClose = new PipedOutputStream(inputStreamToClose);
	printWriterToClose = new PrintWriter(outputStreamToClose);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    printWriterToClose.println(textField.getText());
                    printWriterToClose.flush();
                    textField.setText("");
                }
                super.keyReleased(e);
            }
        });
    }

    @Override
    public void close() throws IOException {
	super.close();
	printWriterToClose.close();
	outputStreamToClose.close();
    }

}