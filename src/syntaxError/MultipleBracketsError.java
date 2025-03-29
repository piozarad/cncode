package syntaxError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CordConverter.FunctionUtilities;

public class MultipleBracketsError implements SyntaxError {

	@Override
	public String getErrorMessage() {
		return "Zagnie¿d¿ony komentarz (komentarz w komentarzu)";
	}

	@Override
	public boolean checkError(String block) {
		if(FunctionUtilities.hasComment(block))
		{
			Pattern p = Pattern.compile(".*\\(.*\\(.*\\).*\\).*");
			Matcher m = p.matcher(block);
			
			return (m.matches());
		}
		else return false;
	}

}
