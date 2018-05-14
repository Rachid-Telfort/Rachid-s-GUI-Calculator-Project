//This class is part of the guiCalculator package.
package guiCalculator;

//Needed to use BorderLayouts.
import java.awt.BorderLayout;

//Needed to use Colors.
import java.awt.Color;

//Needed to use Dimensions.
import java.awt.Dimension;

//Needed to use Fonts.
import java.awt.Font;

//Needed to use GridLayouts.
import java.awt.GridLayout;

//Needed to use ActionEvents.
import java.awt.event.ActionEvent;

//Needed to use ActionListeners.
import java.awt.event.ActionListener;

//Needed to use KeyEvents.
import java.awt.event.KeyEvent;

//Needed to use BigIntegers.
import java.math.BigInteger;

//Needed to use ArrayLists.
import java.util.ArrayList;

//Needed to use HashMaps.
import java.util.HashMap;

//Needed to use Stacks.
import java.util.Stack;

//Needed to use AbstractActions.
import javax.swing.AbstractAction;

//Needed to use JButtons.
import javax.swing.JButton;

//Needed to use JComponents.
import javax.swing.JComponent;

//Needed to use JPanels.
import javax.swing.JPanel;

//Needed to use JScrollPanes.
import javax.swing.JScrollPane;

//Needed to use JTextAreas.
import javax.swing.JTextArea;

//Needed to use KeyStrokes.
import javax.swing.KeyStroke;

//Needed to use BadLocationExceptions.
import javax.swing.text.BadLocationException;

//Needed to use CannotUndoExceptions.
import javax.swing.undo.CannotUndoException;

//Needed to use UndoManagers.
import javax.swing.undo.UndoManager;

//Needed to use Pairs.
import javafx.util.Pair;

//This class is a custom JPanel that will be used to visually 
//and programmatically calculate and solve binary expressions.
class BinaryPanel extends JPanel
{
	//This integer denotes the caret position in relation to the JTextArea that
	//this BinaryPanel contains. It will help us with manipulating the JTextArea
	//that this BinaryPanel contains.
	private int caretPosition;
	
	//This integer denotes the line offset in relation to the caret position of the
	//JTextArea that this BinaryPanel contains. It will help us with manipulating the
	//JTextArea that this BinaryPanel contains.
	private int lineOffset;
	
	//This integer denotes the start offset of the line that contains the line offset
	//in relation to the caret position in the JTextArea that this BinaryPanel contains.
	//It will help us with manipulating the JTextArea that this BinaryPanel contains.
	private int lineStartOffset;
	
	//This integer denotes the end offset of the line that contains the line offset
	//in relation to the caret position in the JTextArea that this BinaryPanel contains.
	//It will help us with manipulating the JTextArea that this BinaryPanel contains.
	private int lineEndOffset;
	
	//This is the serialVersionUID that is the tag in regards to identifying this JPanel.
	private static final long serialVersionUID=1L;
	
	//This ArrayList will be used in helping to map keystrokes to the 
	//binaryButtons on this binaryButtonsPanel so button shortcuts can be used.
	//This ArrayList consists of Pairs of integers where the pair key is the
	//keyboard key and the pair value is the keyboard modifier associated with the keyboard key.
	private ArrayList<Pair<Integer, Integer>> keyStrokePairs;
	
	//This BinaryExpressionCalculator will be responsible for parsing, calculating,
	//and solving the binary expressions, in String format, that the user wishes to solve.
	//Note: The BinaryExpressionCalculator implements the ExpressionCalculator interface.
	private BinaryExpressionCalculator binaryCalculator;
	
	//This JPanel will contain the binaryButtons that will be used with inputting valid
	//text in the JTextArea that this BinaryPanel contains.
	private JPanel binaryButtonsPanel;
	
	//This denotes the static JButton array that will contain the binaryButtons that will allow
	//the user to input valid text into the JTextArea that this BinaryPanel contains.
	private JButton[] binaryButtons;
	
	//This denotes the JScrollPane that will allow the user to scroll through 
	//past and present binary calculations.
	private JScrollPane binaryScrollPane;
	
	//This denotes the JTextArea that will visually contain all the past and present 
	//binary expressions typed, calculated, and/or solved by the BinaryExpressionCalculator
	//of this BinaryPanel.
	private JTextArea binaryTextArea;
	
	//This denotes the static String array that will contain the descriptions
	//that will briefly describe what each JButton does as well as their
	//activation shortcuts in the binaryButtons static array.
	private String[] binaryButtonDescriptions;
	
	//This denotes the UndoManager that will be responsible for undoing or redoing any
	//viable edits that the user typed in the binaryTextArea of this BinaryPanel.
	private UndoManager editManager;
	
