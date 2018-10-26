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
	private static boolean gameIsRunning;
	private static String answer;
	private static int playerNum;
	
	public static String verifyAnswer;
	public static String qAnswer;
	public static int player1Score, player2Score, player3Score;
	
	
	public static void main(String [] args) {
		
			// Initialize scanner
	input = new Scanner(System.in);
		 	System.out.println("Welcome to the quiz client");
		 
		 	// Boolean for starting game
	connect = true;
	
	System.out.println("Do you want to Join this session ? y/n");
	answer = input.next().toLowerCase();
	
	try {
			// Initialize socket and data streams
		connectToServer = new Socket("192.168.43.119", 8000);
		toServer = new DataOutputStream(connectToServer.getOutputStream());
		fromServer = new DataInputStream(connectToServer.getInputStream());
		
		
		wantToPlay(answer);
			// Close connection if player typed n
		while (connect) {
			//toServer.flush();
			playerNum = fromServer.readInt();
			System.out.println("You are player"+ playerNum);
			
			// System.out.print("");
			
			while (gameIsRunning) {
			questionNumber = fromServer.readInt();
			System.out.println(questionNumber);
			QuestionDB.questions[questionNumber].PrintQuestion();
			
			
			qAnswer = input.next();
			//recursive method about getting the correct input from client
			verifyInput(qAnswer);
				 		
			//correct answer + score addition
			correctAnswer();
			
			leadingPlayer = fromServer.readInt();
			//status of the quiz (who is leading etc.)
			status();
			
			
			//check win condition
			winConditionMet();
			
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
	
	
	public static void correctAnswer() {
		try {
		if(QuestionDB.questions[questionNumber].checkAns(verifyAnswer)) {
			score++;
			toServer.writeInt(score);
			System.out.println("Correct, you now have " + score + " points");
			// +score for that
		}
		else {
			QuestionDB.questions[questionNumber].CorrectAnswer();
		}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void status () {
		try {
		
		if (leadingPlayer < 3 ) {
		System.out.println(leadingPlayer + "is in the lead with " + fromServer.readInt() + " points");
		}else {
		System.out.println("multiple players are leading with " + fromServer.readInt() + " points");
		}
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
	}
	
	public static void winConditionMet(){
		try {
			
			if (playerNum == 1) {
				player2Score = fromServer.readInt();
				player3Score = fromServer.readInt();
			} else if (playerNum == 2) {
				player1Score = fromServer.readInt();
				player3Score = fromServer.readInt();
			} else if (playerNum == 3) {
				player1Score = fromServer.readInt();
			    player2Score = fromServer.readInt();
			}
			
		
			if(player1Score >= 5 && player1Score == player2Score && player1Score == score) {
			
			} else if (player1Score >= 5 && player1Score == player2Score) {
			System.out.println("draw");
			} else if (player1Score >= 5 && player1Score == score) {
			
			} else if (player2Score >= 5 && player2Score == score) {
			
			} else if(player1Score >= 5 ) {
			gameIsRunning = false;
			endGameStatus(1, player1Score);
			} else if (player2Score >=5 ){
			gameIsRunning = true;
			endGameStatus(2, player2Score);
			} else if (score >= 5) {
			gameIsRunning = false;
			endGameStatus(0,score);
			
		}
		
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
	}
	public static void endGameStatus(int x,int score) {
		if( x == 0 ) {
			System.out.println("You Won with: " + score);
		}else {
			System.out.println("player: " + x + " won with: " + score);
		}
	}
	public static void wantToPlay(String answer) {
		try {
		// Start game if player typed y
		if(answer.equals("y")) {
			toServer.writeInt(1);
			System.out.println("Connecting to Lobby");
			gameIsRunning = true;
		}
		if(answer.equals("n")) {
			connect=false;
			connectToServer.close();
			input.close();
		}
		if (answer.equals("y") || answer.equals("n")){
			
		} else {
			
			wantToPlay(input.next().toLowerCase());
		}
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
	}
	
	
} // Class bracket
