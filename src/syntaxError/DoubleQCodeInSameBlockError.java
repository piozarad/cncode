package syntaxError;

public class DoubleQCodeInSameBlockError implements SyntaxError {

	//TODO dopisac metode ktora bêdzie wyrzuca³a komentarz z analizowanego bloku
	
	
	@Override
	public boolean checkError(String block) {
		
		if(block.contains("Q"))
		{
			char[] sequence = block.toCharArray();
			int counter =0;
			for(char c: sequence)
			{
				if(c=='Q')
					counter++;
				if(counter>1)
					return true;
			}
		}

		 return false;
	}

	@Override
	public String getErrorMessage() {
		return "Dwa kody Q nie mog¹ wystêpowaæ w tym samym bloku";
	}

	
}
