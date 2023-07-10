package syntaxError;

public class DoubleHCodeInSameBlockError implements SyntaxError {
	
	
	@Override
	public boolean checkError(String block) {
		
		if(block.contains("H"))
		{
			char[] sequence = block.toCharArray();
			int counter =0;
			for(char c: sequence)
			{
				if(c=='H')
					counter++;
				if(counter>1)
					return true;
			}
		}

		 return false;
	}

	@Override
	public String getErrorMessage() {
		return "Dwa kody H nie mog¹ wystêpowaæ w tym samym bloku";
	}

	
}
