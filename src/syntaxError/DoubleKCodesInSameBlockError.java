package syntaxError;

public class DoubleKCodesInSameBlockError implements SyntaxError {

	@Override
	public String getErrorMessage() {
		return "Dwa kody 'K' nie mog¹ siê znajdowaæ w tym samym bloku";
	}

	@Override
	public boolean checkError(String block) {
		if(block.contains("K"))
		{
			char[] sequence = block.toCharArray();
			int counter =0;
			for(char c: sequence)
			{
				if(c=='K')
					counter++;
				if(counter>1)
					return true;
			}
		}

		 return false;
	}

}
