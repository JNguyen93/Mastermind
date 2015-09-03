import java.util.Random;

public class Code {
	private char[] secretCode;						//secretcode generated
	private char[] guess;
	
	public Code(int numSecret){
	secretCode = new char[numSecret];					//secretcode generated
	guess = new char[numSecret];							// player's guess
	}
	
	public void generateSecretCode(char[] secret, int numSecret, int numColor) {
		String colors = "BGOPRYM";			//string of possible secrete code options
		Random rand = new Random();			
		for (int i = 0; i < numSecret; i++) {		//fill array with code
			secret[i] = colors.charAt(rand.nextInt(numColor));
		}
	}

	public void printSecret(){	
		System.out.println(secretCode);
		}

	public char[] getSecretCode(){
		return secretCode;
	}
	
	public char[] getGuess(){
		return guess;
	}
	
}
