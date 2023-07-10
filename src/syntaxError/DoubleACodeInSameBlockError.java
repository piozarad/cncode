package syntaxError;

public class DoubleACodeInSameBlockError implements SyntaxError {

	
	
	@Override
	public boolean checkError(String block) {
		
		if(block.contains("A"))
		{
			char[] sequence = block.toCharArray();
			int counter =0;
			for(char c: sequence)
			{
				if(c=='A')
					counter++;
				if(counter>1)
					return true;
			}
		}

		 return false;
	}

	@Override
	public String getErrorMessage() {
		return "Dwa 'A' kody nie mog¹ wystêpowaæ w tym samym bloku";
	}


	
}
