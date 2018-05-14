//This class is part of the guiCalculator package.
package guiCalculator;

//Needed to use BorderLayouts
import java.awt.BorderLayout;

//Needed to use Colors.
import java.awt.Color;

//Needed to use Components.
import java.awt.Component;

//Needed to use Dimensions.
import java.awt.Dimension;

//Needed to use Fonts.
import java.awt.Font;

//Needed to use GraphicsEnvironments.
import java.awt.GraphicsEnvironment;

//Needed to use ActionEvents.
import java.awt.event.ActionEvent;

//Needed to use ActionListeners.
import java.awt.event.ActionListener;

//Needed to use KeyEvents.
import java.awt.event.KeyEvent;

//Needed to use WindowAdapters.
import java.awt.event.WindowAdapter;

//Needed to use WindowEvents.
import java.awt.event.WindowEvent;

//Needed to use Vectors.
import java.util.Vector;

//Needed to use JFrames.
import javax.swing.JFrame;

//Needed to use JLists.
import javax.swing.JList;

//Needed to use JMenus.
import javax.swing.JMenu;

//Needed to use JMenuBars.
import javax.swing.JMenuBar;

//Needed to use JMenuItems.
import javax.swing.JMenuItem;

//Needed to use JScrollPanes.
import javax.swing.JScrollPane;

//Needed to use KeyStrokes.
import javax.swing.KeyStroke;

//Needed to use ListCellRenderers.
import javax.swing.ListCellRenderer;

//Needed to use UIManagers.
import javax.swing.UIManager;

//Needed to use WindowConstants.
import javax.swing.WindowConstants;

//This class constitutes the GUICalculator program and it constituents.
//This class will control the main display that the user will see and interact with.
public class GUICalculator extends JFrame
{
	//This is the serialVersionUID that is the tag in regards to identifying this JFrame.
	private static final long serialVersionUID=1L;
	
	//This is the instance of a BinaryPanel that will allow the user to
	//perform arithmetic on binary numbers.
	private BinaryPanel binaryPanel;
	
	//ADD THE OTHER PANELS LATER!
	
	//This will contain the menu items that will be used to show and select the fonts
	//provided by the system so the user can change the font of the various components
	//of the GUICalculator.
	private JList<JMenuItem> systemFontsList;
	
	//This will be an instance of the main menu bar near the top of the GUICalculator interface.
	//This will show the topmost menus that the user can access.
	private JMenuBar mainMenuBar;
	
	//This static array will contain the main menus that the user can access in order to edit various
	//aspects of the GUICalculator and/or change calculation modes.
	private JMenu[] mainMenus;
	
	//This static array will contain the items that can be directly accessible from the main menus.
	//Some of these items might contain more sub menus and/or sub menu items.
	private JMenuItem[] mainMenuItems;
	
	//This denotes the JScrollPane that will allow the user to scroll through the various fonts
	//provided by the system and maybe select one of them in order to change the fonts
	//of various components of the GUICalculator.
	private JScrollPane systemFontsScrollPane;
	
	//This static String array will contain the font family names of the fonts provided by the
	//system. This static String array will also help in setting up the fonts that the user can select
	//and edit the various components of the GUICalculator with.
	private String[] systemFontsFamilyNames;
	
	//This vector will contain all the fonts provided by the system, as JMenuItems, that the user
	//will see and/or be able to interact with.
	private Vector<JMenuItem> systemFontsVector;
	
