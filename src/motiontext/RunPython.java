package motiontext;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog; 
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class RunPython extends JDialog{
	
	private static final long serialVersionUID = 1L;
	String scriptPath;
	String executabelPath;
	MyTextPane textArea =  new MyTextPane();

	public RunPython(Frame parent, String scriptPath, String executabelPath, String fontSize, String fontName) throws IOException, BadLocationException {
		super(parent, "Python Output", true);
		
		this.scriptPath = scriptPath;
		this.executabelPath = executabelPath;		
		this.textArea.setForeground(Color.WHITE);
		this.textArea.setBackground(Color.BLACK);
		this.textArea.setFont(new Font(fontName, Font.PLAIN, Integer.parseInt(fontSize))); 
		this.textArea.setEditable(false); 
		
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
	 

	public void run() throws IOException, BadLocationException {

		String[] cmd = new String[2];
		cmd[0] = executabelPath;
		cmd[1] = scriptPath;

		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(cmd);

		BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
		BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = "";
		
		Document doc = textArea.getDocument();
		while ((line = bfr.readLine()) != null) {
			doc.insertString(doc.getLength(), line + "\n", null);
		}

		while ((line = stdError.readLine()) != null) {
			doc.insertString(doc.getLength(), line + "\n", null);
		}
	} 
	
	private static class MyTextPane extends JTextPane {
		private static final long serialVersionUID = 1L;
		public MyTextPane() {
			super();
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Image img = null;
			try {
				img = ImageIO.read(MotionText.class.getResource("/resources/python.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(img, 225, 50, this);
			super.paintComponent(g);
		}
	}

}
