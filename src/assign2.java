import java.util.Scanner;
import javax.swing.JOptionPane;

public class assign2 {
	
	public static void main(String[] args){
		
		Scanner input = new Scanner(System.in);
		Game debug = new Game(true);			//initializes game
		String in = debug.introduction();					//introduction
		boolean start = debug.prompt(in);	//prompt to play
		Code secret = new Code(debug.getNumSecret());
		while(start){	//generate secret code
			JOptionPane.showMessageDialog(null, "\nGenerating secret code....\n");
			secret.generateSecretCode(secret.getSecretCode() ,debug.getNumSecret(), debug.getNumColors());
			if(debug.getReveal())	//show secret code
				secret.printSecret();
			debug.runGame(secret.getSecretCode(), secret.getGuess(), input);	//run game
			in = JOptionPane.showInputDialog("Are you ready for another game? (Y/N)");
			start = debug.prompt(in);	//prompt to play
		}
		input.close();
	}
}
