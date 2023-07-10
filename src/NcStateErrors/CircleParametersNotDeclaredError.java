package NcStateErrors;

import CordConverter.NcState;

public class CircleParametersNotDeclaredError implements BadNcState {

	@Override
	public boolean findError(NcState state) {
		if(state.getActiveGCodes().getActiveGCodeInGroup(1)==2 || state.getActiveGCodes().getActiveGCodeInGroup(1)==3)	
		{
			if(state.getActiveGCodes().getActiveGCodeInGroup(2)==18)
				return state.getiJk()[0]==null || state.getiJk()[2]==null;
			
			else if(state.getActiveGCodes().getActiveGCodeInGroup(2)==19)
				return state.getiJk()[1]==null || state.getiJk()[2]==null;
			
			else return state.getiJk()[0]==null || state.getiJk()[1]==null;
			
			
		}
		return false;
	}

	@Override
	public String getErrorMessage() {
		return "Nie zadeklarowano wszystkich parametrów interpolacji ko³owej";
	}

	@Override
	public String getExtendedMessage() {
		return "W przypadku interpolacji w plaszczyznie G17 wymagany jest wektor I J<br>"
				+ "W przypadku interpolacji w plaszczyznie G18 wymagany jest wektor I K<br>"
				+"W przypadku interpolacji w plaszczyznie G19 wymagany jest wektor J K<br>"
				+ "Nie podanie któregokolwiek z wymaganych sk³adowych powoduje b³¹d";
	}

}
