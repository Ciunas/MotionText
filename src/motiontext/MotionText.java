package motiontext;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton; 
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths; 
import java.util.HashMap; 
import java.util.prefs.Preferences;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;


/**
 * @author ciunas
 *
 */

public class MotionText{

	HashMap<String, DataNode> mapper = new HashMap<String, DataNode>();
	private static Preferences prefs;
	private Path filePath; 
	private String activeTab ="";
	private JScrollPane scrollPane_1;
	private JLabel lblNewLabel_2;
	private JFrame frame;
	private JTree tree;
	private MyKeyListener listener;
	private JTabbedPane tabbedPane;
	private JPanel panel_6;
	private boolean state; 
	private JTextField textField_1;

	/**
	 * Launch the application. 
	 */
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		EventQueue.invokeAndWait(new Runnable() {
			public void run() {
				try {
					prefs = Preferences.userRoot().node(this.getClass().getName());
					UIManager.setLookAndFeel("com.jtattoo.plaf." + prefs.get("Theme", "noire.Noire") + "LookAndFeel");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				}
				try {
					MotionText window = new MotionText();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MotionText() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setMinimumSize(new Dimension(400, 400));
		frame.setBounds(100, 100, 1200, 785);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.GRAY));
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(null);
		panel_2.add(tabbedPane, BorderLayout.CENTER);

		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 12));

		panel_6 = new JPanel();
		panel_6.setSize(new Dimension(10, 10));
		panel_2.add(panel_6, BorderLayout.SOUTH);
		panel_6.setVisible(state = false);
		panel_6.setLayout(new MigLayout("", "[][][][][grow][]", "[10px,grow]"));

		textField_1 = new JTextField();
		textField_1.setPreferredSize(new Dimension(200, 19));
		panel_6.add(textField_1, "cell 0 0");

		JButton chckbxExact = new JButton("Find");
		chckbxExact.setSize(new Dimension(64, 10));
		chckbxExact.setFont(new Font("Dialog", Font.BOLD, 10));
		panel_6.add(chckbxExact, "cell 1 0");

		JButton btn = new JButton("");
		panel_6.add(btn, "cell 2 0");
		btn.setIcon(new ImageIcon(MotionText.class.getResource("/resources/down.png")));

		JButton lbl = new JButton("");
		panel_6.add(lbl, "cell 3 0");
		lbl.setIcon(new ImageIcon(MotionText.class.getResource("/resources/up.png")));
		
