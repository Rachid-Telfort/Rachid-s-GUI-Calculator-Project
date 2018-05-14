package main;

import javax.swing.SwingUtilities;
import guiCalculator.GUICalculator;

public class Main 
{
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater
		(
			new Runnable()
			{
				@Override
				public void run() 
				{
					@SuppressWarnings("unused")
					GUICalculator guiCalculator=new GUICalculator();
				}
			}
		);
	}
}