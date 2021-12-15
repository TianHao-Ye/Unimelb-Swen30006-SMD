package log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;



public class Log{
	private static Log log = null;  
	private final Path file_name = Path.of("cribbage.log");

	private Log(){
		try {
			Files.deleteIfExists(file_name);
		}catch(IOException e) {
			System.err.println(e);
		}
 
	}
	
	public static Log getInstance() {
		if(log == null) {
			log = new Log();
		}
	    return log;
	}

	public void logging(String text){
		String s = text +"\n";
		try {
			Files.write(file_name, s.getBytes(), StandardOpenOption.CREATE,StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.err.println(e);
		}
	 
	}
}
