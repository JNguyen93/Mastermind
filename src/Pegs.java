
public class Pegs {
	private char[] guess;
	private char[] guess1;
	private char[] secret;
	private char[] secret1;
	private int[] pegs;
	private int black;
	private int white;
	
	public Pegs(char[] guess, char[] secret){
		this.guess = guess;
		this.secret = secret;
		guess1 = new char[secret.length];
		secret1 = new char[secret.length];
		black = 0;
		white = 0;
		pegs = new int[secret.length];
	}
	
	public boolean checkGuess(){
		for(int i = 0; i < pegs.length; i++){
			pegs[i] = 0;
		}
		for(int i = 0; i< secret.length; i++){
			guess1[i] = guess[i];
			secret1[i] = secret[i];
		}
		//Scans guess for correct color and position
		for(int i = 0; i < secret.length; i++){
			if(guess1[i] == secret1[i]){
				guess1[i] = 'N';
				secret1[i] = 'n';
				pegs[i] = 1;
				black++;
			}
		}
		//Scans guess for correct color but wrong position
		for(int i = 0; i < secret.length; i++){
			for(int j = 0; j < secret.length; j++){
				if(secret1[i] == guess1[j]){
					if(pegs[i] != 1){
						guess1[j] = 'N';
						secret1[i] = 'n';
						pegs[i] = 2;
						white++;
					}
				}
			}
		}
		//if all black, you win!
		if(black == secret.length)
			return true;
		else
			return false;
	}
	
	public int getBlack(){
		return black;
	}
	
	public int getWhite(){
		return white;
	}
	
	public void setToZero() {
		black = 0;
		white = 0;
		return ;
	}
	
}
