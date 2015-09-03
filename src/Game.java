import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Game {	//game class
	private boolean reveal;
	private String intro;
	private int guessCount;
	private int numGuess;
	private int numSecret;
	private int numColors;
	private int black;
	private int white;
	private int guessIndex;
	private String[][] history;
	private String colors = "BGOPRYM";			//string of possible secret code options

	public Game(boolean debug){					//game class constructor
		reveal = debug;
		numSecret = 5;
		numColors = 7;
		numGuess = 15;
		history = new String[numGuess][2];
		guessIndex = 0;
	}
	
	public String introduction(){	//prints introduction
		intro = "Welcome to Mastermind. Here are the Rules.\n\nThis is a text "
				+ "verson of the class board game Mastermind.\n"
				+ "The computer will think of a secret code. The code consists of " + numSecret + " colored pegs.\n"
				+ "The pegs may be one of " + numColors+ " colors: blue, green, orange, purple, red, or yellow.\n"
				+ "A color may appear more than once in the code.\n"
				+ "You try to guess what colored pegs are in the code and what order they are in.\n"
				+ "After you make a valid guess, the result will be displayed.\n"
				+ "The result consists of a black peg for each peg you have guessed exactly correct\n"
				+ "(color and position) in your guess. For each peg that is the correct color, but out "
				+ "of position,\nyou get a white peg. For each peg, which is fully incorrect, you get no feedback.\n"
				+ "\nOnly the first letter of the color is displayed. B is for blue, R is for Red, and so forth.\n"
				+ "When entering guesses you only need to enter the first character of each color as a capital letter.\n\n"
				+ "You have " + numGuess + " guesses to figure out the secret code or you lose the game. Are you ready to play?\n"
				+ "(Y/N):";
		String input = JOptionPane.showInputDialog(intro);
		return input;
	}
	
	public boolean getReveal(){
		return reveal;
	}
	
	public int getNumColors(){
		return numColors;
	}
	
	public int getNumSecret(){
		return numSecret;
	}
	
	public int getGuessCount(){
		return guessCount;
	}
	
	public void recordHistory(String g, String r){
		history[guessIndex][0] = g;
		history[guessIndex][1] = r;
		guessIndex++;
	}
	
	public void displayHistory(){
		ArrayList<String> hist = new ArrayList<String>();
		hist.add("Guess  Results");
		for(int i = 0; i < guessIndex; i++){
			hist.add("\n" + history[i][0] + "   " + history[i][1]);
		}
		JOptionPane.showMessageDialog(null, hist.toString());
	}
	
	public void printHistory(){
		System.out.println("Guess  Results");
		for(int i = 0; i < guessIndex; i++){
			System.out.println(history[i][0] + "   " + history[i][1]);
		}
	}
	
	public boolean prompt(String in){		//prompts user for Y or N
		String s = in;
		char c = s.charAt(0);
		while(c != 'Y' || c != 'N'){
		try{
			if(c == 'Y'){
				guessCount = numGuess;
				guessIndex = 0;
				return true;
			}
			else if(c == 'N'){
				return false;
			}
			else
				throw new IllegalArgumentException("Please input a Y or N.");
			}
			catch(IllegalArgumentException e){
				JOptionPane.showMessageDialog(null, e.getMessage());
				s = JOptionPane.showInputDialog("Are you ready to play? (Y/N):");
				c = s.charAt(0);
			}
		}
		return false;
	}
	
	private boolean validGuess(String colors, String guess){	//checks the guess vs colors available
		for(int i = 0; i < guess.length(); i++){
			boolean result = (colors.indexOf(guess.charAt(i)) > -1);
			if(!result)
				return false;
		}
		return true;
	}

	public void runGame(char[] secret, char[] guess, Scanner in){
		//Scanner keyboard = in;
		Pegs peg = new Pegs(guess, secret);
		
		while(guessCount != 0){
		String playerGuess = JOptionPane.showInputDialog("You have " + guessCount + " guesses left.\n"
				+ "What is your next guess?\n"
				+ "Type in your next guess and press enter.\n"
				+ "Enter your guess: ");

		try{
		if(playerGuess.equals("history")){
			if(guessIndex == 0){
				JOptionPane.showMessageDialog(null, "You haven't guessed yet!\n");
			}
			else{
				printHistory();
				displayHistory();
				System.out.println("");
			}		
		}
		//checks the length of the guess and if the guesses are valid colors
		else if (playerGuess.length() != numSecret || !(validGuess(colors, playerGuess))){
			throw new IllegalArgumentException("Invalid guess! Please input a new guess.");
			//JOptionPane.showMessageDialog(null, playerGuess + "->" + "INVALID GUESS");
			//System.out.println(playerGuess + "-> " + "INVALID GUESS");
			}
		else{
			for(int i = 0; i < numSecret; i++){		//copies guess to array
				guess[i] = playerGuess.charAt(i);
				}
				if (peg.checkGuess()){		//if four black pegs, they win
					JOptionPane.showMessageDialog(null, playerGuess + "-> Result: " + peg.getBlack() + " black pegs - " + "You Win !!\n");
					System.out.println(playerGuess + "-> Result: " + peg.getBlack() + " black pegs - ");
					System.out.println("You Win !!\n");
					return;
				}	
				else{	//if they don't win, return how many black and white pegs they have
					black = peg.getBlack();
					white = peg.getWhite();
					if(white > 0 && black > 0){
						JOptionPane.showMessageDialog(null, playerGuess + "-> Result: " + black + " black peg(s) and " + white + " white peg(s).\n");
						System.out.println(playerGuess + "-> Result: "+ black + " black peg(s) and " + white + " white peg(s).\n");
					}
					else if (white > 0 && black <= 0){
						JOptionPane.showMessageDialog(null, playerGuess + "-> Result: " + white + " white peg(s).\n");
						System.out.println(playerGuess + "-> Result: "+ white + " white peg(s).\n");
					}
					else if (black > 0 && white <= 0){
						JOptionPane.showMessageDialog(null, playerGuess + "-> Result: " + black + " black peg(s).\n");
						System.out.println(playerGuess + "-> Result: "+ black + " black peg(s).\n");
					}
					else{
						JOptionPane.showMessageDialog(null, playerGuess + "-> Result: No pegs.\n");
						System.out.println(playerGuess + "-> Result: No pegs.\n");
					}
					guessCount--;
					peg.setToZero();
				}
				recordHistory(playerGuess, black + " black peg(s) " + white + " white peg(s)");
			}
		}
		catch(IllegalArgumentException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
			if(guessCount == 0){	//if guesses = 0, they lose
				JOptionPane.showMessageDialog(null, "You have no more guesses. You lose!");
				System.out.println("You have no more guesses. You lose!");
				return;
			}
		}
	}
}
