<<<<<<< HEAD
=======
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
>>>>>>> 74af8a54a5ad3cadddba1975039df055ba98fc11
import java.util.Scanner;

public class QuizGameClient {

	
	
	public static void main(String [] args) {
		 Scanner input = new Scanner(System.in);
		 
		 System.out.println("Welcome to the quiz client");
		 
	Scanner input = new Scanner(System.in);
	boolean connect = true;
	try {
		
		Socket connectToServer = new Socket("localhost", 8000);
		DataInputStream fromServer = new DataInputStream(connectToServer.getInputStream());
		DataOutputStream toServer = new DataOutputStream(connectToServer.getOutputStream());
		
		while (connect) {
			System.out.print("");
		}
	}
	catch (IOException ex) {
		System.out.println(ex.toString() + 'n');
	}
>>>>>>> 74af8a54a5ad3cadddba1975039df055ba98fc11
	}

}
