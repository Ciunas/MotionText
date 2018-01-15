package motiontext;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets; 
import java.nio.file.Path; 
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager; 

public class DataNode {
	UndoManager undoManager = new UndoManager();
	JScrollPane js = new JScrollPane(); 
	JTextPane jta = new JTextPane(); 
	Path location;
	WordSearch ws;
	boolean search = false;
	
	public DataNode(Path location, String fontSize, String fourgColour, String backColour, String fontName){
		this.location = location;  
		this.jta.setForeground(setColour(fourgColour));
		this.jta.setBackground(setColour(backColour));
		this.jta.setFont(new Font(fontName, Font.PLAIN, Integer.parseInt(fontSize))); 
		this.jta.setCaretColor(Color.red);
		this.jta.putClientProperty("caretWidth", 2);
		this.jta.setFocusable(true); 
		this.jta.setMargin(new Insets(6, 6, 0, 0)); // top,left,bottom,right
		jta.getDocument().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit()); 
			}
		});
		this.js.setViewportView(jta); 
		setTabs(4);
	}

	public void changeFont(String fontSize, String fourgColour, String backColour, String fontName ){ 
		jta.setForeground(setColour(fourgColour));
		jta.setBackground(setColour(backColour));
		jta.setFont(new Font(fontName, Font.PLAIN, Integer.parseInt(fontSize))); 
		js.setViewportView(jta); 
		jta.setFocusable(true);
	}
		
	private Color setColour(String colStr) {
		Color[] col = { Color.BLACK, Color.BLUE, Color.GRAY, Color.GREEN, Color.ORANGE, Color.RED, Color.WHITE,
				Color.YELLOW, Color.PINK };
		String[] colours = { "Black", "Blue", "Gray", "Green", "Orange", "Red", "White", "Yellow", "Pink" };
		int i;
		for (i = 0; i < colours.length ; i++) {
			if (colStr.equals(colours[i])) {
				break;
			}
		}
		return col[i];
	} 
	
	

	public void setTabs(int charactersPerTab) {
		FontMetrics fm = jta.getFontMetrics(jta.getFont());
		int charWidth = fm.charWidth(' ');
		int tabWidth = charWidth * charactersPerTab;

		TabStop[] tabs = new TabStop[12];

		for (int j = 0; j < tabs.length; j++) {
			int tab = j + 1;
			tabs[j] = new TabStop(tab * tabWidth);
		}

		TabSet tabSet = new TabSet(tabs);
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setTabSet(attributes, tabSet);
        int length = jta.getDocument().getLength();
        jta.getStyledDocument().setParagraphAttributes(0, length, attributes, false);

	}

	public void undo() {
		try {
			undoManager.undo();
		} catch (CannotRedoException cre) {
			cre.printStackTrace();
		}
	}

	public void redo() {
		try {
			undoManager.redo();
		} catch (CannotRedoException cre) {
			cre.printStackTrace();
		}
	}

	public WordSearch getWs() {
		return ws;
	}

	public void setWs(WordSearch ws) {
		this.ws = ws;
	}
	
	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public Path getLocation() {
		return location;
	} 

	public JScrollPane getJs() {
		return js;
	} 

	public JTextPane getJta() {
		return jta;
	}
}