package notepad;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.BevelBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException; 
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

	private JFrame frame;
	private JPanel panel_4;
	private static DefaultMutableTreeNode root;
	private static DefaultTreeModel treeModel;
	private JTree tree;
	private Preferences prefs;
	private String fileLocation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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

		String rootDir = setGetPreference("Root", "", "get");
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

		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("Blank", null, scrollPane, null);

		JTextArea textArea = new JTextArea();
		textArea.setForeground(Color.GREEN);
		textArea.setBackground(Color.BLACK);
		textArea.setFont(new Font("SansSerif", Font.PLAIN, 15));
		scrollPane.setViewportView(textArea);

		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(200, 10));
		panel_3.setBackground(Color.LIGHT_GRAY);
		panel_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BorderLayout(0, 0));

		panel_4 = new JPanel();
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
				fileLocation = rootDir + "/" + userObject;
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
			}
		});

		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6, "cell 0 2,grow");
		panel_6.setLayout(new GridLayout(2, 1, 0, 0));

		JButton btnNewButton_1 = new JButton("Open");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 readFile(fileLocation,  textArea);
				 
				 tabbedPane.addTab("Tab 2", null, new JLabel("This is tab 2"));
				 
			}
		});
		panel_6.add(btnNewButton_1);

		JButton btnNewFile = new JButton("New File");
		panel_6.add(btnNewFile);

		getList(root, fileRoot);
	}

	public void changeRoot() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFileChooser f = new JFileChooser();
				f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				f.showSaveDialog(null);
				setGetPreference("Root", f.getSelectedFile().toString(), "set");
			}
		});
	}

	public void getList(DefaultMutableTreeNode node, File f) {

		File[] files = f.listFiles();
		if (files == null)
			return;

		for (File file : files) {
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
			node.add(childNode);

			if (file.isHidden()) {

			} else if (file.isDirectory()) {
				getList(childNode, file);
			}
		}
	}

	public String setGetPreference(String id, String value, String SetGet) {
		// This will define a node in which the preferences can be stored
		prefs = Preferences.userRoot().node(this.getClass().getName());

		if (SetGet.equals("get")) {
			return (prefs.get(id, ""));
		} else
			prefs.put(id, value);
		return "";
	}
	
	
	/**Read a file from specified location and write to specified JTextArea
	 * @param location
	 * @param jtextarea
	 */
	public void readFile(String location, JTextArea jtextarea ) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				try (BufferedReader br = new BufferedReader(new FileReader(location)))
				{
					String line;
					while ((line = br.readLine()) != null) {
						jtextarea.append(line + "\n");
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
		
		
	
}
