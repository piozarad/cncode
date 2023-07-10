package syntaxError;

public class DoublePCodeInSameBlockError implements SyntaxError {

	
	@Override
	public boolean checkError(String block) {
		
		if(block.contains("P"))
		{
			char[] sequence = block.toCharArray();
			int counter =0;
			for(char c: sequence)
			{
				if(c=='P')
					counter++;
				if(counter>1)
					return true;
			}
		}

		 return false;
	}

	@Override
	public String getErrorMessage() {
		return "Dwa kody P nie mog¹ wystêpowaæ w tym samym bloku";
	}

	
}
