package CordConverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;



public class Log {
	
	LogManager logManager;
	private static Logger logger;
	private FileHandler fileHandler;
	private int logNumber=0;
	static final String FILE = "FrezLog/FrezLog.txt";
	LocalTime date;
	static final DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("kk:mm:ss");
	private boolean enableLogging;
	
	
	
	
	
	public Log() 
	{
		
		LogManager.getLogManager().reset();
	//logManager & logger
		if(logger==null)
			logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
		preapareLogging();
	}
	
	private void preapareLogging() 
	{
		try
		{
			readyFile();
	
			 readyHandler(FILE);
			 
			
			enableLogging = true;
		}
		catch (IOException e)
		{
			enableLogging = false;
		}
	}
	
	
	
	private void readyHandler(String FILE) throws IOException
	{
		//handler
		fileHandler = new FileHandler(FILE,true);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}
	
	
	
	
	
	private void readyFile() throws IOException
	{
		
		//check if Directory exists if not create one
		Path dictionaryLog = Paths.get("FrezLog");
		if(!Files.exists(dictionaryLog) )
		{	
				Files.createDirectory(dictionaryLog);
		}
		
		//operations in file log.txt
		Path log = Paths.get(FILE);
		
		if(!Files.exists(log))
		{	
				Files.createFile(log);
		}
		
		
	}
	
	
	
	
	
	
	
	public void writeErrorLog(String message, Exception ex, String activeClass)
	{
		if(enableLogging)
		{
			logNumber++;
			
			String errorMessageFormat = String.format(" LN %d: Exception occured:%s Class invoked: %s%nTFN:%s%n",logNumber,ex.getMessage(),activeClass,message);
			logger.log(Level.SEVERE, errorMessageFormat);
		}
	}
	
	public void writeInfoLog(String message, String activeClass)
	{
		if(enableLogging)
		{
			logNumber++;
		
			date = LocalTime.now();
			String infoMessageFormat = String.format("LN %d: Class invoked: %s%n:%s%n",logNumber,activeClass,message);
			logger.log(Level.INFO, infoMessageFormat);
		}
	}
	
	
}
