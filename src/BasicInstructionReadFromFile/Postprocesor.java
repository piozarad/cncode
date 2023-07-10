package BasicInstructionReadFromFile;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import basicInstruction.Instruction;
import basicInstruction.InstructionFactory;



public class Postprocesor {


	private List<Instruction> instructionsDatabase;
	
	private String name;
	
	
	public Postprocesor()
	{
		this.instructionsDatabase=new ArrayList<>();
	}
	
	

	public String getName()
	{
		if(this.name!=null)
			return this.name;
		else return "Brak nazwy";
	}
	public void setName(String name)
	{
		this.name=name;
	}
	
	
	public void addInstruction(String line)
	{
		this.instructionsDatabase.add(InstructionFactory.createInstuction(line));	
	}
	
	public String getInstructionsAsString()
	{
		StringJoiner sj = new StringJoiner("\n");
		
		for(Instruction i:instructionsDatabase)
		{
			sj.add(i.toString());
		}
		return sj.toString();
		
	}
	
	public List<Instruction> getInstructions()
	{
		return this.instructionsDatabase;
	}
	
	
	
	@Override
	public String toString()
	{
		return this.name;
	}
	
}
