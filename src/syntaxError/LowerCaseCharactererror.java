package syntaxError;

public class LowerCaseCharactererror implements SyntaxError {

	@Override
	public String getErrorMessage() {
		return "U¿yto ma³ej litery";
	}

	@Override
	public boolean checkError(String block) {
		return block.chars()
				.anyMatch(Character::isLowerCase);
	}

}
