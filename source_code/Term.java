/**
	Author: Paul Armstrong

	Description:
		The Polynomial class "has" Terms
**/

public class Term implements Comparable<Term>
{
	public float coefficient;
	public float exponent;
	
	public Term (float coefficient, float exponent)
	{
		this.coefficient = coefficient;
		this.exponent = exponent;
	}
	
	@Override
	public int compareTo(Term other)
	{
		// Primarily the exponent will be used to determine comparison
		if (other.exponent - this.exponent > 0.00001)
		{
			return 1;
		} else if (other.exponent - this.exponent < -0.00001) {
			return -1;
		} else {
			// If the same, use the coefficients
			if (other.coefficient > this.coefficient)
				return 1;
			else return -1;
		}
	}
	
	@Override
	public String toString()
	{
		// If coefficient is 0 then the string version is just "0"
		if (approxEqual(coefficient,0))
			return "0";
		
		// Define the three parts of the term in explicit format
		String strCoef = Float.toString(coefficient);
		if (approxEqual(coefficient,Math.round(coefficient)))
			strCoef = Integer.toString(Math.round(coefficient));
		String strX = "x";
		String strExp = "^"+Float.toString(coefficient);
		if (approxEqual(exponent,Math.round(exponent)))
			strExp = "^"+Integer.toString(Math.round(exponent));
		
		// If the coefficient is 1 then it can appear empty in the string
		if (approxEqual(coefficient,1))
		{
			strCoef = "";
		}
		
		// If the exponent is 0, then the exponent and x can be taken away
		if (approxEqual(exponent,0))
		{
			strExp = "";
			strX = "";
		
		// If the exponent is 1, then just the exponent can be taken away
		} else if (approxEqual(exponent,1)) {
			strExp = "";
		}
		
		return (strCoef+strX+strExp);
	}
	
	// Quick way to check floats are "equal"
	private boolean approxEqual(float a, float b)
	{
		return (Math.abs(a - b) < 0.00001);
	}
}