	//This is the default constructor of the GUICalculator.
	//This constructor initializes the GUICalculator and associated components
	//so the program can get up and running for the user to be able to see and interact with.
	public GUICalculator()
	{
		//We set up the title of the program by accessing the super class'
		//constructor that takes a String argument.
		super("Rachid's Gui Calculator");
		
		//We set up the various aspects of the UI that will, hopefully, enhance the visuals
		//of the GUICalculator and increase its interactivity.
		UIManager.put("Menu.font", new Font(null, Font.PLAIN, 20));
		UIManager.put("Menu.selectionBackground", Color.YELLOW);
		UIManager.put("MenuBar.background", Color.BLACK);
		UIManager.put("MenuItem.font", new Font(null, Font.PLAIN, 20));
		UIManager.put("MenuItem.selectionBackground", Color.YELLOW);
		UIManager.put("ToolTip.background", Color.YELLOW);
		UIManager.put("ToolTip.font", new Font(null, Font.PLAIN, 20));
		
		//Here we add a WindowListener to the super class.
		//This is added because we need to clear and nullify all associated components
		//of the GUICalculator in order to assist the Java garbage collector in garbage collection.
		super.addWindowListener
		(
			new WindowAdapter()
			{
				@Override
				public void windowClosing(WindowEvent arg0) 
				{
					GUICalculator.this.binaryPanel.clear();
					
					//CLEAR OTHER PANELS HERE LATER!
					
					GUICalculator.this.systemFontsVector.clear();
					
					GUICalculator.this.binaryPanel=null;
					
					//NULLIFY OTHER PANELS HERE LATER!
					
					GUICalculator.this.systemFontsList=null;
					GUICalculator.this.mainMenuBar=null;
					GUICalculator.this.mainMenus=null;
					GUICalculator.this.mainMenuItems=null;
					GUICalculator.this.systemFontsScrollPane=null;
					GUICalculator.this.systemFontsFamilyNames=null;
					GUICalculator.this.systemFontsVector=null;
					
					GUICalculator.this.dispose();
				}
			}
		);
		
		//We set the background of content pane of the super class to be black.
		//The reason for the color black is because I believe that it matches with the other color
		//combinations of the GUICalculator.
		super.getContentPane().setBackground(Color.BLACK);
		
		//We set the default close operation of the super class, and consequently the GUICalculator class,
		//to exit on close so as to not consume any more memory after the GUICalculator
		//has been disposed.
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//We set the main menu bar and associated components to be ready to be displayed and
		//for the user to interact with.
		this.setMainMenuBar();
		
		//We then set the different panels that will allow the user to access
		//the different arithmetic modes that the GUICalculator provides.
		this.binaryPanel=new BinaryPanel();
		
		///ADD THE OTHER PANELS HERE LATER!
		
		//We set the super class' menu bar, and consequently the GUICalculator's menu bar, to be
		//the main menu bar we previously set up.
		super.setJMenuBar(this.mainMenuBar);
		
		//We set the super class, and consequently the GUICalculator class, to not be resizable
		//because the GUICalculator will have specific dimensions.
		super.setResizable(false);
		
		//We set the size of the super class, and consequently the GUICalculator class, to have a width of
		//720 pixels and a height of 480 pixels.
		super.setSize(720, 480);
		
		//We set the super class, and consequently the GUICalculator class, to be visible
		//so the user can actually see and interact with the program.
		super.setVisible(true);
	}
	
