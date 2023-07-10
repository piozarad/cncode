package GCodeGroup;

import java.util.List;

public class GCodeGroup {
	
	protected int activeCode=-1;
	
	protected final int[] group;
	
	
	
	public boolean returnGroupNumber(int gCode)
	{
		
		for(int i=0; i<group.length;i++)
		{
			if(gCode==group[i])
				return true;
		}
		
		return false;
		
	}
	
	
	
	public GCodeGroup(final int[] group)
	{
		this.group=group;
		
	}
	
	
	
	 static boolean isPropperGroupNumber(int groupNumber)
		{
			return ((groupNumber>-1 && groupNumber<18 && groupNumber!=13 ) || groupNumber==19);
		}
		
	 public int getActiveCode()
	 {
	 	return activeCode;
	 }
	 
	 
	
	 public void updateGroup(List<Integer> gCodeArray) 
	 {
			for(int i: group)
			{
				if(gCodeArray.contains(i))
				{
					this.activeCode=i;
					checkConditions(i);
					break;
				}
			}
	
	 }
	 
	 
	 public void updateGroup(int gCode)
	 {
		 for(int i : group)
		 {
			 if(i==gCode)
				 this.activeCode=gCode;
		 }
	 }
	 
	private void checkConditions(int code)
	{
		if(code==9)
			this.group[0]=-1;
		
		
	}
	 
	 
	 
	 
}
