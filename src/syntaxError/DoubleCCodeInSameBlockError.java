package syntaxError;

public class DoubleCCodeInSameBlockError implements SyntaxError {

	//TODO dopisac metode ktora b�dzie wyrzuca�a komentarz z analizowanego bloku
	
	
	@Override
	public boolean checkError(String block) {
		
		if(block.contains("C"))
		{
			char[] sequence = block.toCharArray();
			int counter =0;
			for(char c: sequence)
			{
				if(c=='C')
					counter++;
				if(counter>1)
					return true;
			}
		}

		 return false;
	}

	@Override
	public String getErrorMessage() {
		return "Dwa C kody nie mog� wyst�powa� w tym samym bloku";
	}

	
}
