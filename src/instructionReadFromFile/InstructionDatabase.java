package instructionReadFromFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.runners.ParentRunner;

import CordConverter.Edytor;
import CordConverter.Wind;
import instruction.Add;
import instruction.AddToNextLine;
import instruction.AddToUpperLine;
import instruction.ChangeTo;
import instruction.ChangeWhen;
import instruction.Delete;
import instruction.DeleteLine;
import instruction.DeleteWhen;
import instruction.Instruction;
import instruction.InstructionFactory;
import instruction.Marks;




//acts as Model in MVC
public class InstructionDatabase {

	private List<File> files;
	private List<Postprocesor> basicInstructionsDatabase;
	
	
	public InstructionDatabase()
	{
		this.files=new ArrayList<>();
		this.basicInstructionsDatabase = new ArrayList<>();
		
	}
	
	public void addFile(File f)
	{
		this.files.add(f);
	}
	
	public List<File> getFileList()
	{
		return this.files;
	}

	public void addPostProcesor(Postprocesor postprocesor)
	{
		this.basicInstructionsDatabase.add(postprocesor);
	}

	/*
	 * @returns List of instructions loaded from file
	 */
	public List<Postprocesor> getPostprocesorList()
	{
		return this.basicInstructionsDatabase;
	}
}
