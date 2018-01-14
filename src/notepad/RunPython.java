package notepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader; 
import javax.swing.JButton;
import javax.swing.JDialog; 
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RunPython extends JDialog{
	
	private static final long serialVersionUID = 1L;
	String scriptPath;
	String executabelPath;
	JTextArea textArea =  new JTextArea();

	public RunPython(Frame parent, String scriptPath, String executabelPath, String fontSize, String fourgColour, String backColour, String fontName) throws IOException {
		super(parent, "Python Output", true);
		
		this.scriptPath = scriptPath;
		this.executabelPath = executabelPath;		
		this.textArea.setForeground(setColour(fourgColour));
		this.textArea.setBackground(setColour(backColour));
		this.textArea.setFont(new Font(fontName, Font.PLAIN, Integer.parseInt(fontSize))); 
		
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}

		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(640, 480));
		getContentPane().add(myPanel, BorderLayout.CENTER);
		myPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		myPanel.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		JButton btnNewButton = new JButton("Close");
		panel.add(btnNewButton, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		myPanel.add(scrollPane, BorderLayout.CENTER);

		textArea = new JTextArea();
		textArea.setMargin( new Insets(6, 6, 0, 0) ); //top,left,bottom,right
		scrollPane.setViewportView(textArea);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		run();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true); 
	}
	 

	public void run() throws IOException {

		String[] cmd = new String[2];
		cmd[0] = executabelPath;
		cmd[1] = scriptPath;

		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(cmd);

		BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
		BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = "";
		
		while ((line = bfr.readLine()) != null) {
			textArea.append(line + "\n");
			System.out.println(line);
		}

		while ((line = stdError.readLine()) != null) {
			textArea.append(line + "\n");
			System.out.println(line);
		}
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

}
