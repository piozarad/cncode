package instruction;

public enum Marks {

	ADD
	{
		@Override
		public String toString()
		{
			return "+";
		}
	},
	ADD_TO_NEXT_LINE
	{
		
		@Override
		public String toString()
		{
			return "v+";
		}
	},
	
	ADD_TO_UPPER_LINE
	{
		
		@Override
		public String toString()
		{
			return "^+";
		}
	},
	DELETE
	{
		@Override
		public String toString()
		{
			return "/";
		}
	},
	
	CHANGE_TO
	{
		
		@Override
		public String toString()
		{
			return "=>";
		}
	},
	
	ADD_UNTIL
	{
		
		@Override
		public String toString()
		{
			return "vv";
		}
	},
	LIMIT
	{
		@Override
		public String toString()
		{
			return "<MAX>";
		}
	};


}
