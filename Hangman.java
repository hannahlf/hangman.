/**
 * Add comments at the heading describing what the program does
 * as well as who authored it.
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Hangman {
	public int incorrect = 0;
	public boolean playing = true;
	List<String> wordList = new ArrayList<String>();
	ArrayList<Character> guessed = new ArrayList<Character>();

	/**
	 * Read in the list of words
	 */
	public void readWords(String fileName) throws FileNotFoundException {
		Scanner s = new Scanner(new File(fileName));

		while (s.hasNext())
			wordList.add(s.next());

		s.close();
	}

	/**
	 * Selects a random word from the list
	 * and returns it.
	 */
	public String getWord() {
		// returns a random word from wordList

		Random r = new Random();

		return wordList.get(r.nextInt(wordList.size()));
	}
	
	public boolean inWord(char nextChoice, String word) {
		boolean correct = false;
		//String word = getWord();
		for(int i = 0; i < word.length(); i++) {
			if(nextChoice == word.charAt(i))
			{
				correct = true;
				break;
			}//else {
				//correct = false;
		//}
		}

		return correct;

	}

	private int indexOf(Object nextChoice) {
		int index = -1;
		for (int i = 0; i < wordList.size(); i++) {
			if (nextChoice == guessed) {
				index = i;
				break;
			}
		}

		return index;
	}
	
	public boolean contains(char nextChoice, CharSequence guessed)
	{
		boolean hasGuessed = false;
		for(int i = 0; i < guessed.length(); i++)
		{
			if(nextChoice == guessed.charAt(i))
			{
				hasGuessed = true;
			}
		}
		
		
		return hasGuessed;
	}



	//Replaces correct letters in word
	public StringBuilder replaceLetter(char nextChoice, String word, StringBuilder blank) {

		for(int i = 0; i < word.length(); i++)
		{
			if(nextChoice == word.charAt(i))
			{
				blank.setCharAt(i, nextChoice);
			}
		}

		return blank;
	}
	
	//Checks to see if blank guesses match real word
	public boolean won(String word, StringBuilder blank)/////METHOD NOT WORKING///////
	{
		if(word.equals(blank))
		{
			return true;
		}else
			return false;
	}


	/**
	 * Plays the game. Currently very limited functionality.
	 */
	public void playGame(String word) {
		char nextChoice;
		Scanner reader = new Scanner(System.in);
	    word = getWord();
	    // construct the empty StringBuilder object
	    StringBuilder blank = new StringBuilder();
	    for (int i = 0; i < word.length(); i++)
	    	blank.append('_');  

		//Starts the game keeps the turns under 6
		while (playing == true)
		{
			if(won(word, blank) == true)
				{
					playing = false;
					System.out.println("Congratulations you guessed the word!");
				}
			System.out.print("Your choice: ");
			nextChoice = reader.next().charAt(0);
					
			Character.toLowerCase(nextChoice);
			if(Character.isLetter(nextChoice) == false)
			{
				System.out.println("Please choose a letter from the alphabet.");
				playing = true;
			}
				
			
			if(guessed.contains(nextChoice) == true)
			{
				System.out.println("You have already guessed that letter.  Guess again");
				playing = true;
			}
			
			if(inWord(nextChoice, word) == true)
			{
				guessed.add(nextChoice);
				System.out.println(replaceLetter(nextChoice, word, blank));
			}else{
				System.out.println(nextChoice + " was not in the word");
				incorrect++;
				if(incorrect == 100)
				{
					playing = false;
					System.out.println();
					System.out.println("The word was " + word + " you did not guess the word");
				}else
				{
					System.out.println("Incorrect guesses " + incorrect);
				}
				}	
		   
			
			}
	}



	public static void main(String[] args) {
		Hangman game = new Hangman();


		try {
			game.readWords("words.txt");
			game.playGame(game.getWord());
		} catch (FileNotFoundException fnf) {
			System.err.println("words.txt file Not Found");
		}


	}

}
