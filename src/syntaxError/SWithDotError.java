package syntaxError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SWithDotError implements SyntaxError {

	Pattern p = Pattern.compile(".*S\\d+\\.");
	Matcher m;
	
	@Override
	public boolean checkError(String block) {
		m=p.matcher(block);
		return m.find();
	}

	@Override
	public String getErrorMessage() {
		return "Obroty wrzeciona musz¹ byæ liczba ca³kowit¹";
	}

}
