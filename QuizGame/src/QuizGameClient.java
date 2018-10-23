import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class QuizGameClient {

	static int score = 0; 
	
	public static void main(String [] args) {
		 Scanner input = new Scanner(System.in);
		 	System.out.println("Welcome to the quiz client");
		 
	boolean connect = true;
	
	System.out.println("Do you want to continue ? y/n lowercase only");
	String answer = input.next();
	
	try {
		
		Socket connectToServer = new Socket("192.168.43.181", 8200);
		ObjectOutputStream toServer = new ObjectOutputStream(connectToServer.getOutputStream());
		ObjectInputStream fromServer = new ObjectInputStream(connectToServer.getInputStream());
		
		if(answer.equals("y")) {
			toServer.writeInt(1);
			System.out.println("Connecting to server");
		}
		
		while (connect) {
			toServer.flush();
			if(answer.equals("n")) {
				connect=false;
				connectToServer.close();
				input.close();
			}
			
			System.out.println("You are player "+fromServer.readInt());
			
			// System.out.print("");
			
			Question question = (Question) fromServer.readObject();
			question.PrintQuestion();
			
			String qAnswer = input.next();
			if(qAnswer.equals(question.getAns())) {
				score++;
				toServer.writeInt(score);
				System.out.println("You have");
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
