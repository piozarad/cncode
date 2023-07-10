package syntaxError;

public class DoubleFCodeInSameBlockError implements SyntaxError {


	
	@Override
	public boolean checkError(String block) {
		
		if(block.contains("F"))
		{
			char[] sequence = block.toCharArray();
			int counter =0;
			for(char c: sequence)
			{
				if(c=='F')
					counter++;
				if(counter>1)
					return true;
			}
		}

		 return false;
	}

	@Override
	public String getErrorMessage() {
		return "Dwa kody F nie mog¹ wystêpowaæ w tym samym bloku";
	}

	
}