	//This denotes the constructor for this BinaryPanel.
	//In the constructor all relevant variables are initialized
	//and set up for display and/or use.
	BinaryPanel()
	{
		//This constructs the super class, and consequently this BinaryPanel,
		//with a BorderLayout manager.
		//This is done in order to more accurately position the 
		//components in this BinaryPanel in terms of set regions like north,
		//south, east, west, etc.
		super(new BorderLayout());
		
		//We construct the binaryCalculator of this BinaryPanel.
		this.binaryCalculator=new BinaryExpressionCalculator();
		
		//We construct and set up the relevant variables for the 
		//binaryButtonsPanel for display and/or use.
		this.setBinaryButtonsPanel();
		
		//We construct and set up the relevant variables for the 
		//binaryTextArea for display and/or use.
		this.setBinaryTextArea();
		
		//We add the binaryButtonsPanel to the super class to the south
		//of the super class' layout manager.
		super.add(this.binaryButtonsPanel, BorderLayout.SOUTH);
		
		//We add the binaryScrollPane to the super class to the center
		//of the super class' layout manager.
		super.add(this.binaryScrollPane, BorderLayout.CENTER);
		
		//We set the super class' background color to white.
		//This is done for easier display to the user in case any component
		//of this BinaryPanel fails to be set up correctly.
		super.setBackground(Color.WHITE);
		
		//We set up the width and the height of the super class, and consequently this
		//BinaryPanel. We set up the width to be 720 pixels and the height to be 440 pixels.
		//The reason for these numbers is because I believe, 
		//that is a good size for such a panel.
		super.setSize(720, 440);
	}
	
	//This function clears all associated fields of this BinaryPanel.
	//This function will be used in helping the Java garbage collector in garbage collection.
	public void clear()
	{
		this.keyStrokePairs.clear();
		this.binaryCalculator.clear();
		
		this.keyStrokePairs=null;
		this.binaryCalculator=null;
		this.binaryButtonsPanel=null;
		this.binaryButtons=null;
		this.binaryScrollPane=null;
		this.binaryTextArea=null;
		this.binaryButtonDescriptions=null;
		this.editManager=null;
	}
	
	//This function does again an edit made to the JTextArea that
	//this BinaryPanel contains. This function is made in order
	//to do again an edit to the JTextArea that this BinaryPanel contains
	//from the menu item of the parent GUICalculator JFrame.
	public void redo()
	{
		//Here we check if the editManager of this BinaryPanel
		//can do again the edit to the JTextArea that this BinaryPanel contains. 
		//If it can, we do again the edit, otherwise no changes are made to
		//the JTextArea that this BinaryPanel contains.
		if(this.editManager.canRedo())
		{
			this.editManager.redo();
		}
	}
	
	//This function is an override of the default setFont function of a JPanel.
	//The override was made in order to make changing the fonts of the various
	//components of this BinaryPanel programmatically easier.
	//In this function the super class', the binaryButtons', and the binaryTextArea's
	//setFont functions are called.
	@Override
	public void setFont(Font newFont)
	{
		super.setFont(newFont);
		
		if(this.binaryButtons!=null)
		{
			for(JButton binaryButton:this.binaryButtons)
			{
				binaryButton.setFont(newFont);
			}
		}
		
		if(this.binaryTextArea!=null)
		{
			this.binaryTextArea.setFont(newFont);
		}
	}
	
	//This function undoes an edit made to the JTextArea that
	//this BinaryPanel contains. This function is made in order
	//to undo an edit to the JTextArea that this BinaryPanel contains
	//from the menu item of the parent GUICalculator JFrame.
	public void undo()
	{
		//Here we check if the editManager of this BinaryPanel
		//can undo the edit to the JTextArea that this BinaryPanel contains. 
		//If it can, we undo the edit, otherwise no changes are made to
		//the JTextArea that this BinaryPanel contains.
		try 
		{
			if(this.binaryTextArea.getCaretPosition()!=this.binaryTextArea.getLineStartOffset(this.binaryTextArea.getLineOfOffset(this.binaryTextArea.getCaretPosition()))&&this.editManager.canUndo())
			{
				this.editManager.undo();
			}
		} 
		
		catch (CannotUndoException e) 
		{
			e.printStackTrace();
		} 
		
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
	}
	
