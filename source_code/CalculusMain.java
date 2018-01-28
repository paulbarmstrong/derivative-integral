import javax.swing.JOptionPane;

/**
	Author: Paul Armstrong
	Original Date: November 2015 (has since been updated)
	Description:
		This script will calculate derivatives and integrals of decimal
		number polynomial functions according to the power rule.
	
		There are plenty of online resources for computing derivatives
		and integrals. The reason why I wanted to make this script was
		to better understand how to apply the power rule. However, the
		vast majority of time spent making this went into converting
		String to Polynomial and vice versa
		(The Polynomial constructor and toString method)
**/

public class CalculusMain
{
	public static void main(String[] args)
	{
		// Prompt for an initial choice of what to do
		String[] options = {"Derive","Integrate","Exit"};
		int choice = JOptionPane.showOptionDialog(null,
				"What to do?","Calculus",0,3,null,options,null);
		
		while (choice != 2)
		{
			String input = JOptionPane.showInputDialog("Expression as a function of x: ");
			// Create a new polynomial based on the input
			Polynomial polynomial = new Polynomial(input);
			if (choice == 0)
			{
				// If chose to derive, display both the original polynomial and the derivative
				JOptionPane.showMessageDialog(null,
					"Derivative of "+polynomial+":\n\n"+polynomial.derive());
			} else {
				// If chose to integrate, display both the original polynomial and the integral
				JOptionPane.showMessageDialog(null,
					"Integral of "+polynomial+":\n\n"+polynomial.integrate()+" + C");
			}
			
			// Prompt for choice of what to do next
			choice = JOptionPane.showOptionDialog(null,
					"What to do?","Calculus",0,3,null,options,null);
		}
	}
}