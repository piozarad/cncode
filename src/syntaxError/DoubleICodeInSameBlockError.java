package syntaxError;

public class DoubleICodeInSameBlockError implements SyntaxError {

	@Override
	public String getErrorMessage() {
		return "Dwa kody 'I' nie moga siê znajdowaæ w jednym bloku";
	}

	@Override
	public boolean checkError(String block) {
		if(block.contains("I"))
		{
			char[] sequence = block.toCharArray();
			int counter =0;
			for(char c: sequence)
			{
				if(c=='I')
					counter++;
				if(counter>1)
					return true;
			}
		}

		 return false;
	}

}
