package CordConverter;

public enum ControlTypes {

	
	SINUMERIC
	{
		
		@Override
		public String toString()
		{
			return "Sinumeric";
		}
	},
	FANUC
	{
		@Override
		public String toString()
		{
			return "Fanuc";
		}
	},
	OKUMA
	{
		@Override
		public String toString()
		{
			return "Okuma";
		}
	},
	
	HITACHI
	{
	@Override
	public String toString()
	{
		return "Hitachi";
	}
	
}

	
}
