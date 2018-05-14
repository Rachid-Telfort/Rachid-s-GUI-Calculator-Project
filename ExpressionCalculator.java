//This interface is part of the guiCalculator package.
package guiCalculator;

//Needed to use ArrayLists.
import java.util.ArrayList;

//This interface parses and evaluates an expression typed on the GUI Calculator
//when the user invokes an event to signal that they want
//the typed expression to be evaluated.
interface ExpressionCalculator 
{
	//This function takes an infix expression in String form
	//and returns an ArrayList of tokens of the infix expression
	//in postfix form.
	//Note: if the infix expression has no tokens to parse, this function returns
	//an empty ArrayList.
	ArrayList<String> infixToPostfix(String infixExpression);
	
	//This function takes a test character to check if 
	//it is a valid operand based on the interface implementation.
	//This function returns true if the test character is an operand, false otherwise.
	boolean isOperand(char testCharacter);
	
	//This function takes a test character to check if 
	//it is a left parenthesis based on the interface implementation.
	//This function returns true if the test character is a left parenthesis, false otherwise.
	boolean isLeftParenthesis(char testCharacter);
	
	//This function takes a test String to check if 
	//it is a valid operator based on the interface implementation.
	//This function returns true if the test String is an operator, false otherwise.
	boolean isOperator(String testString);
	
	//This function takes a test character to check if 
	//it is a right parenthesis based on the interface implementation.
	//This function returns true if the test character is a right parenthesis, false otherwise.
	boolean isRightParenthesis(char testCharacter);
	
	//This function takes an expression, in String form and assumed to be in infix form,
	//and returns its result in String form based on the interface implementation.
	//Note: if the expression cannot be solved, has no tokens, or arithmetic overflow occurs, 
	//this function returns null.
	String evaluateExpression(String expression);
}