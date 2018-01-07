package notepad;

import java.awt.Color;
import java.awt.Font;  
import javax.swing.JScrollPane;
import javax.swing.JTextArea; 

public class DataNode {

	JScrollPane js = new JScrollPane(); 
	JTextArea jta = new JTextArea(); 
	String location;
	
	public DataNode(String location, String fontSize, String fourgColour, String backColour){
		this.location = location;  
		jta.setForeground(setColour(fourgColour));
		jta.setBackground(setColour(backColour));
		jta.setFont(new Font("Dialog", Font.PLAIN, Integer.parseInt(fontSize))); 
		jta.setCaret(new Caret());
		jta.setFocusable(true); 
		js.setViewportView(jta);  
	}
	
	public void changeFont(String fontSize, String fourgColour, String backColour){ 
		jta.setForeground(setColour(fourgColour));
		jta.setBackground(setColour(backColour));
		jta.setFont(new Font("Dialog", Font.PLAIN, Integer.parseInt(fontSize))); 
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public JScrollPane getJs() {
		return js;
	}

	public void setJs(JScrollPane js) {
		this.js = js;
	}

	public JTextArea getJta() {
		return jta;
	}

	public void setJta(JTextArea jta) {
		this.jta = jta;
	}
	
	public String getJText() {
		
		return jta.getText();
	}
	
	public void appendJText(String text) {
		jta.append(text);
	}
	
	
	 
}