		JLabel lblNewLabel_1 = new JLabel("Made By: Ciunas Bennett");
		lblNewLabel_1.setFont(new Font("Dialog", Font.ITALIC, 10));
		panel_6.add(lblNewLabel_1, "cell 5 0");
		
		
		lbl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (String key : mapper.keySet()) {
					if (key.contentEquals(activeTab) && mapper.get(key).isSearch()) {
						try {
							mapper.get(key).ws.backwards();
						} catch (BadLocationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (String key : mapper.keySet()) {
					if (key.contentEquals(activeTab) && mapper.get(key).isSearch()) {
						try {
							mapper.get(key).ws.forward();
						} catch (BadLocationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		chckbxExact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					search(textField_1);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
		});

		Box left = Box.createVerticalBox();
		left.add(Box.createVerticalStrut(30));

		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				changeEventTab(changeEvent, textField_1);
			}
		};
		tabbedPane.addChangeListener(changeListener);

		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(250, 10));
		panel_3.setBackground(Color.LIGHT_GRAY);
		panel_3.setBorder(new LineBorder(Color.GRAY));
		panel.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new MigLayout("", "[grow]", "[][grow][grow]"));

		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, "cell 0 0,grow");
		panel_5.setLayout(new BorderLayout(0, 0));

		JLabel lblPwd = new JLabel("Working Directory");
		panel_5.add(lblPwd, BorderLayout.CENTER);

		JButton btnChangePwd = new JButton("Set");
		btnChangePwd.setToolTipText("Set the location of the working directory.");
		btnChangePwd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setTreeWD("chooseFile");
			}
		});
		panel_5.add(btnChangePwd, BorderLayout.EAST);

		scrollPane_1 = new JScrollPane();
		panel_4.add(scrollPane_1, "cell 0 1 1 2,grow");

		scrollPane_1.setViewportView(tree);
		scrollPane_1.setFocusable(false);

		JPanel panel_7 = new JPanel();
		panel_7.setLayout(new MigLayout("", "[grow][grow][grow]", "[]"));

		JLabel lblNewLabel = new JLabel("Tree");
		panel_7.add(lblNewLabel, "cell 0 0 2 1");

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "File stuff", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.add(panel_8, BorderLayout.SOUTH);
		panel_8.setLayout(new GridLayout(2, 2, 0, 0));

		JButton btnNewButton_1 = new JButton("Open");
		panel_8.add(btnNewButton_1);
		btnNewButton_1.setToolTipText("Open highlighted file");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addTab(tabbedPane);
			}
		});

		JButton btnNewButton_2 = new JButton("New");
		panel_8.add(btnNewButton_2);
		btnNewButton_2.setToolTipText("Create a new file");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newFile();
			}
		});

		JButton btnNewFile = new JButton("Save");
		panel_8.add(btnNewFile);
		btnNewFile.setToolTipText("Save foreground tab");
		btnNewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile(mapper.get(activeTab));
			}
		});

		JButton btnNewButton_3 = new JButton("Delete");
		panel_8.add(btnNewButton_3);
		btnNewButton_3.setToolTipText("Delete file from working directory");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteFile(tabbedPane);
			}
		});

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Options");
		menuBar.add(mnNewMenu);

		JMenuItem mntmFile = new JMenuItem("Set Font");
		mntmFile.setMnemonic(KeyEvent.VK_E);
		mntmFile.setToolTipText("Set theme of Text Editor");
		mntmFile.addActionListener((ActionEvent event) -> {
			setFont();
		});
		mnNewMenu.add(mntmFile);

		JMenuItem mntmNewMenuItem = new JMenuItem("Set Theme");
		mntmNewMenuItem.setMnemonic(KeyEvent.VK_E);
		mntmNewMenuItem.setToolTipText("Set theme of Text Editor");
		mntmNewMenuItem.addActionListener((ActionEvent event) -> {
			setTheme();
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Set Line Encoding");
		mnNewMenu.add(mntmNewMenuItem_2);
		mntmNewMenuItem_2.setMnemonic(KeyEvent.VK_E);
		mntmNewMenuItem_2.setToolTipText("Set theme of Text Editor");
		mntmNewMenuItem_2.addActionListener((ActionEvent event) -> {
			setLineEncoding();
		});
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Set Python Executable");
		mnNewMenu.add(mntmNewMenuItem_3);
		mntmNewMenuItem_3.setMnemonic(KeyEvent.VK_E);
		mntmNewMenuItem_3.setToolTipText("Set theme of Text Editor");
		mntmNewMenuItem_3.addActionListener((ActionEvent event) -> {
			setPythonLocation();
		});

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Help");
		mntmNewMenuItem_1.setMnemonic(KeyEvent.VK_E);
		mntmNewMenuItem_1.setToolTipText("Set theme of Text Editor");
		mntmNewMenuItem_1.addActionListener((ActionEvent event) -> {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					JOptionPane.showMessageDialog(frame, "No Help For You:", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			});

		}); 
		mnNewMenu.add(mntmNewMenuItem_1);

		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Python");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				python();
			}
		});
		menuBar.add(btnNewButton);

		setTreeWD("display");
		listener = new MyKeyListener();
		frame.setFocusable(false);
	}

	private void setPythonLocation() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFileChooser f = new JFileChooser();
				f.setDialogTitle("Specify Your Python Executable");
				f.showSaveDialog(null);
				if (f.getSelectedFile() != null) {
					setOrGetPref("Python", f.getSelectedFile().toString(), "set");
				} else {
					return;
				}
			}
		});
	}

	protected void python() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (activeTab != null && !activeTab.isEmpty()
						&& activeTab.substring(activeTab.lastIndexOf(".") + 1).contentEquals("py")) {
					for (String key : mapper.keySet()) {
						if (key.contentEquals(activeTab)) {
							if (setOrGetPref("Python", null, "get").contentEquals("UnSet")) { 
										JFileChooser f = new JFileChooser();
										f.setDialogTitle("Specify Your Python Executable");
										f.showSaveDialog(null);
										if (f.getSelectedFile() != null) {
											setOrGetPref("Python", f.getSelectedFile().toString(), "set");
										} else {
											return;
										} 
							}
							saveFile(mapper.get(activeTab));
							try {
								@SuppressWarnings("unused")
								RunPython rp = new RunPython(frame, mapper.get(key).location.toString(),
										setOrGetPref("Python", null, "get"), setOrGetPref("FontSize", null, "get"),
										setOrGetPref("FontType", null, "get"));
							} catch (IOException | BadLocationException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
	}

	/**
	 * Search for a word in JTextArea
	 */
	public void search(JTextField textField) throws BadLocationException {		
		if (!textField.getText().contentEquals("")) { 
			for (String key : mapper.keySet()) { 
				if (key.contentEquals(activeTab) ) {  
					if(mapper.get(key).isSearch()) {
						mapper.get(key).ws.removeHighlight();
					}
					try {   
						WordSearch ws = new WordSearch(mapper.get(key).getJta(), textField);
						if(ws.setFirst() == -1)
							return;
						mapper.get(key).setWs(ws); 
						mapper.get(key).setSearch(true);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
		}  
	}	

	/**
	 * Monitors change in active tab
	 */
	private void changeEventTab(ChangeEvent changeEvent, JTextField jtf) {
		JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
		int index = sourceTabbedPane.getSelectedIndex();
		if (index == -1) {
			activeTab = "";
		} else {
			String temp = activeTab;
			activeTab = sourceTabbedPane.getTitleAt(index);
			for (String key : mapper.keySet()) {
				if (key.contentEquals(temp)) {
					mapper.get(key).getJta().removeKeyListener(listener);
					panel_6.setVisible(state = false);
					jtf.setText("");
				} 
				else if (key.contentEquals(activeTab)) {
					mapper.get(key).getJta().addKeyListener(listener);

					if (mapper.get(key).search) {
						panel_6.setVisible(state = true);
						jtf.setText(mapper.get(key).ws.getWord());
					}
				}
			}
		}
	}

	/**
	 * Create a new file
	 */
	private void newFile() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				File fileToSave = null;
				JFileChooser fileChooser = new JFileChooser(setOrGetPref("Root", null, "get"));
				fileChooser.setDialogTitle("Specify save location.");
				int userSelection = fileChooser.showSaveDialog(frame);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					fileToSave = fileChooser.getSelectedFile();
					try {
						fileToSave.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
					filePath = Paths.get(fileToSave.toString()); 
					addTab(tabbedPane);
					setTreeWD("display");
				}
			}
		});
	}

	/**
	 * Sets the colour and size of font
	 */
	private void setFont() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JPanel myPanel = new JPanel();
				myPanel.setLayout(new MigLayout("", "[grow][grow]", "[grow][grow][grow][grow][grow]"));
				JLabel lblSetHteFont = new JLabel("Font Size:");
				myPanel.add(lblSetHteFont, "cell 0 1,alignx trailing");

				Integer[] ITEMS = { 9, 10, 11, 12, 14, 16, 18, 20, 24, 32 };
				JComboBox<Integer> comboBox = new JComboBox<Integer>(ITEMS);
				comboBox.getModel().setSelectedItem(Integer.parseInt(setOrGetPref("FontSize", null, "get")));
				myPanel.add(comboBox, "cell 1 1,growx");

				String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
				JLabel lblSetFontType = new JLabel("Font Type:");
				myPanel.add(lblSetFontType, "cell 0 2,alignx trailing");
				JComboBox<String> comboBox_3 = new JComboBox<String>(fonts);
				comboBox_3.getModel().setSelectedItem(setOrGetPref("FontType", null, "get"));
				myPanel.add(comboBox_3, "cell 1 2,growx");

				String[] colours = { "Black", "Blue", "Gray", "Green", "Orange", "Red", "White", "Yellow", "Pink" };
				JLabel lblSetFourgroun = new JLabel("Foreground Colour:");
				myPanel.add(lblSetFourgroun, "cell 0 3,alignx trailing");
				JComboBox<String> comboBox_1 = new JComboBox<String>(colours);
				comboBox_1.getModel().setSelectedItem(setOrGetPref("FourgColour", null, "get"));
				myPanel.add(comboBox_1, "cell 1 3,growx");

				JLabel lblSetBackgroundColour = new JLabel("Background Colour:");
				myPanel.add(lblSetBackgroundColour, "cell 0 4,alignx trailing");
				JComboBox<String> comboBox_2 = new JComboBox<String>(colours);
				comboBox_2.getModel().setSelectedItem(setOrGetPref("BackColour", null, "get"));
				myPanel.add(comboBox_2, "cell 1 4,growx");

				int result = JOptionPane.showConfirmDialog(frame, myPanel, "Set Font", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					Integer temp = (Integer) comboBox.getSelectedItem();
					setOrGetPref("FontSize", Integer.toString(temp), "set");
					setOrGetPref("FourgColour", (String) comboBox_1.getSelectedItem(), "set");
					setOrGetPref("BackColour", (String) comboBox_2.getSelectedItem(), "set");
					setOrGetPref("FontType", (String) comboBox_3.getSelectedItem(), "set");
					for (DataNode dn : mapper.values()) {
						dn.changeFont(Integer.toString(temp), (String) comboBox_1.getSelectedItem(),
								(String) comboBox_2.getSelectedItem(), (String) comboBox_3.getSelectedItem());
					}
				}
			}
		});
	}

	/**
	 * Sets the theme of the window
	 */
	private void setTheme() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				String[] values = { "Aluminium", "Smart", "Noire", "Acryl", "Aero", "Fast", "HiFi", "McWin", "Mint",
						"Luna" };
				Object selected = JOptionPane.showInputDialog(frame, "Choose Your  Theme", "Selection",
						JOptionPane.DEFAULT_OPTION, null, values, setOrGetPref("Theme", null, "get")
								.substring(setOrGetPref("Theme", null, "get").lastIndexOf(".") + 1));
				if (selected != null) {
					setOrGetPref("Theme", selected.toString().toLowerCase() + "." + selected.toString(), "set");
					try {
						UIManager.setLookAndFeel(
								"com.jtattoo.plaf." + prefs.get("Theme", "noire.Noire") + "LookAndFeel");
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						e1.printStackTrace();
					}
					frame.setVisible(false);
					SwingUtilities.updateComponentTreeUI(frame);
					frame.setVisible(true);
				}
			}
		});
	}

	/**
	 * Sets line encoding of file
	 */
	private void setLineEncoding() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (JOptionPane.showConfirmDialog(frame, "Use Linux Line Encoding", "Choose Linux",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					setOrGetPref("LineEncoding", "Yes", "set");
				}
			}
		});
	}

	/**
	 * Deletes file
	 */
	protected void deleteFile(JTabbedPane tabbedPane) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (!Files.isDirectory(filePath)) {
					File file = new File(filePath.toString());
					int reply = JOptionPane.showConfirmDialog(frame,
							"Delete Following File: " + filePath.getFileName().toString(), "Delete",
							JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						if (file.delete()) {
							String temp = null;
							for (String key : mapper.keySet()) {
								if (filePath.getFileName().toString().contentEquals(key)) {
									for (int i = 0; i < tabbedPane.getTabCount(); i++) {
										if (tabbedPane.getTitleAt(i).equals(filePath.getFileName().toString()))
											tabbedPane.remove(i);
										temp = key;
									}
								}
							}
							if (temp != null && !temp.isEmpty()) {
								mapper.remove(temp);
								filePath = Paths.get(setOrGetPref("Root", null, "get"));
							}
							setTreeWD("display");
						}  
					}
				} else
					JOptionPane.showMessageDialog(frame, "No File to Delete!");
			}
		});
	}

	/**
	 * Builds a JTree 
	 */
	private static DefaultMutableTreeNode addNodes(File dir) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(dir);
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				node.add(addNodes(file));
			} else {
				node.add(new DefaultMutableTreeNode(file));
			}
		}
		return node;
	}

	/**
	 * Reads text form a JTextArea and writes to specified file.
	 */
	private void saveFile(DataNode dataNode) {
		if (activeTab != null && !activeTab.isEmpty()) {
			try (BufferedWriter bw = Files.newBufferedWriter(dataNode.getLocation())) {
				if (setOrGetPref("LineEncoding", null, "get").contentEquals("Yes")) {
					bw.write(dataNode.getJta().getText());
				} else
					dataNode.getJta().write(bw); 
				displayInfo("Saved: " + dataNode.getLocation().getFileName().toString()); 
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}

		} else {
			JOptionPane.showMessageDialog(frame, "No File to Save!", "Hay", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Checks for user settings, returns setting if present
	 */
	private String setOrGetPref(String id, String value, String SetGet) {
		if (SetGet.equals("get")) {
			String temp = null;
			switch (id) {
			case "Root":
				temp = prefs.get(id, ".");
				break;
			case "FontSize":
				temp = prefs.get(id, "14");
				break;
			case "FourgColour":
				temp = prefs.get(id, "Black");
				break;
			case "BackColour":
				temp = prefs.get(id, "White");
				break;
			case "Theme":
				temp = prefs.get(id, "noire.Noire");
				break;
			case "FontType":
				temp = prefs.get(id, "Calibri");
				break;
			case "LineEncoding":
				temp = prefs.get(id, "No");
				break;
			case "Python":
				temp = prefs.get(id, "UnSet");
				break;
			default:
				break;
			}
			return temp;
		} else
			prefs.put(id, value);
		return "";
	}

	/**
	 * Adds a new tab to the JTabPane, with a close button.
	 */
	private void addTab(JTabbedPane tabbedPane) {  
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (filePath != null && !Files.isDirectory(filePath)) {
					for (String name : mapper.keySet()) {
						if (name.contentEquals(filePath.getFileName().toString())) {
							for (int i = 0; i < tabbedPane.getTabCount(); i++) {
								if (tabbedPane.getTitleAt(i).equals(filePath.getFileName().toString()))
									tabbedPane.setSelectedIndex(i);
							}
							return;
						}
					}
					String line;
					DataNode dn = new DataNode(filePath, setOrGetPref("FontSize", null, "get"),
							setOrGetPref("FourgColour", null, "get"), setOrGetPref("BackColour", null, "get"),
							setOrGetPref("FontType", null, "get"));
					tabbedPane.addTab(filePath.getFileName().toString(), null, dn.getJs());
					tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1,
							new ButtonTabComponent(tabbedPane, mapper, filePath.getFileName().toString()));
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
					try (BufferedReader br = new BufferedReader(new FileReader(filePath.toString()))) {
					    Document doc = dn.getJta().getDocument();
						while ((line = br.readLine()) != null) {
							try {
								doc.insertString(doc.getLength(), line + "\n", null);
							} catch (BadLocationException e) { 
								e.printStackTrace();
							} 
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					dn.getJta().addKeyListener(listener);
					dn.getJta().setCaretPosition(0);
					mapper.put(filePath.getFileName().toString(), dn);
				} else {
					JOptionPane.showMessageDialog(frame, "No File Highlighted!", "Hay", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Displays info about file saved
	 */
	private void displayInfo(String info) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Integer waitSeconds = 3;
				lblNewLabel_2.setIcon(new ImageIcon(MotionText.class.getResource("/resources/ic_penguin.gif")));
				Timer timer = new Timer(waitSeconds * 1000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						lblNewLabel_2.setText("");
						lblNewLabel_2.setIcon(null);
					}
				});
				timer.start();
			}
		});
	}

	/**
	 * Sets the tree view directory for user.
	 */
	private void setTreeWD(String type) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (type.contentEquals("chooseFile")) {
					JFileChooser f = new JFileChooser();
					f.setDialogTitle("Specify Your Working Directory");
					f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					f.showSaveDialog(null);
					if (f.getSelectedFile() != null) {
						setOrGetPref("Root", f.getSelectedFile().toString(), "set");
						tree = new JTree(addNodes(new File(f.getSelectedFile().toString())));
					} else {
						return;
					}
				} else
					tree = new JTree(addNodes(new File(setOrGetPref("Root", null, "get"))));

				tree.setRootVisible(true);
				tree.setShowsRootHandles(false);
				tree.setToggleClickCount(10);
				tree.setFocusable(false);
				tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
				tree.setCellRenderer(new FileTreeCellRenderer());
				scrollPane_1.setViewportView(tree);
				tree.addTreeSelectionListener(new TreeSelectionListener() {
					@Override
					public void valueChanged(TreeSelectionEvent e) {
						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree
								.getLastSelectedPathComponent();
						Object userObject = selectedNode.getUserObject(); 
						filePath = Paths.get(userObject.toString());  
					}
				});

				MouseListener ml = new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						int selRow = tree.getRowForLocation(e.getX(), e.getY());
						if (selRow != -1) {
							if (e.getClickCount() == 2) {
								if (filePath != null && !Files.isDirectory(filePath)) {
									addTab(tabbedPane);
								}
							}
						}
					}
				};
				tree.addMouseListener(ml);
			}
		});
	}

	/**
	 * Helper class KeyListener
	 */
	public class MyKeyListener implements KeyListener {

		boolean ctl = false;
		boolean s = false;
		boolean f = false;
		boolean z = false;
		boolean x = false;

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_CONTROL:
				ctl = true;
				break;
			case KeyEvent.VK_X: 
				x = true;
				break;
			case KeyEvent.VK_S:
				s = true;
				break;
			case KeyEvent.VK_F:
				f = true;
				break;
			case KeyEvent.VK_Z: 
				z = true;
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
			if (ctl == true && s == true) {
				saveFile(mapper.get(activeTab)); 
			} else if (ctl == true && f == true) {
				state = !state;
				panel_6.setVisible(state);
			}else if (ctl == true && z == true) {
				for (String key : mapper.keySet()) {
					if (key.contentEquals(activeTab)) {
						mapper.get(key).undo();
					}
				}
			}else if (ctl == true && x == true) {
				for (String key : mapper.keySet()) { 
					if (key.contentEquals(activeTab)) {
						mapper.get(key).redo();
					}
				}
			}

			switch (e.getKeyCode()) {
			case KeyEvent.VK_CONTROL:
				ctl = false;
				break;
			case KeyEvent.VK_X:
				x = false;
				break;
			case KeyEvent.VK_S:
				s = false;
				break;
			case KeyEvent.VK_F:
				f = false;
				break;
			case KeyEvent.VK_Z:
				z = false;
				break;
			default:
				s = false;
				ctl = false;
				x = false;
				f = false;
				z = false;
				break;
			}
		}
	}

	/**
	 * Helper class for JTree Creation
	 */
	class FileTreeCellRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			if (value instanceof DefaultMutableTreeNode) {
				value = ((DefaultMutableTreeNode) value).getUserObject();
				if (value instanceof File) {
					value = ((File) value).getName();
				}
			}
			return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		}
	}
}