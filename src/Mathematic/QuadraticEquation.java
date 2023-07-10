package Mathematic;

import java.util.ArrayList;
import java.util.List;

public class QuadraticEquation {

	private static QuadraticEquation instance;
	
	float a;
	float b;
	float c;
	
	float delta;

	List<Float> results;
	private QuadraticEquation()
	{
		results=new ArrayList<>();
	}
	
	public static QuadraticEquation getInstance()
	{
		if(instance==null)
		{
			instance = new QuadraticEquation();
		}
		
		return instance;
	}
	
	
	/**
	 * Redefines parameters in this equation
	 * @param a parameter a in ax^2 +bx +c
	 * @param b
	 * @param c
	 */
	public QuadraticEquation newEquation(float a, float b, float c)
	{
		this.a=a;
		this.b=b;
		this.c=c;
		this.results.getClass();
		this.delta=-1f;
		return this;
	}

	
	/**
	 * Solves this equation
	 * @return returns true if this equation has results
	 */
	public boolean solve()
	{
		this.results.clear();
		if(a==0 && b==0)
		{
			return false;
		}
		else if(a==0)
		{
			results.add((-c/b));
			
		}
		else if(c==0)
		{
			results.add(0f);
			results.add(-b/a);
		}
		else 
		{
			 this.delta = (float)(Math.pow(b,2) - 4* a*c);
			
			if(delta <0)	return false;
			else if(Math.abs(delta) < 0.001) results.add(-b/(2*a));
			else
			{
				results.add((float)((-b-Math.sqrt(delta))/(2*a)) );
				results.add((float)((-b+Math.sqrt(delta))/(2*a)) );	
			}
		}
		return true;
	}
	
	
	public List<Float> getResults()
	{
		return results;
		
	}
	


}
