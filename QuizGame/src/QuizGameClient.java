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
			
			for (int i = 0; i < QuestionDB.questions.length; i++) {
			questionNumber = fromServer.readInt();
			System.out.println(questionNumber);
			QuestionDB.questions[questionNumber].PrintQuestion();
			
			
			String qAnswer = input.next();
			if(QuestionDB.Question1.checkAns(qAnswer)) {
				score++;
				toServer.writeInt(score);
				System.out.println("Correct, you now have " + score + " points");
				// +score for that
			}
			else {
				QuestionDB.Question1.CorrectAnswer();
			}
			System.out.println("Leading player has " + fromServer.readInt() + "points");
			}
			
		}
	}
	catch (IOException ex) {
		System.out.println(ex.toString() + 'n');
	} 
	}

}
