package syntaxError;

public class DoubleMCodeError implements SyntaxError{

	@Override
	public boolean checkError(String block) {
		if(block.contains("M"))
		{
			char[] sequence = block.toCharArray();
			int counter =0;
			for(char c: sequence)
			{
				if(c=='M')
					counter++;
				if(counter>1)
					return true;
			}
		}

		 return false;
	}

	@Override
	public String getErrorMessage() {
		return "Wiêcej ni¿ jeden kod maszynowy nie mo¿e znajdowaæ siê w jednym bloku";
	}

}
