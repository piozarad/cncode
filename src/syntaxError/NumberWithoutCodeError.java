package syntaxError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberWithoutCodeError implements SyntaxError {
	
	Pattern p = Pattern.compile(" \\d+");
	Matcher m;
	
	@Override
	public boolean checkError(String block) {
		m=p.matcher(block);
		
		return m.find();

	}

	@Override
	public String getErrorMessage() {
		return "Liczba bez kodu";
	}

}
