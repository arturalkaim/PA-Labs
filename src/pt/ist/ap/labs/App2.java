package pt.ist.ap.labs;
import java.io.*;

public class App1{

	public static void main(String[] args) {
		try{
			IntroShell s = new IntroShell();
			s.execute();
		} catch(IOException ioe){
			ioe.printStackTrace();
		}	
		

	}
	
}