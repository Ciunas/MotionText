package notepad;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.nio.file.Path; 
import javax.swing.JScrollPane;
import javax.swing.JTextArea; 

public class DataNode {

	JScrollPane js = new JScrollPane(); 
	JTextArea jta = new JTextArea(); 
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
		this.jta.setMargin( new Insets(6, 6, 0, 0) ); //top,left,bottom,right
		this.js.setViewportView(jta);  
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

	public JTextArea getJta() {
		return jta;
	}
}