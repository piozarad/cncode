package GCodeGroup;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class ActiveGCodes {

	
	private GCodeGroup[] groups;
	
	
	
	public ActiveGCodes()
	{
		this.groups= new GCodeGroup[22];
		
		
		
		for(int i=0;i<groups.length;i++)
		{
			if(!GCodeGroup.isPropperGroupNumber(i))	
				continue;
			
			else
			{
				groups[i]= GroupFactory.gCodeGroupFactory(i);
			}
			
			
		}
	}
	
	
	
	public void updateGroup(List<Integer> gCodeArray)
	{
		for(GCodeGroup g: groups)
		{
			if(g!=null)
			{
				g.updateGroup(gCodeArray);
			}
		}
		
		
	}
	
	
	
	public List<Integer> getAll()
	{
		List<Integer> result = new LinkedList<>();
		
		for(GCodeGroup g:groups)
		{
			if(g!=null && g.activeCode!=-1)
				result.add(g.activeCode);
			
		}
		
		return result;
		
	}
	
	/**
	 * 
	 * @param groupId id of group
	 * @return returns int value of active g code in given group
	 * Groups are :
	 * 0 (not modal){4,5,7,8,9,10,11,27,28,29,30,37,39,45,46,47,48,60,65,92} <br>
	 * 1 {0,1,2,3,33}<br>
	 * 2 {17,18,19}<br>
	 * 3 {90,91}<br>
	 * 4 {22,23}<br>
	 * 5 {94,95}<br>
	 * 6 {20,21}<br>
	 * 7 {40,41,42}<br>
	 * 8 {43,44,49}<br>
	 * 9 {73,74,76,80,81,82,83,84,85,86,87,88,89}<br>
	 * 10 {98,99}<br>
	 * 11 {50,51}<br>
	 * 12 {66,67}<br>
	 * 14 {53,54,55,56,57,58,59}<br>
	 * 15 {61,62,63,64}<br>
	 * 16 {68,69}<br>
	 * 17 {15,16}<br>
	 * 19 {25,26}<br>
	 * 22 {50.1,51.1}<br>
	 * 
	
	 */
	public int getActiveGCodeInGroup(int groupId)
	{
		int result =-1;
		
		
		if(GCodeGroup.isPropperGroupNumber(groupId))
		{
			return this.groups[groupId].activeCode;
		}
		
		
		
		return result;
		
		
	}
	
	public int getGroupNumber(int code)
	{
		for(int i=0;i<22;i++)
		{
			if(!GCodeGroup.isPropperGroupNumber(i))	
				continue;
			
			if(groups[i].returnGroupNumber(code))
				return i;
		}
		
		return -1;
		
	}
	
	
	
	@Override
	public String toString()
	{
		StringJoiner joiner = new StringJoiner(" G", "Active GCodes:", ".");
		
		for(int i=0; i<=22; i++)
		{
			if( GCodeGroup.isPropperGroupNumber(i) && this.groups[i].activeCode!=-1 )
				joiner.add(Integer.toString(this.groups[i].activeCode));
		}
		
		
		return joiner.toString();
		
	}



	
	

	public void addNewCode(int i) {
		
		int group =getGroupNumber(i);
		
		if(group != -1)
			this.groups[group].updateGroup(i);
		
		fixGCodeCollisions(i);
	}

	/*
	 * Updates groups with given code
	 * ex. When applying Code G81 from group 9 group 1 is set to -1
	 */
	private void fixGCodeCollisions(int gCode) {
		
		if(getGroupNumber(gCode)==9)
			resetGroup(1);
		
	}



	/**
	 * 
	 * @param groupNumber id of group
	 * Groups are :
	 * 0 (not modal){4,5,7,8,9,10,11,27,28,29,30,37,39,45,46,47,48,60,65,92} <br>
	 * 1 {0,1,2,3,33}<br>
	 * 2 {17,18,19}<br>
	 * 3 {90,91}<br>
	 * 4 {22,23}<br>
	 * 5 {94,95}<br>
	 * 6 {20,21}<br>
	 * 7 {40,41,42}<br>
	 * 8 {43,44,49}<br>
	 * 9 {73,74,76,80,81,82,83,84,85,86,87,88,89}<br>
	 * 10 {98,99}<br>
	 * 11 {50,51}<br>
	 * 12 {66,67}<br>
	 * 14 {53,54,55,56,57,58,59}<br>
	 * 15 {61,62,63,64}<br>
	 * 16 {68,69}<br>
	 * 17 {15,16}<br>
	 * 19 {25,26}<br>
	 * 
	 * 
	 */
	public void resetGroup(int groupNumber) {
		if(GCodeGroup.isPropperGroupNumber(groupNumber))
			this.groups[groupNumber].activeCode=-1;
		
		
		
	}
	
	
	

	
	
}
