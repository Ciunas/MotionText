package notepad;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DataNode {

	JScrollPane js = new JScrollPane(); 
	JTextArea jta = new JTextArea(); 
	String location;
	
	public DataNode(String location){
		this.location = location; 
		jta.setForeground(Color.GREEN);
		jta.setBackground(Color.BLACK);
		jta.setFont(new Font("SansSerif", Font.PLAIN, 15));
		js.setViewportView(jta);
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
