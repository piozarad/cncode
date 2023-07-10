package syntaxError;

public class DoubleJCodeInSameBlockError implements SyntaxError {

	@Override
	public String getErrorMessage() {
		return "Dwa kody 'J' nie mog¹ siê znajdowaæ w tym samym bloku";
	}

	@Override
	public boolean checkError(String block) {
		if(block.contains("J"))
		{
			char[] sequence = block.toCharArray();
			int counter =0;
			for(char c: sequence)
			{
				if(c=='J')
					counter++;
				if(counter>1)
					return true;
			}
		}

		 return false;
	}

}
