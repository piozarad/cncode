package GCodeGroup;

public class GroupFactory {

	
	private GroupFactory() {};
	
	
	
	
	public static GCodeGroup gCodeGroupFactory(int i)
	{
		if(GCodeGroup.isPropperGroupNumber(i))
		{
			switch (i)
			{
				case 0:
					return new Group0();
					
				case 1:
					return new Group1();
				case 2:
					return new Group2();
					
				case 3:
					return new Group3();	
				case 4:
					return new Group4();
					
				case 5:
					return new Group5();	
				case 6:
					return new Group6();
					
				case 7:
					return new Group7();
				case 8:
					return new Group8();
				case 9:
					return new Group9();
				case 10:
					return new Group10();
				case 11:
					return new Group11();
				case 12:
					return new Group12();
				case 14:
					return new Group14();
				case 15:
					return new Group15();
				case 16:
					return new Group16();
				case 17:
					return new Group17();
				case 19:
					return new Group19();
				case 22:
					return new Group22();
				
					
			}
			
			
		}
		
		
		return null;
		
	}
	
}
