package ca.scrabblescoreboard.controller;

import ca.scrabblescoreboard.ScrabbleScoreboardApplication;
import ca.scrabblescoreboard.model.Bonus;
import ca.scrabblescoreboard.model.Game;
import ca.scrabblescoreboard.model.Player;

public class Controller {
	
    /* A = 1, B = 3, C = 3, etc. */
    private static final int[] LETTER_SCORES = {
        1, 3, 3, 2, 1, 4, 2, 4, 1, 8,
        5, 1, 3, 1, 1, 3, 10, 1, 1, 1,
        1, 4, 4, 8, 4, 10
        };
    
    public static int getLetterScore(char letter) {
        char upperCase = Character.toUpperCase(letter);
        return LETTER_SCORES[upperCase - 'A'];
    }
	
	public static Game newGame(int aNumberOfPlayers) {
		Game game = new Game(aNumberOfPlayers);
		if (2 > aNumberOfPlayers || 4 < aNumberOfPlayers) {
			throw new IllegalArgumentException("Number of players should be between 2 and 4.");
		}
		for (int i = 0; i < aNumberOfPlayers; i++) {
			game.addPlayer(new Player("Player " + Integer.toString(i+1), 0, i+1, game));
		}
		game.setCurrentPlayer(game.getPlayer(0));
		ScrabbleScoreboardApplication.setScoreboard(game);
		return game;
	}
	
	public static void changePlayerName (int index, String newName) {
		Game game = ScrabbleScoreboardApplication.getScoreboard();
		game.getPlayer(index).setName(newName);
	}
	
	public static int playWord (String word) {
		int score = 0;
        for (char letter : word.toCharArray()) {
            score += getLetterScore(letter);
        }
        return score;		
	}
	
    public static int doubleLetter (Character c, int score){
    	Bonus doubleLetter = new Bonus("Double Letter", 2);
        score += getLetterScore(c);
        return score;	
    }
    
    public static int tripleLetter (Character c, int score){
    	Bonus tripleLetter = new Bonus("Triple Letter", 3);
        score += 2 * getLetterScore(c);
        return score;	
    }
    
    public static int doubleWord (String word, int score){
    	Bonus doubleWord = new Bonus("Double Word", 2);
        for (char letter : word.toCharArray()) {
            score += getLetterScore(letter);
        }
        return score;	
    }
    
    public static int tripleWord (String word, int score){
    	Bonus tripleWord = new Bonus("Triple Word", 3);
        for (char letter : word.toCharArray()) {
            score += 2 * getLetterScore(letter);
        }
        return score;	
    }
	
	public static int confirmWord (int score) {
		Game game = ScrabbleScoreboardApplication.getScoreboard();
		Player player = game.getCurrentPlayer();
		player.setPoints(player.getPoints() + score);
		int turn = 0;
		for (int i = 0; i < game.getNumberOfPlayers()-1; i++) {
			if (player.getName().equals(game.getPlayer(i).getName())) {
				turn = i+1;
			}
		}
		game.setCurrentPlayer(game.getPlayer(turn));
		return player.getPoints();
	}

}
