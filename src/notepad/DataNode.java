package notepad;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DataNode {

	JScrollPane js;
	JTextArea jta;
	int number;
	
	public DataNode(int number){
		this.number = number;
		this.js = new JScrollPane();
		this.jta = new JTextArea(); 
		jta.setForeground(Color.GREEN);
		jta.setBackground(Color.BLACK);
		jta.setFont(new Font("SansSerif", Font.PLAIN, 15));
		js.setViewportView(jta);
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
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
	 
}
