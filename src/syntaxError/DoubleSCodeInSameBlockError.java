package syntaxError;

public class DoubleSCodeInSameBlockError implements SyntaxError {


	
	
	@Override
	public boolean checkError(String block) {
		
		if(block.contains("S"))
		{
			char[] sequence = block.toCharArray();
			int counter =0;
			for(char c: sequence)
			{
				if(c=='S')
					counter++;
				if(counter>1)
					return true;
			}
		}

		 return false;
	}

	@Override
	public String getErrorMessage() {
		return "Wi�cej ni� jeden kod S w jednym bloku nie mog� wyst�powa� w tym samym bloku";
	}

	
}
