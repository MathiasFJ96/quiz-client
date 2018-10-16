import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class QuizGameClient {

	public static void main(String [] args) {
		 System.out.println("Welcome to the quiz client");
		 
	Scanner input = new Scanner(System.in);
	boolean connect = true;
	try {
		
		Socket connectToServer = new Socket("192.168.43.119", 8000);
		DataInputStream fromServer = new DataInputStream(connectToServer.getInputStream());
		DataOutputStream toServer = new DataOutputStream(connectToServer.getOutputStream());
		
		while (connect) {
			System.out.print("");
		}
	}
	catch (IOException ex) {
		System.out.println(ex.toString() + 'n');
	}
	}

}
