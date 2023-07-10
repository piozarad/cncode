package syntaxError;

public class DoubleNCodeInSameBlockError implements SyntaxError {

	
	@Override
	public boolean checkError(String block) {
		
		if(block.contains("N"))
		{
			char[] sequence = block.toCharArray();
			int counter =0;
			for(char c: sequence)
			{
				if(c=='N')
					counter++;
				if(counter>1)
					return true;
			}
		}

		 return false;
	}

	@Override
	public String getErrorMessage() {
		return "Dwa numery blokow nie mog¹ wystêpowaæ w tym samym bloku";
	}

	
}
