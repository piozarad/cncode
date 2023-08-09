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
	private static final String INSTRUCTION_PATH = "postprocesory\\Instrukcje do postprocesorów.txt";

	
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
			JOptionPane.showMessageDialog(null, "Nie znaleziono folderu z postprocesorami - utworzono nowy folder", "B³¹d odczytu pliku", JOptionPane.OK_OPTION);
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
			//ogólne
			bw.append("\t**Instrukcja do postprocesorów**\n");
			bw.append("\n -Plik do odczytu przez program musi mieæ rozszerzenie .nc Inne pliki nie bêd¹ brane pod uwagê.");
			bw.append("\n -Pierwsza linia w pliku musu zawieraæ nazwê postprocesora w formacie %Nazwa");
			bw.append("\n -Ka¿da linia zaczynaj¹ca siê od znaku * jest traktowana jako komentarz i nie jest interpretowana przez program");
			bw.append("\n -W przypadku b³êdnego odczytu/nie rozpoznania komendy przez program wyst¹pi b³ad oraz przekazana zostanie informacja "
					+ "\n o linijce która nie mog³a zostaæ prawid³owo odczytana");
			
			//dotyczace instrukcji
			bw.append("\n\n\t **operatory**");
			bw.append("\n-Prawid³owe instrukcje wymienione poni¿ej zosta³y pokazane w œrodku ' ' w przypadku prawid³owego pliku znaki te nale¿y pomin¹æ");
			bw.append("\n-Dodanie: operator +	 np 'G54+G90'  Dodaje 'G90' kiedy w linii znajduje siê kod 'G54' ");
			bw.append("\n-Kasowanie: operator /	 np '/G98'  Kasuje z ka¿dej linii kod 'G98' ");
			bw.append("\n-Kasowanie warunkowe: operator /	 np 'G81/G98'  Kasuje z linii 'G98' kiedy w linii znajduje siê kod 'G81' ");
			bw.append("\n-Kasowanie ca³ej linii: operator KL>	 np 'KL>G30'  Kasuje ca³¹ linie kodu w którym znajduje siê 'G30'");
			bw.append("\n-Zamiana: operator =>	 np 'M130=>M60' Zamienia w linii 'M130' na 'M60' ");
			bw.append("\n-Zamiana warunkowa: operator ? =>	 np 'G4?X=>P' Kiedy linia kodu zawiera 'G4' zamienia w niej 'X' na 'P' ");
			bw.append("\n-Dopisz poni¿ej: operator v+	np 'Sv+G4X2' Po napotkaniu linii z kodem obrotów wrzeciona dodaje poni¿ej now¹ liniê z kodem 'G4 X2'");
			bw.append("\n-Dodaj wy¿ej: operator ^+	 np 'G81^+G71 Z500' Po napotkaniu linii z kodem 'G81' dodaje powy¿ej kod 'G71 Z500' ");
			bw.append("\n-Wyodrêbnij funkcje: operator <>	 np '<>M5' u¿yty w linii 'N100 M5 M1' zwraca 3 bloki : \n\tN100  \n\tM5  \n\tM1  ");
			bw.append("\n-Wyodrêbnij warunkowo: operator ?<>	np. 'M5?<>M1' jw. z t¹ ró¿nic¹, ¿e wyzwala siê tylko jeœli w bloku znajduje siê pierwsza funkcja (Tu M5)");
			bw.append("\n-Znak %t jest czytany jako number aktualnego narzêdzia np. 'HA=>D%t' Zamienia korektor HA na D1 (je¿eli wczeœniej zostalo wywo³ane narzêdzie T1 i komenda M6)'");
			bw.append("\n-Znak %l (ma³a litera L) jest czytany jako dowolny number. Mo¿na go wykorzystaæ gdy znany jest typ kodu ale nie jest znany numer. Np. /D%l kasuje korektor D o dowolnym numerze w kodzie - D1, D2 ..itd");
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
