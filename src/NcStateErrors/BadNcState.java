package NcStateErrors;

import CordConverter.NcState;

public interface BadNcState extends CordConverter.Error {
		
	public boolean findError(NcState state);
	
	public String getExtendedMessage();

}
