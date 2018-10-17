
public class Question implements java.io.Serializable {
	
	public String QuizText;
	public String A;
	public String B;
	public String C;
	public String D;
	public String Ans;
	
	public Question(String QuizText, String A, String B, String C, String D, String Ans) {
		this.QuizText = QuizText;
		this.A = A;
		this.B = B;
		this.C = C;
		this.D = D;
		this.Ans = Ans;
	}
	
	public void PrintQuestion() {
		System.out.println(QuizText);
		System.out.println("A: " +A);
		System.out.println("B: " +B);
		System.out.println("C: " +C);
		System.out.println("D: " +D);
	}
	
	public void CorrectAnswer() {
		System.out.println("The correct answer is: " +Ans);
	}
}
