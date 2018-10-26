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
	private static int LeadingPlayersScore;
	
	private static Scanner input;
	private static boolean connect;
	private static boolean gameIsRunning;
	private static String answer;
	private static int playerNum;
	
	public static String verifyAnswer;
	public static String qAnswer;
	public static int player1Score, player2Score, player3Score, everoneWantToPlay;
	
	
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
		connectToServer = new Socket("192.168.43.119", 8900);
		toServer = new DataOutputStream(connectToServer.getOutputStream());
		fromServer = new DataInputStream(connectToServer.getInputStream());
		
		
		wantToPlay(answer);
			// Close connection if player typed n
		
		while (connect) {
			//toServer.flush();
			if (playerNum == 0) {
				playerNum = fromServer.readInt();
			System.out.println("You are player"+ playerNum);
			}
			everoneWantToPlay = fromServer.readInt();
			
			if (everoneWantToPlay == 2) {
				System.out.println("Someone Left");
				connect = false;
					
			} 
			
			
			// System.out.print("");
			
			while (gameIsRunning) {
				
			
			questionNumber = fromServer.readInt();
			//System.out.println(questionNumber);
			QuestionDB.questions[questionNumber].PrintQuestion();
			
			
			qAnswer = input.next();
			//recursive method about getting the correct input from client
			verifyInput(qAnswer);
				 		
			//correct answer + score addition
			correctAnswer();
			
			leadingPlayer = fromServer.readInt();
			LeadingPlayersScore = fromServer.readInt();
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
			toServer.writeInt(score);
		}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void status () {
		
		if (leadingPlayer <=3 ) {
		System.out.println("Player" +leadingPlayer+ ": " + "is in the lead with " + LeadingPlayersScore + " points");
		}else {
		System.out.println("multiple players are leading with " + LeadingPlayersScore + " points");
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
			
		
			if ( (player1Score == player2Score && player1Score >= 5)|| (score == player2Score && score >= 5) || (player1Score == player3Score && player1Score >=5) || (score == player3Score && score >= 5)|| (player2Score == player3Score && player2Score>=5) || (score == player3Score && score>=5)) {
			endGameStatus(4, 5);
			}else if(player1Score >= 5 ) {
			gameIsRunning = false;
			endGameStatus(1, player1Score);
			} else if (player2Score >=5 ){
			gameIsRunning = true;
			endGameStatus(2, player2Score);
			} else if (score >= 5) {
			gameIsRunning = false;
			endGameStatus(0,score);
			}else if (player3Score >= 5) {
				gameIsRunning = false;
				endGameStatus(3,player3Score);
			}
			
		
		
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
	}
	
	public static void playAgain(String answer) {
		try {
			
			
			// Start game if player typed y
			if(answer.equals("y")) {
				toServer.writeInt(1);
				System.out.println("Connected to Lobby");
				System.out.println("Game starting over");
				System.out.println("Waiting for all 3 players to answer");
				connect = true;
				gameIsRunning = true;
				score = 0;
			}
			if(answer.equals("n")) {
				toServer.writeInt(0);
				connect = true;
				connectToServer.close();
				input.close();
			}
			if (answer.equals("y") || answer.equals("n")){
				
			} else {
				System.out.println("please write y or n");
				playAgain(input.next().toLowerCase());
			}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
	}
	public static void endGameStatus(int x,int score) {
		if( x == 0 ) {
			System.out.println("You Won with: " + score);
			System.out.println("Do you want to play again? y/n");
			playAgain(input.next().toLowerCase());
		} else if (x ==4) {
			System.out.println("Drawed with: " + score);
			System.out.println("Do you want to play again? y/n");
			playAgain(input.next().toLowerCase());
		}else {
			System.out.println("player " + x + " won with: " + score);
			System.out.println("Do you want to play again? y/n ");
			playAgain(input.next().toLowerCase());
		}
		
		
	}
	public static void wantToPlay(String answer) {
		try {
		// Start game if player typed y
		if(answer.equals("y")) {
			toServer.writeInt(1);
			System.out.println("Connected to Lobby");
			gameIsRunning = true;
		}
		if(answer.equals("n")) {
		
			toServer.writeInt(2);
			
			System.out.println("Server closing");
			
			
		}
		if (answer.equals("y") || answer.equals("n")){
			
		} else {
			System.out.println("please write y or n");
			wantToPlay(input.next().toLowerCase());
		}
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
	}
	
	
} // Class bracket
