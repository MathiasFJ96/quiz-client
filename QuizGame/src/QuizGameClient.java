import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;

public class QuizGameClient {

	
	
	public static void main(String [] args) {
		 Scanner input = new Scanner(System.in);
		 
		 System.out.println("Welcome to the quiz client");
		 
	boolean connect = true;
	try {
		
		Socket connectToServer = new Socket("192.168.43.119", 8000);
		DataInputStream fromServer = new DataInputStream(connectToServer.getInputStream());
		DataOutputStream toServer = new DataOutputStream(connectToServer.getOutputStream());
		ObjectInputStream objectFromServer = new ObjectInputStream(connectToServer.getInputStream());
		
		while (connect) {
			System.out.print("");
			
			Question question = (Question) objectFromServer.readObject();
			question.PrintQuestion();
			
			
			
		}
	}
	catch (IOException ex) {
		System.out.println(ex.toString() + 'n');
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
