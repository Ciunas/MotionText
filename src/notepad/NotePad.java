package notepad;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;  
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException; 
import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
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
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.prefs.Preferences;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JTree;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.Font; 

/**
 * @author ciunas
 *
 */
 
public class NotePad {
	
	HashMap <String, DataNode> mapper = new HashMap<String, DataNode>(); 
	private static Preferences prefs;
	private String filePath;  
	private String fileName; 
	private String activeTab;
	JScrollPane scrollPane_1;
	JFrame frame;
	JTree tree; 
	
	/**
	 * Launch the application.
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
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
					NotePad window = new NotePad();
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
	public NotePad() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(400, 400));
		frame.setBounds(100, 100, 1054, 771);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Made By: Ciunas Bennett.");
		lblNewLabel_1.setFont(new Font("Dialog", Font.ITALIC, 12));
		panel_1.add(lblNewLabel_1, BorderLayout.EAST);

		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel_2.add(tabbedPane, BorderLayout.CENTER);
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				activeTab = sourceTabbedPane.getTitleAt(index);
				System.out.println(activeTab);
			}
		};
		tabbedPane.addChangeListener(changeListener);

		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(250, 10));
		panel_3.setBackground(Color.LIGHT_GRAY);
		panel_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new MigLayout("", "[grow]", "[][grow][grow][grow]"));
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, "cell 0 0,grow");
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JLabel lblPwd = new JLabel("PWD");
		panel_5.add(lblPwd, BorderLayout.WEST);
		
		JButton btnChangePwd = new JButton("Change PWD");
		btnChangePwd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setTreeWD("chooseFile"); 
			}
		});
		panel_5.add(btnChangePwd, BorderLayout.CENTER);
		
		scrollPane_1 = new JScrollPane();
		panel_4.add(scrollPane_1, "cell 0 1 1 2,grow");
		 
		scrollPane_1.setViewportView(tree); 
	
		JPanel panel_7 = new JPanel(); 
		panel_7.setLayout(new MigLayout("", "[grow][grow][grow]", "[]"));
		
		JLabel lblNewLabel = new JLabel("Tree");
		panel_7.add(lblNewLabel, "cell 0 0 2 1");

 
		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6, "cell 0 3,grow");
		panel_6.setLayout(new GridLayout(2, 1, 0, 0));
		 
		JButton btnNewButton_1 = new JButton("Open Tab");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(filePath != null) {
					addTab(tabbedPane, filePath);
				}else {
					JOptionPane.showMessageDialog(frame, "No File Highlighted!");
				}				
			}
		});
		
		JButton btnNewButton_2 = new JButton("New Tab");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int reply = JOptionPane.showConfirmDialog(frame, "Save to working directory:", "Save",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					String name = JOptionPane.showInputDialog(frame, "File Name:");
					if (!name.isEmpty()) {
						File fileToSave = new File(setOrGetPref("Root", null, "get") + "/" + name);
						try {
							fileToSave.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} else {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify save location.");
					int userSelection = fileChooser.showSaveDialog(frame);
					if (userSelection == JFileChooser.APPROVE_OPTION) {
						File fileToSave = fileChooser.getSelectedFile();
						try {
							fileToSave.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				setTreeWD("display");
			}
		});
		panel_6.add(btnNewButton_2);
		panel_6.add(btnNewButton_1);

		JButton btnNewFile = new JButton("Save");
		btnNewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				saveFile(mapper.get(activeTab));
			}
		});
		panel_6.add(btnNewFile);

		JButton btnNewButton_3 = new JButton("Delete");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int reply = JOptionPane.showConfirmDialog(frame, "Delete Following File: " + fileName,
						"Delete", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					File file = new File(filePath);
					if (file.delete()) {
						JOptionPane.showMessageDialog(frame, "Deleted");
						setTreeWD("display");
					}else
						JOptionPane.showMessageDialog(frame, "Error fiel not Deleted:");
				}
			}
		});
		panel_6.add(btnNewButton_3); 
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmFile = new JMenuItem("Set Font");
		mnNewMenu.add(mntmFile);

		JMenuItem mntmNewMenuItem = new JMenuItem("Set Theme");
		mntmNewMenuItem.setMnemonic(KeyEvent.VK_E);
		mntmNewMenuItem.setToolTipText("Set theme of Text Editor");
		mntmNewMenuItem.addActionListener((ActionEvent event) -> {
			String[] values = {"Aluminium", "Smart", "Noire", "Acryl", "Aero", "Fast", "HiFi", "Texture", "McWin", "Mint", "Smart", "Luna", "Texture"};
		 
			Object selected = JOptionPane.showInputDialog(frame, "Choose Your  Theme", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "0");
			if ( selected != null ){
				System.out.println(selected.toString());
				setOrGetPref("Theme", selected.toString().toLowerCase() + "."  + selected.toString() , "set");
				frame.setVisible(false);
					try {
						UIManager.setLookAndFeel("com.jtattoo.plaf." + prefs.get("Theme", "noire.Noire") + "LookAndFeel");
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) { 
						e1.printStackTrace();
					}				
				frame.setVisible(true);
			}else{
			    System.out.println("User cancelled");
			} 
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		setTreeWD("display");
	}
	
	
	/**Builds a JTree
	 * @param dir
	 * @return DefaultMutableTreeNode
	 */
	public static DefaultMutableTreeNode addNodes(File dir) {
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
	
	/**Reads text form a JTextArea and writes to specified file.
	 * @param filePath
	 * @param jta
	 */
	public void saveFile(DataNode dataNode) { 
		System.out.println(dataNode.getLocation());
		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(dataNode.getLocation()))){  
			bw.write(dataNode.getJta().getText());  
		} catch (Exception e) { 
			System.err.println("Error: " + e.getMessage());
		}
	}
		
	/**Checks for user settings, returns setting if present
	 * @param id
	 * @param value
	 * @param SetGet
	 * @return
	 */
	protected String setOrGetPref(String id, String value, String SetGet) { 
		if (SetGet.equals("get")) {
			if(id.contains("Root")) {
				return (prefs.get(id, "."));
			}else
				return (prefs.get(id, ""));
		} else
			prefs.put(id, value);
		System.out.println(value);
		return "";
	}
	
	/**Adds a new tab to the JTabPane, with a close button.
	 * @param tabbedPane
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 */
	protected void addTab(JTabbedPane tabbedPane, String location) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				System.out.println(location);
				String line;
				DataNode dn = new DataNode(filePath);
				tabbedPane.addTab(fileName, null, dn.getJs());
				tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, new ButtonTabComponent(tabbedPane));
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1); 	
				try (BufferedReader br = new BufferedReader(new FileReader(location))) {
					while ((line = br.readLine()) != null) {
						dn.getJta().append(line + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				mapper.put(fileName, dn);
			}
		});
	}
	
	/**Adds a new tab to the JTabPane, with a close button.
	 * @param tabbedPane
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 */
	protected void addTab(JTabbedPane tabbedPane)  {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() { 
				DataNode dn = new DataNode(activeTab);
				tabbedPane.addTab(fileName, null, dn.getJs());				
				tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, new ButtonTabComponent(tabbedPane));
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
				dn.getJs().setViewportView(dn.getJta());    
				mapper.put(fileName, dn);  
			}
		}); 
	}
	
	/**Sets the tree view directory for user. 
	 */
	protected void setTreeWD(String type) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				if(type.contentEquals("chooseFile")) {
					JFileChooser f = new JFileChooser();
					f.setDialogTitle("Specify Your Working Directory");
					f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					f.showSaveDialog(null);
					setOrGetPref("Root", f.getSelectedFile().toString(), "set");  
					tree = new JTree(addNodes(new File(f.getSelectedFile().toString())));
				}else
					tree = new JTree(addNodes(new File(setOrGetPref("Root", null, "get"))));				
				tree.setRootVisible(true);
				tree.setShowsRootHandles(true);
				tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
				tree.setCellRenderer(new FileTreeCellRenderer());
				scrollPane_1.setViewportView(tree);
				tree.addTreeSelectionListener(new TreeSelectionListener() {
					@Override
					public void valueChanged(TreeSelectionEvent e) {
						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
						Object userObject = selectedNode.getUserObject();
						filePath = userObject.toString();
						fileName = filePath.replaceFirst(".*/([^/?]+).*", "$1");
					}
				});
			}
		});
	}
	
	
	/**helper class for JTree Creation
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