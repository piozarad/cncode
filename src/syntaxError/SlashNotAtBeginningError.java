package syntaxError;

public class SlashNotAtBeginningError implements SyntaxError {

	@Override
	public boolean checkError(String block) {
		
		
		if(block.contains("/") )
		{
			int i=0;
			while(i<block.length() && block.charAt(i)==' ')
				i++;
			if(block.charAt(i)!='/')
				return true;
			else return false;
		}
				
	
		else return false;
	}

	@Override
	public String getErrorMessage() {
		return "Znak '/' funkcji block skip powinien znajdowaæ siê na pocz¹tku bloku";
	}

}
