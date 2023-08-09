package instructionReadFromFile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;

import CordConverter.Edytor;
import CordConverter.FunctionAnalyzeUtilities;
import CordConverter.Wind;
import instruction.Instruction;


//acts as Controller in MVC
public class ReadFromFile {

	
	static final String INSTRUCTIONS_PATH = "postprocesory";
	
	private final InstructionDatabase model;
	private final ReadInstructionsGui view;
	private final Edytor parent;
	private static final String INSTRUCTION_PATH = "postprocesory\\Instrukcje do postprocesor�w.txt";

	
	public ReadFromFile(ReadInstructionsGui view,Edytor parent)
	{
		this.parent=parent;
		this.model=new InstructionDatabase();
		this.view=view;
		
		searchForFiles();
		updatePostprocesorModelList();
		view.updateComboBox(this.model.getPostprocesorList());
		view.addInstructionListListener(new InstructionListListener());
		view.addExecuteButtonListener(new ExecuteButtonListener());
		view.updateInstructionList(view.getComboBoxSelectedItem().getInstructionsAsString());
		
		
	}


	private void searchForFiles()
	{
		File folder = new File(INSTRUCTIONS_PATH);
		File instruction = new File (INSTRUCTION_PATH);
		
		if(folder.exists())
		{
			File []filesInside = folder.listFiles();
			
			
			for(File f: filesInside)
			{
				if(f.getName().toUpperCase().endsWith(".NC"))
					model.addFile(f);
			}
			
			
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Nie znaleziono folderu z postprocesorami - utworzono nowy folder", "B��d odczytu pliku", JOptionPane.OK_OPTION);
		}
		
		if(!instruction.exists())
		{
			createFolderWithInstructionFile();
		}
	}
	
	
	private void updatePostprocesorModelList()
	{
		
		if(!this.model.getFileList().isEmpty())
		{
			for(File f:this.model.getFileList())
			{
				model.addPostProcesor(readInstructionFromFile(f));
			}
		}
	}
	
	
	private void createFolderWithInstructionFile()
	{
		File temp = new File(INSTRUCTIONS_PATH);
		temp.mkdir();
		
		File instruction = new File(INSTRUCTION_PATH);
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(instruction)))
		{
			//og�lne
			bw.append("\t**Instrukcja do postprocesor�w**\n");
			bw.append("\n -Plik do odczytu przez program musi mie� rozszerzenie .nc Inne pliki nie b�d� brane pod uwag�.");
			bw.append("\n -Pierwsza linia w pliku musu zawiera� nazw� postprocesora w formacie %Nazwa");
			bw.append("\n -Ka�da linia zaczynaj�ca si� od znaku * jest traktowana jako komentarz i nie jest interpretowana przez program");
			bw.append("\n -W przypadku b��dnego odczytu/nie rozpoznania komendy przez program wyst�pi b�ad oraz przekazana zostanie informacja "
					+ "\n o linijce kt�ra nie mog�a zosta� prawid�owo odczytana");
			
			//dotyczace instrukcji
			bw.append("\n\n\t **operatory**");
			bw.append("\n-Prawid�owe instrukcje wymienione poni�ej zosta�y pokazane w �rodku ' ' w przypadku prawid�owego pliku znaki te nale�y pomin��");
			bw.append("\n-Dodanie: operator +	 np 'G54+G90'  Dodaje 'G90' kiedy w linii znajduje si� kod 'G54' ");
			bw.append("\n-Kasowanie: operator /	 np '/G98'  Kasuje z ka�dej linii kod 'G98' ");
			bw.append("\n-Kasowanie warunkowe: operator /	 np 'G81/G98'  Kasuje z linii 'G98' kiedy w linii znajduje si� kod 'G81' ");
			bw.append("\n-Kasowanie ca�ej linii: operator KL>	 np 'KL>G30'  Kasuje ca�� linie kodu w kt�rym znajduje si� 'G30'");
			bw.append("\n-Zamiana: operator =>	 np 'M130=>M60' Zamienia w linii 'M130' na 'M60' ");
			bw.append("\n-Zamiana warunkowa: operator ? =>	 np 'G4?X=>P' Kiedy linia kodu zawiera 'G4' zamienia w niej 'X' na 'P' ");
			bw.append("\n-Dopisz poni�ej: operator v+	np 'Sv+G4X2' Po napotkaniu linii z kodem obrot�w wrzeciona dodaje poni�ej now� lini� z kodem 'G4 X2'");
			bw.append("\n-Dodaj wy�ej: operator ^+	 np 'G81^+G71 Z500' Po napotkaniu linii z kodem 'G81' dodaje powy�ej kod 'G71 Z500' ");
			bw.append("\n-Wyodr�bnij funkcje: operator <>	 np '<>M5' u�yty w linii 'N100 M5 M1' zwraca 3 bloki : \n\tN100  \n\tM5  \n\tM1  ");
			bw.append("\n-Wyodr�bnij warunkowo: operator ?<>	np. 'M5?<>M1' jw. z t� r�nic�, �e wyzwala si� tylko je�li w bloku znajduje si� pierwsza funkcja (Tu M5)");
			bw.append("\n-Znak %t jest czytany jako number aktualnego narz�dzia np. 'HA=>D%t' Zamienia korektor HA na D1 (je�eli wcze�niej zostalo wywo�ane narz�dzie T1 i komenda M6)'");
			bw.append("\n-Znak %l (ma�a litera L) jest czytany jako dowolny number. Mo�na go wykorzysta� gdy znany jest typ kodu ale nie jest znany numer. Np. /D%l kasuje korektor D o dowolnym numerze w kodzie - D1, D2 ..itd");
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
	
	
	
	private Postprocesor readInstructionFromFile(File file)
	{
		Postprocesor newPostProcesor = new Postprocesor();

		
		try (Stream<String> readFile=Files.lines(Path.of(file.getPath()))
				.filter(element -> element.length()>0)
				.filter(line -> !line.startsWith("*") && !line.startsWith("%") )){
			
			readFile.forEach(newPostProcesor::addInstruction);
		}
		
		 catch (IOException e) {
		
			Wind.log.writeErrorLog("Error when reading Instrucitons from file", e, this.getClass().getSimpleName());
			
		}
		
		
		//try to get title
		try(BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			String name = br.readLine();
			if(name.startsWith("%"))
				newPostProcesor.setName(name.substring(1));
		} catch (FileNotFoundException e) {
			Wind.log.writeErrorLog("File not found", e, this.getClass().getSimpleName());
			
		} catch (IOException e) {
			Wind.log.writeErrorLog("IOException", e, this.getClass().getSimpleName());
			
		}

		return newPostProcesor;
	}
	

	class InstructionListListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			view.updateInstructionList(view.getComboBoxSelectedItem(). getInstructionsAsString());
			
		}
		
	}
	
	class ExecuteButtonListener implements ActionListener
	{

	@Override
	public void actionPerformed(ActionEvent e) {
		String []temp = parent.txt.getTxtArea().getText().split("\n");
		
		temp=Arrays.stream(temp)
				.map(f->f.replace("\r",""))
				.toArray(String[]::new);
		
		
		for(int i=0;i<temp.length;i++)
		{
			for(Instruction instruction: view.getComboBoxSelectedItem().getInstructions())
			{
				temp[i] = instruction.applyChanges(temp[i]);
			}
		}

		parent.txt.getTxtArea().setText("");

		PrintStream stream = new PrintStream(parent.txt);
		System.setOut(stream);
		
		for(String s: temp)
		{
			if(s.length()!=0)
				System.out.println(s);
		}
	}
	}
}
