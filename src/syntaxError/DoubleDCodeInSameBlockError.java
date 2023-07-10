package syntaxError;

public class DoubleDCodeInSameBlockError implements SyntaxError {

	
	
	@Override
	public boolean checkError(String block) {
		
		if(block.contains("D"))
		{
			char[] sequence = block.toCharArray();
			int counter =0;
			for(char c: sequence)
			{
				if(c=='D')
					counter++;
				if(counter>1)
					return true;
			}
		}

		 return false;
	}

	@Override
	public String getErrorMessage() {
		return "Dwa kody D nie mog¹ wystêpowaæ w tym samym bloku";
	}

}
