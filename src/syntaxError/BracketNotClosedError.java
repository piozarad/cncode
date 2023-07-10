package syntaxError;

public class BracketNotClosedError implements SyntaxError {

	@Override
	public String getErrorMessage() {
		return "Niedomkniety nawias komentarza";
	}

	@Override
	public boolean checkError(String block) {
		return block.contains("(") && !block.contains(")") || !block.contains("(") && block.contains(")");
	}

}
