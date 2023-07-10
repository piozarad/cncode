package syntaxError;

public class DoubleBCodeInSameBlockError implements SyntaxError {

	//TODO dopisac metode ktora bêdzie wyrzuca³a komentarz z analizowanego bloku
	
	
	@Override
	public boolean checkError(String block) {
		
		if(block.contains("B"))
		{
			char[] sequence = block.toCharArray();
			int counter =0;
			for(char c: sequence)
			{
				if(c=='B')
					counter++;
				if(counter>1)
					return true;
			}
		}

		 return false;
	}

	@Override
	public String getErrorMessage() {
		return "Dwa 'B' kody nie mog¹ wystêpowaæ w tym samym bloku";
	}

	
}