	//This function sets up the main menu bar and associated components
	//so the main menu bar can be ready for display and for the user to be able to
	//interact with it.
	private void setMainMenuBar()
	{
		//We set up the main menu items that will be directly below
		//the main menu bar selections in the menu hierarchy in the
		//GUICalculator.
		this.mainMenuItems=new JMenuItem[]
		{
			new JMenuItem("Binary"),
			new JMenuItem("Octal"),
			new JMenuItem("Decimal"),
			new JMenuItem("Hexadecimal"),
			new JMenuItem("Redo"),
			new JMenuItem("Undo"),
			new JMenuItem("Quit"),
		};
		
		//We add an actionListener to the first mainMenuItem which will switch
		//the current calculation mode into the binary calculation mode.
		this.mainMenuItems[0].addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					if(GUICalculator.this.getContentPane().getComponentCount()==1)
					{
						GUICalculator.this.getContentPane().remove(0);
					}
					
					GUICalculator.this.add(GUICalculator.this.binaryPanel, BorderLayout.CENTER);
					GUICalculator.this.revalidate();
					GUICalculator.this.repaint();
				}
			}
		);
		
		//COMMENT THIS LATER!
		this.mainMenuItems[1].addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					
				}
			}
		);
		
		//COMMENT THIS LATER!
		this.mainMenuItems[2].addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					
				}
			}
		);
		
		//COMMENT THIS LATER!
		this.mainMenuItems[3].addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					
				}
			}
		);
		
		//We add an actionListener to the fifth mainMenuItem that will allow the user
		//to redo edits made to the currently displayed calculation mode on the 
		//GUICalculator.
		this.mainMenuItems[4].addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					if(GUICalculator.this.getContentPane().getComponentCount()==1&&GUICalculator.this.getContentPane().getComponent(0)==GUICalculator.this.binaryPanel)
					{
						GUICalculator.this.binaryPanel.redo();
					}
					
					//ADD THIS FOR OTHER PANELS LATER!
				}
			}
		);
		
		//We add an actionListener to the sixth mainMenuItem that will allow the user
		//to undo edits made to the currently displayed calculation mode on the 
		//GUICalculator.
		this.mainMenuItems[5].addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					if(GUICalculator.this.getContentPane().getComponentCount()==1&&GUICalculator.this.getContentPane().getComponent(0)==GUICalculator.this.binaryPanel)
					{
						GUICalculator.this.binaryPanel.undo();
					}
					
					//ADD THIS FOR OTHER PANELS LATER!
				}
			}
		);
		
		//We add an actionListener to the seventh mainMenuItem that will allow the user
		//to quit the program in another way in addition to pressing the close button at the top-right
		//corner of the GUICalculator window.
		this.mainMenuItems[6].addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					GUICalculator.this.binaryPanel.clear();
					
					//CLEAR OTHER PANELS HERE LATER!
					
					GUICalculator.this.systemFontsVector.clear();
					
					GUICalculator.this.binaryPanel=null;
					
					//NULLIFY OTHER PANELS HERE LATER!
					
					GUICalculator.this.systemFontsList=null;
					GUICalculator.this.mainMenuBar=null;
					GUICalculator.this.mainMenus=null;
					GUICalculator.this.mainMenuItems=null;
					GUICalculator.this.systemFontsScrollPane=null;
					GUICalculator.this.systemFontsFamilyNames=null;
					GUICalculator.this.systemFontsVector=null;
					
					GUICalculator.this.dispose();
				}
			}
		);
		
		//We set the accelerators that will act as keyboard shortcuts to access the mainMenuItems.
		//This will allow the user to access the mainMenuItems by using keyboard shortcuts in addition to
		//pressing the mainMenuItem itself.
		this.mainMenuItems[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.ALT_DOWN_MASK));
		this.mainMenuItems[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.ALT_DOWN_MASK));
		this.mainMenuItems[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.ALT_DOWN_MASK));
		this.mainMenuItems[3].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.ALT_DOWN_MASK));
		this.mainMenuItems[4].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));
		this.mainMenuItems[5].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		this.mainMenuItems[6].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		
		//We set the tool tip texts for the mainMenuItems that will give a brief description of what
		//each mainMenuItem does and the keyboard combination to activate the mainMenuItem
		//the user hovers against.
		this.mainMenuItems[0].setToolTipText("Switches the GUI Calculator to Binary Mode.");
		this.mainMenuItems[1].setToolTipText("Switches the GUI Calculator to Octal Mode.");
		this.mainMenuItems[2].setToolTipText("Switches the GUI Calculator to Decimal Mode.");
		this.mainMenuItems[3].setToolTipText("Switches the GUI Calculator to Hexadecimal Mode.");
		this.mainMenuItems[4].setToolTipText("Redoes the last edit made to the GUI Calculator.");
		this.mainMenuItems[5].setToolTipText("Undoes the last edit made to the GUI Calculator.");
		this.mainMenuItems[6].setToolTipText("Closes the GUI Calculator.");
		
		//We set up the main menus that the user will be able to select at the topmost
		//of the menu hierarchy, the menus include changing the calculation modes of
		//the GUICalculator, editing the GUICalculator, and/or quitting the GUICalculator.
		this.mainMenus=new JMenu[]
		{
			new JMenu("Mode"),
			new JMenu("Edit"),
			new JMenu("Options"),
			new JMenu("Fonts")
		};
		
		//We add the appropriate main menu items under the first of the main menus.
		this.mainMenus[0].add(this.mainMenuItems[0]);
		this.mainMenus[0].add(this.mainMenuItems[1]);
		this.mainMenus[0].add(this.mainMenuItems[2]);
		this.mainMenus[0].add(this.mainMenuItems[3]);
		
		//We add the appropriate main menu items under the second of the main menus.
		this.mainMenus[1].add(this.mainMenuItems[4]);
		this.mainMenus[1].add(this.mainMenuItems[5]);
		this.mainMenus[1].add(this.mainMenus[3]);
		
		//We add the appropriate main menu item under the third of the main menus.
		this.mainMenus[2].add(this.mainMenuItems[6]);
		
		//We set the background colors of the appropriate main menu items.
		this.mainMenus[0].setForeground(Color.WHITE);
		this.mainMenus[1].setForeground(Color.WHITE);
		this.mainMenus[2].setForeground(Color.WHITE);
		
		//We set the tool tip texts for the main menus that will explain what sub menus one should expect and/or
		//what the main menu does.
		this.mainMenus[0].setToolTipText("Menu to select the calculation mode of the GUI Calculator.");
		this.mainMenus[1].setToolTipText("Menu to edit various components of the GUI Calculator.");
		this.mainMenus[2].setToolTipText("Menu to select various options pertaining to the GUI Calculator.");
		this.mainMenus[3].setToolTipText("Menu to select various fonts provided by the GUI Calculator.");
		
		//We retrieve the font family names provided by the system so the user can edit the fonts of 
		//certain components of the GUI Calculator.
		this.systemFontsFamilyNames=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		//We initialize the systemFontsVector in order to get ready to populate the
		//systemFontsList with the provided system fonts.
		this.systemFontsVector=new Vector<JMenuItem>();
		
		//This for loop will be used to populate the systemFontsVector with the
		//provided system fonts that will be used to populate the systemFontsList with.
		for(String systemFontsFamilyName:this.systemFontsFamilyNames)
		{
			JMenuItem fontMenuItem=new JMenuItem(systemFontsFamilyName);
			fontMenuItem.setFont(new Font(systemFontsFamilyName, Font.PLAIN, 20));
			fontMenuItem.setToolTipText(fontMenuItem.getFont().getFontName());
			this.systemFontsVector.add(fontMenuItem);
		}
		
		//We initialize the systemFontsList with the system fonts that the systemFontsVector
		//has been populated with, essentially populating the systemFontsList with the same items.
		this.systemFontsList=new JList<JMenuItem>(this.systemFontsVector);
		
		//We set up the cell renderer that will control how the items in the systemFontsList are displayed.
		//In this case we set the cell renderer to be able to show the font in its actual font and allow the user
		//to select the font that they wish to edit various components of the GUICalculator with.
		this.systemFontsList.setCellRenderer
		(
			new ListCellRenderer<JMenuItem>()
			{
				@Override
				public Component getListCellRendererComponent(JList<? extends JMenuItem> arg0, JMenuItem arg1, int arg2, boolean arg3, boolean arg4) 
				{
					if(arg3)
					{
						arg1.setBackground(Color.YELLOW);
						
						UIManager.put("ToolTip.font", arg1.getFont());
						
						for(JMenu mainMenu:GUICalculator.this.mainMenus)
						{
							mainMenu.setFont(arg1.getFont());
						}
						
						for(JMenuItem mainMenuItem:GUICalculator.this.mainMenuItems)
						{
							mainMenuItem.setFont(arg1.getFont());
						}
						
						if(GUICalculator.this.getContentPane().getComponentCount()==1&&GUICalculator.this.getContentPane().getComponent(0)==GUICalculator.this.binaryPanel)
						{
							GUICalculator.this.binaryPanel.setFont(arg1.getFont());
						}
						
						//ADD THE OTHER PANELS LATER!
					}
					
					else
					{
						arg1.setBackground(Color.WHITE);
					}
					
					return arg1;
				}
			}
		);
		
		//We set up the systemFontsScrollPane that will allow the user to scroll through and select
		//the various fonts provided by the system.
		this.systemFontsScrollPane=new JScrollPane(this.systemFontsList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//We set up the dimensions that the systemFontsScrollPane will have.
		this.systemFontsScrollPane.setPreferredSize(new Dimension(540, 480));
		
		//We add the systemFontsScrollPane the font mainMenu so the user can access
		//the various fonts provided by the system.
		this.mainMenus[3].add(this.systemFontsScrollPane);
		
		//We initialize the mainMenuBar which will be the menu bar that the user
		//will first see when they open the GUICalculator program.
		this.mainMenuBar=new JMenuBar();
		
		//We add the appropriate mainMenus to the mainMenuBar to get
		//ready for display and/or interaction by the user.
		this.mainMenuBar.add(this.mainMenus[0]);
		this.mainMenuBar.add(this.mainMenus[1]);
		this.mainMenuBar.add(this.mainMenus[2]);
		
		//We set up the dimensions that the mainMenuBar will have.
		this.mainMenuBar.setPreferredSize(new Dimension(720, 40));
	}
}