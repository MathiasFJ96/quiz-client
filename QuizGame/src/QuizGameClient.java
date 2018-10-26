import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class QuizGameClient {
		// Variables for player score and question numbers
	private static int score = 0;
	private static int questionNumber;
	private static int leadingPlayer;
	
	private static Socket connectToServer;
	private static DataOutputStream toServer;
	private static DataInputStream fromServer;
	
	private static Scanner input;
	private static boolean connect;
	private static String answer;
	public static String verifyAnswer;
	public static String qAnswer;
	
	public static void main(String [] args) {
		
			// Initialize scanner
	input = new Scanner(System.in);
		 	System.out.println("Welcome to the quiz client");
		 
		 	// Boolean for starting game
	connect = true;
	
	System.out.println("Do you want to continue ? y/n lowercase only");
	answer = input.next();
	
	try {
			// Initialize socket and data streams
		connectToServer = new Socket("192.168.43.119", 8000);
		toServer = new DataOutputStream(connectToServer.getOutputStream());
		fromServer = new DataInputStream(connectToServer.getInputStream());
		
			// Start game if player typed y
		if(answer.equals("y")) {
			toServer.writeInt(1);
			System.out.println("Connecting to server");
		}
		
			// Close connection if player typed n
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
			
			
			qAnswer = input.next();
			verifyInput(qAnswer);
				 			 
			if(QuestionDB.questions[questionNumber].checkAns(verifyAnswer)) {
				score++;
				toServer.writeInt(score);
				System.out.println("Correct, you now have " + score + " points");
				// +score for that
			}
			else {
				QuestionDB.Question1.CorrectAnswer();
			}
			leadingPlayer = fromServer.readInt();
			if (leadingPlayer < 3 ) {
			System.out.println(leadingPlayer + "is in the lead with " + fromServer.readInt() + " points");
			}else {
			System.out.println("multiple players are leading with " + fromServer.readInt() + " points");
			}
			
			
			
			}
			
		}
	} // Try end
	catch (IOException ex) {
		System.out.println(ex.toString() + 'n');
	}

	} // Main thread
	public static void verifyInput(String answer) {
		if (answer.equals("a") || answer.equals("b") || answer.equals("c") || answer.equals("d")) {
			verifyAnswer = answer;
			
		} else {
			System.out.println("Please answer with a,b,c or d");
			verifyAnswer = input.next();
		}
		
		if(verifyAnswer.equals("a")|| verifyAnswer.equals("b") || verifyAnswer.equals("c") || verifyAnswer.equals("d")) {
			
     	} else {
     		verifyInput(answer);
     	}
		
	}
} // Class bracket