	//This function sets up the binaryButtonsPanel and associated components that will allow the user
	//to enter valid input into the binaryTextArea. In addition, this function will set up the 
	//binaryButtonsPanel and associated components for display and operation.
	private void setBinaryButtonsPanel()
	{
		//Here we set the keyStrokePairs ArrayList with a capacity of twenty one. 
		//The reason for twenty one as the capacity is because there will be a total of 
		//twenty one binaryButtons that the binaryButtonsPanel will contain. This ArrayList
		//will help us map the appropriate keyboard key combination to the 
		//appropriate binaryButton in the binaryButtons static array of this BinaryPanel.
		this.keyStrokePairs=new ArrayList<Pair<Integer, Integer>>(21);
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_9, KeyEvent.SHIFT_DOWN_MASK));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_0, KeyEvent.SHIFT_DOWN_MASK));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_0, 0));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_1, 0));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_EQUALS, KeyEvent.ALT_DOWN_MASK));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_MINUS, KeyEvent.ALT_DOWN_MASK));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_EQUALS, KeyEvent.SHIFT_DOWN_MASK));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_MINUS, 0));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_BACK_QUOTE, KeyEvent.SHIFT_DOWN_MASK));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_8, KeyEvent.SHIFT_DOWN_MASK));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_SLASH, 0));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_5, KeyEvent.SHIFT_DOWN_MASK));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_COMMA, KeyEvent.ALT_DOWN_MASK));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_PERIOD, KeyEvent.ALT_DOWN_MASK));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_7, KeyEvent.SHIFT_DOWN_MASK));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_6, KeyEvent.SHIFT_DOWN_MASK));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_BACK_SLASH, KeyEvent.SHIFT_DOWN_MASK));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_EQUALS, 0));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_BACK_SPACE, 0));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_BACK_SPACE, KeyEvent.CTRL_DOWN_MASK));
		this.keyStrokePairs.add(new Pair<Integer, Integer>(KeyEvent.VK_DELETE, 0));
		
		//Here we set up the binaryButtonDescriptions static string 
		//array. This static array will help us with explaining what 
		//each binaryButton in the binaryButtons static array
		//does and how to use them. This is done to enhance the user's
		//experience.
		this.binaryButtonDescriptions=new String[]
		{
			"Left parenthesis: Marks the beginning of a subexpression.",
			"Right parenthesis: Marks the end of a subexpression.",
			"Zero: First binary digit.",
			"One: Second binary digit.",
			"Prefix/Postfix increment: Increases a binary number by one; comes either before or after the binary number to be incremented.",
			"Prefix/Postfix decrement: Decreases a binary number by one; comes either before or after the binary number to be decremented.",
			"Unary/Binary plus: If in front or behind a binary number, gives its positive value. If in the middle of two binary numbers, gives their sum.",
			"Unary/Binary minus: If in front or behind a binary number, gives its negative value. If in the middle of two binary numbers, gives their difference.",
			"Bitwise NOT: Gives the bitwise NOT of a binary number; comes before the binary number to be bitwise NOTted.",
			"Multiplication: Gives the product of two binary numbers; comes in between the binary numbers to be multiplied.",
			"Division: Gives the quotient of two binary numbers; comes in between the binary numbers to be divided.",
			"Modulus: Gives the modulus, i.e. the remainder from dividing two binary numbers; comes in between the binary numbers to be modulused.",
			"Bitwise left shift: Gives the bitwise left shift of two binary numbers; comes in between the binary numbers to be bitwise left shifted.",
			"Bitwise right shift: Gives the bitwise right shift of two binary numbers; comes in between the binary numbers to be bitwise right shifted.",
			"Bitwise AND: Gives the bitwise AND of two binary numbers; comes in between the binary numbers to be bitwise ANDed.",
			"Bitwise XOR: Gives the bitwise XOR of two binary numbers; comes in between the binary numbers to be bitwise XORed.",
			"Bitwise OR: Gives the bitwise OR of two binary numbers; comes in between the binary numbers to be bitwise ORed.",
			"Equals: Evaluates the current expression row.",
			"Back: Undoes the last key pressed; moves the cursor back one key.",
			"Clear: Clears the current expression row.",
			"CA: Abbreviation for Clear All; clears the entire calculation area."
		};
		
		//Here we set up the binaryButtons static JButton array.
		//This static array will contain the binaryButtons that the user will
		//interact and perform calculations with. The user could interact
		//with the binaryButtons either by pressing them with the mouse or pressing
		//the corresponding keyboard key combination(s).
		this.binaryButtons=new JButton[]
		{
			new JButton("("),
			new JButton(")"),
			new JButton("0"),
			new JButton("1"),
			new JButton("++"),
			new JButton("--"),
			new JButton("+"),
			new JButton("-"),
			new JButton("~"),
			new JButton("*"),
			new JButton("/"),
			new JButton("%"),
			new JButton("<<"),
			new JButton(">>"),
			new JButton("&"),
			new JButton("^"),
			new JButton("|"),
			new JButton("="),
			new JButton("Back"),
			new JButton("Clear"),
			new JButton("CA")
		};
		
		//Here, we set up the binaryButtonsPanel, with the appropriate layout and dimensions,
		//which will house the binaryButtons in order for the binaryButtons to be displayed
		//and to be functional.
		this.binaryButtonsPanel=new JPanel(new GridLayout(3, 7));
		this.binaryButtonsPanel.setPreferredSize(new Dimension(720, 180));
		
		//Here, we set up the mechanics and visuals for the first seventeen binaryButtons
		//as these binaryButtons will mainly deal with calculations.
		int index=0;
		for(JButton binaryButton:this.binaryButtons)
		{
			if(index<17)
			{
				binaryButton.addActionListener
				(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent arg0) 
						{
							BinaryPanel.this.binaryTextArea.append(binaryButton.getText());
						}
					}
				);
			}
			
			binaryButton.setBackground(Color.GRAY);
			binaryButton.setFont(new Font(null, Font.PLAIN, 20));
			binaryButton.setPreferredSize(new Dimension(60, 60));
			binaryButton.setToolTipText(this.binaryButtonDescriptions[index]);
			binaryButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(this.keyStrokePairs.get(index).getKey(), this.keyStrokePairs.get(index).getValue()), binaryButton.getText());
			binaryButton.getActionMap().put
			(
				binaryButton.getText(),
				
				new AbstractAction()
				{
					private static final long serialVersionUID=1L;
					
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						binaryButton.doClick();
					}
				}
			);
			
			this.binaryButtonsPanel.add(binaryButton);
			
			++index;
		}
		
		//Here, we set up the mechanics and visuals for the eighteenth binaryButton.
		//Interacting with this binaryButton will get the results for the current calculation
		//and display it on the calculation area.
		this.binaryButtons[17].addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					BinaryPanel.this.caretPosition=BinaryPanel.this.binaryTextArea.getCaretPosition();
					
					try 
					{
						BinaryPanel.this.lineOffset=BinaryPanel.this.binaryTextArea.getLineOfOffset(BinaryPanel.this.caretPosition);
						BinaryPanel.this.lineStartOffset=BinaryPanel.this.binaryTextArea.getLineStartOffset(BinaryPanel.this.lineOffset);
						BinaryPanel.this.lineEndOffset=BinaryPanel.this.binaryTextArea.getLineEndOffset(BinaryPanel.this.lineOffset);
						BinaryPanel.this.binaryTextArea.append(" = "+BinaryPanel.this.binaryCalculator.evaluateExpression(BinaryPanel.this.binaryTextArea.getText(BinaryPanel.this.lineStartOffset, BinaryPanel.this.lineEndOffset-BinaryPanel.this.lineStartOffset)));
					} 
					
					catch (BadLocationException e) 
					{
						e.printStackTrace();
					}
					
					BinaryPanel.this.binaryTextArea.append("\n\n");
				}
			}
		);
		
		//We add the eighteenth binaryButton to the binaryButtonsPanel
		//so it can be displayed.
		this.binaryButtonsPanel.add(this.binaryButtons[17]);
		
		//Here, we set up the mechanics and visuals for the nineteenth binaryButton.
		//Interacting with this binaryButton will remove the last character typed in the current calculation.
		this.binaryButtons[18].addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					try 
					{
						if(BinaryPanel.this.binaryTextArea.getDocument().getLength()>0&&BinaryPanel.this.binaryTextArea.getCaretPosition()!=BinaryPanel.this.binaryTextArea.getLineStartOffset(BinaryPanel.this.binaryTextArea.getLineOfOffset(BinaryPanel.this.binaryTextArea.getCaretPosition())))
						{
							BinaryPanel.this.binaryTextArea.getDocument().remove(BinaryPanel.this.binaryTextArea.getDocument().getLength()-1, 1);
						}
					} 
					
					catch (BadLocationException e) 
					{
						e.printStackTrace();
					}
				}
			}
		);
		
		//We add the nineteenth binaryButton to the binaryButtonsPanel
		//so it can be displayed.
		this.binaryButtonsPanel.add(this.binaryButtons[18]);
		
		//Here, we set up the mechanics and visuals for the twentieth binaryButton.
		//Interacting with this binaryButton will clear the current calculation.
		this.binaryButtons[19].addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					if(BinaryPanel.this.binaryTextArea.getDocument().getLength()>0)
					{
						try 
						{
							BinaryPanel.this.binaryTextArea.replaceRange(null, BinaryPanel.this.binaryTextArea.getLineStartOffset(BinaryPanel.this.binaryTextArea.getLineOfOffset(BinaryPanel.this.binaryTextArea.getCaretPosition())), BinaryPanel.this.binaryTextArea.getLineEndOffset(BinaryPanel.this.binaryTextArea.getLineOfOffset(BinaryPanel.this.binaryTextArea.getCaretPosition())));
						} 
						
						catch (BadLocationException e) 
						{
							e.printStackTrace();
						}
					}
				}
			}
		);
		
		//We add the twentieth binaryButton to the binaryButtonsPanel
		//so it can be displayed.
		this.binaryButtonsPanel.add(this.binaryButtons[19]);
		
		//Here, we set up the mechanics and visuals for the twenty-first binaryButton.
		//Interacting with this binaryButton will clear all calculations, previous and present.
		this.binaryButtons[20].addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					BinaryPanel.this.binaryTextArea.setText(null);
				}
			}
		);
		
		//We add the twenty-first binaryButton to the binaryButtonsPanel
		//so it can be displayed.
		this.binaryButtonsPanel.add(this.binaryButtons[20]);
	}
	
	//This function sets up the binaryTextArea and associated components that will allow the user
	//to see their input and get visual feedback from the binaryTextArea. 
	private void setBinaryTextArea()
	{
		//We instantiate the binaryTextArea to get ready for display and provide
		//visual feedback to the user.
		this.binaryTextArea=new JTextArea();
		
		//We instantiate the editManager that will help with edit operations
		//made to the binaryTextArea.
		this.editManager=new UndoManager();
		
		//Then we set the amount of edits that the editManager can hold at a time
		//to an unlimited value.
		this.editManager.setLimit(-1);
		
		//We add the edit manager to the binaryTextArea so the binaryTextArea could
		//listen to edits and be able to undo or redo them.
		this.binaryTextArea.getDocument().addUndoableEditListener(this.editManager);
		
		//We set the input map of the binaryTextArea in order for the binaryTextArea to respond to
		//the redo edit keyboard key combination, i.e. Ctrl+Y.
		this.binaryTextArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK), "Redo");
		this.binaryTextArea.getActionMap().put
		(
			"Redo", 
			
			new AbstractAction()
			{
				private static final long serialVersionUID=1L;
				
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					if(BinaryPanel.this.editManager.canRedo())
					{
						BinaryPanel.this.editManager.redo();
					}
				}
			}
		);
		
		//We set the input map of the binaryTextArea in order for the binaryTextArea to respond to
		//the undo edit keyboard key combination, i.e. Ctrl+Z.
		this.binaryTextArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "Undo");
		this.binaryTextArea.getActionMap().put
		(
			"Undo", 
			
			new AbstractAction()
			{
				private static final long serialVersionUID=1L;
				
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					try 
					{
						if(BinaryPanel.this.binaryTextArea.getCaretPosition()!=BinaryPanel.this.binaryTextArea.getLineStartOffset(BinaryPanel.this.binaryTextArea.getLineOfOffset(BinaryPanel.this.binaryTextArea.getCaretPosition()))&&BinaryPanel.this.editManager.canUndo())
						{
							BinaryPanel.this.editManager.undo();
						}
					} 
					
					catch (CannotUndoException e) 
					{
						e.printStackTrace();
					} 
					
					catch (BadLocationException e) 
					{
						e.printStackTrace();
					}
				}
			}
		);
		
		//We set the input map of the binaryTextArea in order for the binaryTextArea to respond to
		//the enter key pressed as another keyboard shortcut for solving calculations.
		this.binaryTextArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Equals");
		this.binaryTextArea.getActionMap().put
		(
			"Equals",
			
			new AbstractAction()
			{
				private static final long serialVersionUID=1L;
				
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					BinaryPanel.this.binaryButtons[17].doClick();
				}
			}
		);
		
		//We set the input map of the binaryTextArea in order for the binaryTextArea to respond to
		//the space bar pressed as a keyboard shortcut for spacing out calculations.
		this.binaryTextArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Space");
		this.binaryTextArea.getActionMap().put
		(
			"Space",
			
			new AbstractAction()
			{
				private static final long serialVersionUID=1L;
				
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					BinaryPanel.this.binaryTextArea.append(" ");
				}
			}
		);
		
		//Here, we set up other parts of the binaryTextArea such as
		//making it editable, its font, line and word wrapping. This is done
		//to enhance the user's experience and to only allow valid inputs from the user.
		this.binaryTextArea.setEditable(false);
		this.binaryTextArea.setFont(new Font(null, Font.PLAIN, 20));
		this.binaryTextArea.setLineWrap(true);
		this.binaryTextArea.setWrapStyleWord(true);
		
		//We set up the binaryScrollPane and associated components that will allow the user to scroll through past calculations
		//like scrolling through a calculation history.
		this.binaryScrollPane=new JScrollPane(this.binaryTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.binaryScrollPane.setPreferredSize(new Dimension(720, 260));
	}
	
	//This class is a custom calculator class that will read, parse, analyze, and solve
	//expressions involving binary numbers. Furthermore, this class implements the 
	//ExpressionCalculator interface.
	class BinaryExpressionCalculator implements ExpressionCalculator
	{
		//This ArrayList will help in turning the binary prefix expression that the user types into the 
		//binaryPanel into postfix form so the expression can be correctly analyzed and evaluated
		//by the binaryCalculator.
		private ArrayList<String> binaryPostfixList;
		
		//This Stack will hold the temporary tokens from the binary expression when changing the 
		//binary expression from prefix to postfix so the expression can be correctly changed to postfix form.
		private Stack<String> binaryTokenStack;
		
		//This StringBuilder will help build the postfix form of the binary expression that
		//the user wishes to evaluate, binary token by binary token.
		private StringBuilder binaryPostfixBuilder;
		
		//This BinaryExpressionTree will help correctly evaluate the binary expression after the expression has
		//been changed to postfix form.
		private BinaryExpressionTree binaryExpressionTree;
		
		//This HashMap will map the corresponding binary operator token to its precedence value
		//from the highest precedence to the lowest precedence. This will help us put the binary tokens in correct
		//postfix order.
		private HashMap<String, Integer> binaryPrecedenceMap;
		
		//This is the constructor for the BinaryExpressionCalculator class.
		//It will help construct an instance of a binaryCalculator ready to evaluate
		//binary expressions.
		BinaryExpressionCalculator()
		{
			this.binaryPostfixList=new ArrayList<String>();
			this.binaryTokenStack=new Stack<String>();
			this.binaryPostfixBuilder=new StringBuilder();
			this.binaryExpressionTree=new BinaryExpressionTree();
			
			this.binaryPrecedenceMap=new HashMap<String, Integer>();
			this.binaryPrecedenceMap.put("++", 8);
			this.binaryPrecedenceMap.put("--", 8);
			this.binaryPrecedenceMap.put("~", 7);
			this.binaryPrecedenceMap.put("*", 6);
			this.binaryPrecedenceMap.put("/", 6);
			this.binaryPrecedenceMap.put("%", 6);
			this.binaryPrecedenceMap.put("+", 5);
			this.binaryPrecedenceMap.put("-", 5);
			this.binaryPrecedenceMap.put("<<", 4);
			this.binaryPrecedenceMap.put(">>", 4);
			this.binaryPrecedenceMap.put("&", 3);
			this.binaryPrecedenceMap.put("^", 2);
			this.binaryPrecedenceMap.put("|", 1);
			this.binaryPrecedenceMap.put("(", 0);
		}
		
		//This function clears and nullifies all the associated components
		//of the BinaryExpressionCalculator.
		void clear()
		{
			this.binaryPostfixList.clear();
			this.binaryTokenStack.clear();
			this.binaryPostfixBuilder.setLength(0);
			this.binaryExpressionTree.clear();
			this.binaryPrecedenceMap.clear();
			
			this.binaryPostfixList=null;
			this.binaryTokenStack=null;
			this.binaryPostfixBuilder=null;
			this.binaryExpressionTree=null;
			this.binaryPrecedenceMap=null;
		}
		
		//This function will take a binary infix expression, in String form, and 
		//turn it into its equivalent postfix form.
		@Override
		public ArrayList<String> infixToPostfix(String binaryInfixExpression) 
		{
			//First we clear all the variables that will be used in building the postfix
			//form of the binaryInfixExpression.
			this.binaryPostfixList.clear();
			this.binaryTokenStack.clear();
			this.binaryPostfixBuilder.setLength(0);
			
			//We iterate through the binaryInfixExpression, analyzing every character and
			//consider it for the final output of the binaryInfixExpression's postfix form.
			for(int index=0; index<binaryInfixExpression.length(); ++index)
			{
				//This case covers the instance a whitespace character is encountered.
				//In the case one is encountered, we simply ignore it and go to the next iteration of
				//the loop.
				if(binaryInfixExpression.charAt(index)==' ')
				{
					continue;
				}
				
				//If the current binary token is not a whitespace character, then we
				//add it to the binaryPostfixBuilder.
				binaryPostfixBuilder.append(binaryInfixExpression.charAt(index));
				
				//We then check if the current token is a plus symbol. 
				//If it is, we check if the next symbol is also a plus symbol.
				//If the next symbol is also a plus symbol, we also add it to the binaryPostfixBuilder.
				//This is because we get the prefix/postfix increment operator.
				if(binaryInfixExpression.charAt(index)=='+'&&index+1<binaryInfixExpression.length()&&binaryInfixExpression.charAt(index+1)=='+')
				{
					binaryPostfixBuilder.append(binaryInfixExpression.charAt(index+1));
					++index;
				}
				
				//If the previous case does not hold, we then check if the current token is a minus symbol. 
				//If it is, we check if the next symbol is also a minus symbol.
				//If the next symbol is also a minus symbol, we also add it to the binaryPostfixBuilder.
				//This is because we get the prefix/postfix decrement operator.
				else if(binaryInfixExpression.charAt(index)=='-'&&index+1<binaryInfixExpression.length()&&binaryInfixExpression.charAt(index+1)=='-')
				{
					binaryPostfixBuilder.append(binaryInfixExpression.charAt(index+1));
					++index;
				}
				
				//If the previous case does not hold, we then check if the current token is a less than symbol. 
				//If it is, we check if the next symbol is also a less than symbol.
				//If the next symbol is also a less than symbol, we also add it to the binaryPostfixBuilder.
				//This is because we get the bitwise left shift operator.
				else if(binaryInfixExpression.charAt(index)=='<'&&index+1<binaryInfixExpression.length()&&binaryInfixExpression.charAt(index+1)=='<')
				{
					binaryPostfixBuilder.append(binaryInfixExpression.charAt(index+1));
					++index;
				}
				
				//If the previous case does not hold, we then check if the current token is a greater than symbol. 
				//If it is, we check if the next symbol is also a greater than symbol.
				//If the next symbol is also a greater than symbol, we also add it to the binaryPostfixBuilder.
				//This is because we get the bitwise right shift operator.
				else if(binaryInfixExpression.charAt(index)=='>'&&index+1<binaryInfixExpression.length()&&binaryInfixExpression.charAt(index+1)=='>')
				{
					binaryPostfixBuilder.append(binaryInfixExpression.charAt(index+1));
					++index;
				}
				
				//If the previous case does not hold, we then check if the current token is an operand. 
				//If it is, then we add as much operand symbols to the binaryPostfixBuilder as there exists contiguously.
				else if(this.isOperand(binaryInfixExpression.charAt(index)))
				{
					while(index+1<binaryInfixExpression.length()&&this.isOperand(binaryInfixExpression.charAt(index+1)))
					{
						binaryPostfixBuilder.append(binaryInfixExpression.charAt(index+1));
						++index;
					}
					
					this.binaryPostfixList.add(binaryPostfixBuilder.toString());
					this.binaryPostfixBuilder.setLength(0);
					
					continue;
				}
				
				//If the token has not been read yet, then we check if the current token is a left parenthesis. 
				//If it is, then we add it to the binaryTokenStack.
				if(this.isLeftParenthesis(this.binaryPostfixBuilder.charAt(0)))
				{
					this.binaryTokenStack.push(this.binaryPostfixBuilder.toString());
					this.binaryPostfixBuilder.setLength(0);
				}
				
				//If the previous case does not hold, then we check if the current token is a right parenthesis. 
				//If it is, then we remove all symbols from the binaryTokenStack and add the removed symbols
				//to the binaryPostfixList until a left parenthesis is found or the binaryTokenStack is empty.
				else if(this.isRightParenthesis(this.binaryPostfixBuilder.charAt(0)))
				{
					while(!this.binaryTokenStack.isEmpty()&&!this.isLeftParenthesis(this.binaryTokenStack.peek().charAt(0)))
					{
						this.binaryPostfixList.add(this.binaryTokenStack.pop());
					}
					
					if(!this.binaryTokenStack.isEmpty())
					{
						this.binaryTokenStack.pop();
					}
					
					this.binaryPostfixBuilder.setLength(0);
				}
				
				//If the previous case does not hold, then we check if the current token is an operator. 
				//If it is, then we remove all operators that have a higher or equal precedence from the binaryTokenStack and add the removed operators
				//to the binaryPostfixList until an operator with a lower precedence is found or the binaryTokenStack is empty.
				//Then we add the current token to the binaryTokenStack.
				else if(this.isOperator(this.binaryPostfixBuilder.toString()))
				{
					while(!this.binaryTokenStack.isEmpty()&&this.binaryPrecedenceMap.get(this.binaryTokenStack.peek())>=this.binaryPrecedenceMap.get(this.binaryPostfixBuilder.toString()))
					{
						this.binaryPostfixList.add(this.binaryTokenStack.pop());
					}
					
					this.binaryTokenStack.push(this.binaryPostfixBuilder.toString());
					this.binaryPostfixBuilder.setLength(0);
				}
			}
			
			//After going through the binaryInfixExpression we push all the remaining tokens from the binaryTokenStack
			//to the binaryPostfixList.
			while(!this.binaryTokenStack.isEmpty())
			{
				this.binaryPostfixList.add(this.binaryTokenStack.pop());
			}
			
			//We return the binaryPostfixList which will contain
			//the binaryInfixExpression in postfix form.
			return this.binaryPostfixList;
		}
		
		//This function checks whether a character is a binary operand and 
		//returns true if it is, false otherwise. The binary operands are 0 and 1.
		@Override
		public boolean isOperand(char testCharacter)
		{
			return testCharacter=='0'||testCharacter=='1';
		}
		
		//This function checks whether a String is a binary operator and 
		//returns true if it is, false otherwise.
		@Override
		public boolean isOperator(String testString) 
		{
			return testString.equals("++")
			||testString.equals("--")
			||testString.equals("~")
			||testString.equals("*")
			||testString.equals("/")
			||testString.equals("%")
			||testString.equals("+")
			||testString.equals("-")
			||testString.equals("<<")
			||testString.equals(">>")
			||testString.equals("&")
			||testString.equals("^")
			||testString.equals("|")
			||testString.equals("(");
		}
		
		//This function checks whether a character is a left parenthesis and 
		//returns true if it is, false otherwise.
		@Override
		public boolean isLeftParenthesis(char testCharacter) 
		{
			return testCharacter=='(';
		}
		
		//This function checks whether a character is a right parenthesis and 
		//returns true if it is, false otherwise.
		@Override
		public boolean isRightParenthesis(char testCharacter) 
		{
			return testCharacter==')';
		}
		
		//This function takes a binary expression in string infix form, and returns 
		//its evaluated result in string infix form.
		@Override
		public String evaluateExpression(String binaryExpression) 
		{
			this.binaryExpressionTree.clear();
			
			this.binaryExpressionTree.build(this.infixToPostfix(binaryExpression));
			
			return this.binaryExpressionTree.evaluate(this.binaryExpressionTree.root);
		}
		
		//This class is a custom expression tree that will be used to store symbols from
		//binary expressions and evaluate them.
		class BinaryExpressionTree
		{
			BinaryExpressionTreeNode root;
			Stack<BinaryExpressionTreeNode> binaryExpressionTreeNodeStack;
			
			//This is the constructor for the Binary Expression Tree class.
			//This instantiates a binary expression tree ready to collect binary tokens from
			//expressions and solve them.
			BinaryExpressionTree() 
			{
				this.root=null;
				this.binaryExpressionTreeNodeStack=new Stack<BinaryExpressionTreeNode>();
			}
			
			//This function builds the nodes of the binary expression tree from
			//a binary expression in postfix form.
			void build(ArrayList<String> binaryPostfixList)
			{
				//We go through the binary tokens from the postfix binary expression
				//and build the binary expression tree appropriately.
				for(String binaryPostfixToken:binaryPostfixList)
				{
					//We check if the current binaryPostfixToken is an operator. If it is,
					//then we pop the last two inserted BinaryExpressionTreeNodes from the binaryExpressionTreeNodeStack and create a new BinaryExpressionTreeNode
					//with the operator as the root of the two BinaryExpressionTreeNodes we previously popped from the
					//binaryExpressionTreeNodeStack.
					if(BinaryExpressionCalculator.this.isOperator(binaryPostfixToken))
					{
						this.root=new BinaryExpressionTreeNode(binaryPostfixToken);
						
						//This is the case where we encounter a unary operand. If we do encounter a unary operand, we push only the last
						//inserted BinaryExpressionTreeNode from the binaryExpressionTreeNodeStack.
						if((binaryPostfixToken.equals("++")||binaryPostfixToken.equals("--")||binaryPostfixToken.equals("~"))&&!this.binaryExpressionTreeNodeStack.isEmpty())
						{
							this.root.rightChild=this.binaryExpressionTreeNodeStack.pop();
						}
						
						else 
						{
							if(!this.binaryExpressionTreeNodeStack.isEmpty())
							{
								this.root.rightChild=this.binaryExpressionTreeNodeStack.pop();
							}
							
							if(!this.binaryExpressionTreeNodeStack.isEmpty())
							{
								this.root.leftChild=this.binaryExpressionTreeNodeStack.pop();
							}
						}
						
						this.binaryExpressionTreeNodeStack.push(this.root);
					}
					
					else
					{
						this.binaryExpressionTreeNodeStack.push(new BinaryExpressionTreeNode(binaryPostfixToken));
					}
				}
				
				//After we go through the binaryPostfixTokens, there will be one more
				//BinaryExpressionTreeNode left on the binaryExpressionTreeNodeStack.
				//This BinaryExpressionTreeNode will become the root of the final BinaryExpressionTree.
				if(!this.binaryExpressionTreeNodeStack.isEmpty())
				{
					this.root=this.binaryExpressionTreeNodeStack.pop();
				}
			}
			
			//This function takes one or two binary operand tokens and an operator token, all in String form, and
			//returns the result of the operator acting on the operand(s).
			String operate(String firstOperand, String secondOperand, String operator)
			{
				if(secondOperand!=null&&operator!=null&&operator.equals("++"))
				{
					return (new BigInteger(secondOperand, 2).add(new BigInteger("1", 2))).toString(2);
				}
				
				else if(secondOperand!=null&&operator!=null&&operator.equals("--"))
				{
					return (new BigInteger(secondOperand, 2).subtract(new BigInteger("1", 2))).toString(2);
				}
				
				else if(operator!=null&&operator.equals("+"))
				{
					if(firstOperand==null&&secondOperand!=null)
					{
						return new BigInteger(secondOperand, 2).toString(2);
					}
					
					else if(firstOperand!=null&&secondOperand!=null)
					{
						return (new BigInteger(firstOperand, 2).add(new BigInteger(secondOperand, 2))).toString(2);
					}
				}
				
				else if(operator!=null&&operator.equals("-"))
				{
					if(firstOperand==null&&secondOperand!=null)
					{
						return new BigInteger(secondOperand, 2).negate().toString(2);
					}
					
					else if(firstOperand!=null&&secondOperand!=null)
					{
						return (new BigInteger(firstOperand, 2).subtract(new BigInteger(secondOperand, 2))).toString(2);
					}
				}
				
				else if(secondOperand!=null&&operator!=null&&operator.equals("~"))
				{
					return new BigInteger(secondOperand, 2).not().toString(2);
				}
				
				else if(firstOperand!=null&&secondOperand!=null&&operator!=null&&operator.equals("*"))
				{
					return (new BigInteger(firstOperand, 2).multiply(new BigInteger(secondOperand, 2))).toString(2);
				}
				
				else if(firstOperand!=null&&secondOperand!=null&&operator!=null&&operator.equals("/"))
				{
					try
					{
						return (new BigInteger(firstOperand, 2).divide(new BigInteger(secondOperand, 2))).toString(2);
					}
					
					catch (ArithmeticException e)
					{
						e.printStackTrace();
						
						return null;
					}
				}
				
				else if(firstOperand!=null&&secondOperand!=null&&operator!=null&&operator.equals("%"))
				{
					try
					{
						return (new BigInteger(firstOperand, 2).remainder(new BigInteger(secondOperand, 2))).toString(2);
					}
					
					catch (ArithmeticException e)
					{
						e.printStackTrace();
						
						return null;
					}
				}
				
				else if(firstOperand!=null&&secondOperand!=null&&operator!=null&&operator.equals("<<"))
				{
					return (new BigInteger(firstOperand, 2).shiftLeft(new BigInteger(secondOperand, 2).intValueExact())).toString(2);
				}
				
				else if(firstOperand!=null&&secondOperand!=null&&operator!=null&&operator.equals(">>"))
				{
					return (new BigInteger(firstOperand, 2).shiftRight(new BigInteger(secondOperand, 2).intValueExact())).toString(2);
				}
				
				else if(firstOperand!=null&&secondOperand!=null&&operator!=null&&operator.equals("&"))
				{
					return (new BigInteger(firstOperand, 2).and(new BigInteger(secondOperand, 2))).toString(2);
				}
				
				else if(firstOperand!=null&&secondOperand!=null&&operator!=null&&operator.equals("^"))
				{
					return (new BigInteger(firstOperand, 2).xor(new BigInteger(secondOperand, 2))).toString(2);
				}
				
				else if(firstOperand!=null&&secondOperand!=null&&operator!=null&&operator.equals("|"))
				{
					return (new BigInteger(firstOperand, 2).or(new BigInteger(secondOperand, 2))).toString(2);
				}
				
				return null;
			}
			
			//This function traverses the BinaryExpressionTree and evaluates the subtrees recursively.
			//The final result will be the result of the entire postfix expression
			//that the BinaryExpressionTree is built from.
			String evaluate(BinaryExpressionTreeNode root)
			{
				if(root!=null)
				{
					if(BinaryExpressionCalculator.this.isOperator(root.binaryToken))
					{
						return this.operate(this.evaluate(root.leftChild), this.evaluate(root.rightChild), root.binaryToken);
					}
					
					else
					{
						return root.binaryToken;
					}
				}
				
				return null;
			}
			
			//This function is a helper function to the clear() function. This function
			//traverses the whole BinaryExpressionTree and removes the BinaryExpressionTreeNodes
			//from the bottom-up recursively. After this function is done, the BinaryExpressionTree will be empty.
			private void unbuild(BinaryExpressionTreeNode root)
			{
				if(root!=null)
				{
					this.unbuild(root.leftChild);
					this.unbuild(root.rightChild);
					
					root.binaryToken=null;
					root=null;
				}
			}
			
			//This function removes all the BinaryExpressionTreeNodes from the BinaryExpressionTree
			//in order for the BinaryExpressionTree to be ready to be built from and evaluate a new
			//binary expression. This function also clears all other associated components.
			void clear()
			{
				this.unbuild(this.root);
				
				if(this.root!=null)
				{
					if(this.root.binaryToken!=null)
					{
						this.root.binaryToken=null;
					}
					
					this.root=null;
				}
				
				this.binaryExpressionTreeNodeStack.clear();
			}
			
			//This class denotes a BinaryExpressionTreeNode that will act as
			//the nodes that a BinaryExpressionTree will consist of.
			class BinaryExpressionTreeNode
			{
				//This String will hold the binary token that was originally
				//a part of the postfix expression passed to the BinaryExpressionTree to
				//evaluate.
				String binaryToken;
				
				//This BinaryExpressionTreeNode will contain the first operand of
				//the sub expression if this BinaryExpressionTreeNode contains a binary
				//operator token.
				BinaryExpressionTreeNode leftChild;
				
				//This BinaryExpressionTreeNode will contain the second operand of
				//the sub expression if this BinaryExpressionTreeNode contains a binary
				//operator token.
				BinaryExpressionTreeNode rightChild;
				
				//This is the constructor that will assign a binaryToken reference
				//to the binaryToken reference that the current BinaryExpressionTreeNode contains.
				BinaryExpressionTreeNode(String binaryToken)
				{
					this.binaryToken=binaryToken;
				}
			}
		}
	}
}