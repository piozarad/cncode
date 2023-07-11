package ControlsConversion;

import java.util.List;

import instruction.Instruction;

public interface Conversion {
	
	public String convert(List<String> list , int currentIndex, Instruction instruction);

}
