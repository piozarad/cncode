package controls;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import CordConverter.Options;
import CordConverter.Wind;
import instructionReadFromFile.Postprocesor;

public class ControlsIO {

	public void saveToFile()
	{
		
		try(ObjectOutputStream outStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Controls.txt"))))
				{
		
					//outStream.writeObject(Wind.controlList);
				}
		catch (IOException e)
		{
			Wind.log.writeErrorLog("IO exception while trying to save controls", e, this.getClass().getSimpleName());
		}

	}
	
	
	public static void loadFromFile() throws ClassNotFoundException
	{

		try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Controls.txt"))))
		{
			Wind.options=(Options)inputStream.readObject();
			
		}
		catch (IOException e)
		{	
			Wind.log.writeErrorLog("Error occured while atempting to read Controls file", e, "ControlsIO");
			//Wind.controlList=new ArrayList<ControlsModel>();
		}
	}
	
	
	
}
