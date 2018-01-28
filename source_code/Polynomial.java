import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
	Author: Paul Armstrong

	Description:
		The Calculus class "has" Polynomials
**/

public class Polynomial
{
	ArrayList<Term> terms;
	
	public Polynomial (String input)
	{
		terms = new ArrayList<Term>();
		
		input = removeSpaces(0,input);
		
		// If the first term is positive, add the implicit '+'
		if (input.charAt(0)!='-')
			input = "+"+input;
		
		// Iterate over and perform a thorough identification of
		// coefficient and exponent. One reason it is so long is
		// that it considers abnormal inputs like "x^2" and "100"
		for (int termIndex=0;termIndex < input.length()-1;termIndex++)
		{
			if ( input.charAt(termIndex)=='+' || input.charAt(termIndex)=='-')
			{
				float coef = 1;
				if (input.charAt(termIndex)=='-')
					coef = -1;
				termIndex++;
				termIndex += numSpaces(termIndex,input);
				
				// Iterate with coefIndex to identify the coefficient
				int coefIndex=termIndex;
				while (coefIndex < input.length() && (input.charAt(coefIndex) == '.'
						|| ('0' <= input.charAt(coefIndex) && input.charAt(coefIndex) <= '9')))
					coefIndex++;
				if (termIndex != coefIndex)
					coef *= Float.parseFloat(input.substring(termIndex,coefIndex));
				termIndex = coefIndex;
				
				float exp = 1;
				if (termIndex<input.length() && input.charAt(termIndex) == 'x')
				{
					if (termIndex<input.length()-2 && input.charAt(termIndex+1) == '^')
					{
						termIndex+=2;
						if (input.charAt(termIndex) == '-')
						{
							exp *= -1;
							termIndex++;
						}
						
						// Iterate with expIndex to identify the exponent
						int expIndex=termIndex;
						while (expIndex<input.length() && (input.charAt(expIndex) == '.'
								|| ('0' <= input.charAt(expIndex) && input.charAt(expIndex) <= '9')))
							expIndex++;
						exp *= Float.parseFloat(input.substring(termIndex,expIndex));
						termIndex=expIndex;
					}
				} else {
					exp = 0;
				}
				
				// Add it to the terms
				terms.add(new Term(coef,exp));
			}
		}
		
		// Simplify the polynomial
		simplify();
	}
	
	@Override
	public String toString()
	{
		String result = "";
		for (Term term : terms)
		{
			// Add spaced plus and minus signs
			if (term.coefficient < 0) {
				result += " - " + term.toString().substring(1);
			} else {
				result += " + " + term;
			}
		}
		
		// Get rid of explicit plus at the front
		if (result.startsWith(" + "))
			result = result.substring(3);
		if (result=="")
			return "0";
		else return result;
	}
	
	public Polynomial derive()
	{
		for (Term term : terms)
		{
			// As per the power rule: the coefficient is multiplied by
			// the exponent, then the exponent is decreased by one
			term.coefficient *= term.exponent;
			if (!approxEqual(term.exponent,0))
				term.exponent--;
		}
		
		// Ensure that the polynomial is still in simplified form
		simplify();
		return this;
	}
	
	public Polynomial integrate()
	{
		for (Term term : terms)
		{
			// As per the power rule: the exponent is increased by one,
			// then the coefficient is divided by the new exponent
			term.exponent++;
			term.coefficient /= term.exponent;
		}
		
		// Ensure that the polynomial is still in simplified form
		simplify();
		return this;
	}
	
	// This method will combine redundant terms, sort the terms, etc.
	private void simplify()
	{
		// Use a Set to keep track of what terms need to be deleted
		Set<Term> delete = new HashSet<Term>();
		for (int i=0;i<terms.size();i++)
		{
			for (int j=0;j<terms.size();j++)
			{
				// Combine same power terms
				if (i != j && !delete.contains(terms.get(j)) && !delete.contains(terms.get(j))
						&& approxEqual(terms.get(i).exponent,terms.get(j).exponent))
				{
					terms.get(j).coefficient += terms.get(i).coefficient;
					terms.get(i).coefficient = 0;
				}
				
				// Mark zero coefficient terms for deletion
				if (terms.get(i).coefficient == 0)
					delete.add(terms.get(i));
			}
		}
		// Delete all terms marked for deletion
		for (Term term : delete)
		{
			terms.remove(term);
		}
		
		// Lastly, sort the terms (higher power terms come first)
		Collections.sort(terms);
	}
	
	// Quick method to remove spaces from a string at a location
	private String removeSpaces(int index, String str)
	{
		while (str.charAt(index)==' ')
			str = str.substring(index+1);
		return str;
	}
	// Quick method to find the number of spaces at a location in a string
	private int numSpaces(int index, String str)
	{
		int count = 0;
		while (index+count<str.length() && str.charAt(index+count)==' ')
			count++;
		return count;
	}
	
	// Quick way to check floats are "equal"
	private boolean approxEqual(float a, float b)
	{
		return (Math.abs(a - b) < 0.00001);
	}
}
