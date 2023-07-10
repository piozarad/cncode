package NcStateErrors;

import java.util.Optional;

import CordConverter.NcState;
import CordConverter.Wind;

public class BadCircleEndPointError implements BadNcState {

	@Override
	public boolean findError(NcState state) {
		
	
		if(state.getiJk()[0]==null || state.getiJk()[1]==null)
			return false;
		
		
		if((state.getActiveGCodes().getActiveGCodeInGroup(1) ==2 || state.getActiveGCodes().getActiveGCodeInGroup(1) ==3) && state.getActiveGCodes().getActiveGCodeInGroup(2)==17)
		{	
			try {
				
				if(Math.abs(Math.pow((state.getDestinationPoint().getX() -  (state.getActualPoint().getX()+state.getiJk()[0] )), 2) 
						+   Math.pow((state.getDestinationPoint().getY() - (state.getActualPoint().getY()+state.getiJk()[1]) ), 2)
						-((state.getiJk()[0]*state.getiJk()[0])+(state.getiJk()[1]*state.getiJk()[1])))>Optional.of(Wind.options.getCircleTolerance()).orElse(0.05f))
					{		
						return true;
					}
				}
			
				catch(NullPointerException e)
				{
					return false;
				}
	
		}
		return false;
			
			
		
	}

	@Override
	public String getErrorMessage() {
		return "Nieprawid�owy punkt ko�cowy okr�gu";
	}

	@Override
	public String getExtendedMessage() {
		return "Podany punkt ko�cowy mija si� z okr�giem opisanym przez poprzedni punkt i wektor wyznaczaj�cy �rodek okr�gu IJK.<br> Upewnij si�, �e punkt w tym i poprzednim bloku jest podany poprawnie oraz nie ma b��du warto�ci wektor�w I J K";
	}

}
