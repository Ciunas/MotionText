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
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
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
import java.awt.event.ActionEvent; 

/**
 * @author ciunas
 *
 */
 
public class NotePad {

	HashMap <String, DataNode> mapper = new HashMap<String, DataNode>();
	private static DefaultMutableTreeNode root;
	private static DefaultTreeModel treeModel;
	private Preferences prefs;
	private String filePath; 
	@SuppressWarnings("unused")
	private String fileName;
	@SuppressWarnings("unused")
	private String activeTab;
	JFrame frame;
	JTree tree;
	int i = 0;
	
	
	/**
	 * Launch the application.
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 */
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		EventQueue.invokeAndWait(new Runnable() {
			public void run() {
				// getResourceAsFile();
				try {
					// UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
					// UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
					UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
					// fUIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
					// UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
					// UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
					// UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
					// UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
					// UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
					// UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
					// UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
					// UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
					// UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
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
		
		String rootDir = setOrGetPref("Root", "", "get");
		File fileRoot = new File(rootDir);

		root = new DefaultMutableTreeNode(new FileNode(fileRoot));
		treeModel = new DefaultTreeModel(root);  
		
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(400, 400));
		frame.setBounds(100, 100, 1054, 771);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		
		JLabel lblNewLabel_1 = new JLabel("Made By: Ciunas Bennet");
		panel_1.add(lblNewLabel_1);

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
			}
		};
		tabbedPane.addChangeListener(changeListener);

		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(200, 10));
		panel_3.setBackground(Color.LIGHT_GRAY);
		panel_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow]"));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_4.add(scrollPane_1, "cell 0 0 1 2,grow");
		
		
		tree = new JTree(treeModel);
		tree.setShowsRootHandles(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				Object userObject = selectedNode.getUserObject(); 
				if(rootDir.contains(userObject.toString())) {
					filePath = rootDir;
				}else
					filePath = rootDir + "/" + userObject;
				fileName = userObject.toString(); 
			}
		});

		JPanel panel_5 = new JPanel();
		scrollPane_1.setViewportView(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		panel_5.add(tree, BorderLayout.CENTER);
	
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7, BorderLayout.NORTH);
		panel_7.setLayout(new MigLayout("", "[grow][grow][grow]", "[]"));
		
		JLabel lblNewLabel = new JLabel("Tree");
		panel_7.add(lblNewLabel, "cell 0 0 2 1");

		JButton btnNewButton = new JButton("Set Root");
		panel_7.add(btnNewButton, "cell 2 0");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeRoot();
				//treeModel.reload(root);
				//getList(root, fileRoot);
			}
		});
 
		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6, "cell 0 2,grow");
		panel_6.setLayout(new GridLayout(2, 1, 0, 0));
		 
		JButton btnNewButton_1 = new JButton("Open");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			  
				 addTab(tabbedPane, filePath); 			}
		});
		
		JButton btnNewButton_2 = new JButton("New Tab");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");
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
		
		JButton btnNewButton_3 = new JButton("Change Root");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultMutableTreeNode rootNode2 = new DefaultMutableTreeNode(new File("/home/ciunas/Documents/Work"));
				treeModel.setRoot(rootNode2);
				treeModel.reload();
			}
		});
		panel_6.add(btnNewButton_3);
	
		getList(root, fileRoot);
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
		prefs = Preferences.userRoot().node(this.getClass().getName());
		if (SetGet.equals("get")) {
			return (prefs.get(id, ""));
		} else
			prefs.put(id, value);
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
				//System.out.println(fileName);
				DataNode dn = new DataNode(activeTab);
				tabbedPane.addTab(fileName, null, dn.getJs());				
				tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, new ButtonTabComponent(tabbedPane));
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
				dn.getJs().setViewportView(dn.getJta());   
				//System.out.println(fileName);
				mapper.put(fileName, dn); 
				//System.out.println(mapper.get);
				//tabbedPane.get
			}
		}); 
	}
	
	/**Sets the root directory for user.
	 * 
	 */
	protected void changeRoot() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFileChooser f = new JFileChooser();
				f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				f.showSaveDialog(null);
				setOrGetPref("Root", f.getSelectedFile().toString(), "set");   //save root dir to preferences
			}
		});
	}

	
	/**Builds tree list for display.
	 * @param node
	 * @param file
	 */
	protected void getList(DefaultMutableTreeNode node, File file) {
		File[] files = file.listFiles();
		if (files == null)
			return;
		for (File f : files) {
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(f));
			node.add(childNode);

			if (f.isHidden()) {
			} else if (f.isDirectory()) {
				getList(childNode, f);
			}
		}
	}
	
	
	/**Read a file from specified location and write to specified JTextArea
	 * @param location
	 * @param jtextarea
	 *//*
	protected void readFile(String location, DataNode dataNode ) {
//		EventQueue.invokeLater(new Runnable() {
//			@Override
//			public void run() 
				System.out.println(location);
				//System.out.println(dataNode.getNumber());
				try (BufferedReader br = new BufferedReader(new FileReader(location)))
				{
					String line;
					while ((line = br.readLine()) != null) {
						dataNode.getJta().append(line + "\n");
					} 
				} catch (IOException e) {
					e.printStackTrace();
				}
//			}
//		}); 
		
	}
		
		*/
	
}
