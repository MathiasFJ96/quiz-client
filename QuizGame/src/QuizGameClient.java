import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class QuizGameClient {
		// Variables for player score and question numbers
	static int score = 0;
	private static int questionNumber;
	private static int leadingPlayer;
	
	private static Socket connectToServer;
	private static DataOutputStream toServer;
	private static DataInputStream fromServer;
	
	private static Scanner input;
	private static boolean connect;
	private static String answer;
	
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
		connectToServer = new Socket("192.168.43.119", 8200);
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
			
			
			String qAnswer = input.next();
			if(QuestionDB.questions[questionNumber].checkAns(qAnswer)) {
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

} // Class bracket
