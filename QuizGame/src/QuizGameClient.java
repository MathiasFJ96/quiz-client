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
	
	System.out.println("Do you want to continue ? y/n lowercase only");
	String answer = input.next();
	
	try {
		
		Socket connectToServer = new Socket("192.168.43.119", 8300);
		DataInputStream fromServer = new DataInputStream(connectToServer.getInputStream());
		DataOutputStream toServer = new DataOutputStream(connectToServer.getOutputStream());
		ObjectInputStream objectFromServer = new ObjectInputStream(connectToServer.getInputStream());
		
		if(answer.equals("y")) {
			toServer.writeInt(1);
		}
		
		while (connect) {
			toServer.flush();
			if(answer.equals("n")) {
				connect=false;
				connectToServer.close();
				input.close();
			}
				
			// System.out.print("");
			
			Question question = (Question) objectFromServer.readObject();
			question.PrintQuestion();
			
			String qAnswer = input.next();
			if(qAnswer.equals(question.getAns())) {
				// +score for that
			}
			
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
