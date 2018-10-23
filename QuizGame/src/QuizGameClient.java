import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class QuizGameClient {

	static int score = 0;
	private static int questionNumber;
	
	public static void main(String [] args) {
		 Scanner input = new Scanner(System.in);
		 	System.out.println("Welcome to the quiz client");
		 
	boolean connect = true;
	
	System.out.println("Do you want to continue ? y/n lowercase only");
	String answer = input.next();
	
	try {
		
		Socket connectToServer = new Socket("192.168.43.119", 8200);
		DataOutputStream toServer = new DataOutputStream(connectToServer.getOutputStream());
		DataInputStream fromServer = new DataInputStream(connectToServer.getInputStream());
		
		if(answer.equals("y")) {
			toServer.writeInt(1);
			System.out.println("Connecting to server");
		}
		
		while (connect) {
			//toServer.flush();
			if(answer.equals("n")) {
				connect=false;
				connectToServer.close();
				input.close();
			}
			
			System.out.println("You are player "+fromServer.readInt());
			
			// System.out.print("");
			
			
			questionNumber = fromServer.readInt();
			System.out.println(questionNumber);
			QuestionDB.Question1.PrintQuestion();
			
			String qAnswer = input.next();
			if(qAnswer.equals(QuestionDB.Question1.getAns())) {
				score++;
				toServer.writeInt(score);
				System.out.println("You have");
				// +score for that
			}
			
		}
	}
	catch (IOException ex) {
		System.out.println(ex.toString() + 'n');
	} 
	}

}
