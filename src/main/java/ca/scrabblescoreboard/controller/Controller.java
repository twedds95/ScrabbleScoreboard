package ca.scrabblescoreboard.controller;

import ca.scrabblescoreboard.ScrabbleScoreboardApplication;
import ca.scrabblescoreboard.model.*;

public class Controller {
	
    /* A = 1, B = 3, C = 3, etc. */
    private static final int[] LETTER_SCORES = {
        1, 3, 3, 2, 1, 4, 2, 4, 1, 8,
        5, 1, 3, 1, 1, 3, 10, 1, 1, 1,
        1, 4, 4, 8, 4, 10
        };
    
    private static int getLetterScore(char letter) {
        char upperCase = Character.toUpperCase(letter);
        return LETTER_SCORES[upperCase - 'A'];
    }
	
	public static Game newGame(int aNumberOfPlayers) {
		Game game = new Game(aNumberOfPlayers);
		if (2 > aNumberOfPlayers || 4 < aNumberOfPlayers) {
			throw new IllegalArgumentException("Number of players should be between 2 and 4.");
		}
		for (int i = 0; i < aNumberOfPlayers; i++) {
			game.addPlayer(new Player("Player " + (i + 1), game));
		}
		game.setCurrentPlayer(game.getPlayer(0));
		ScrabbleScoreboardApplication.setScoreboard(game);
		return game;
	}

	public static int getCurrentPlayer(){
    	Game game = ScrabbleScoreboardApplication.getScoreboard();
    	Player player = ScrabbleScoreboardApplication.getScoreboard().getCurrentPlayer();
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			if (player.getName().equals(game.getPlayer(i).getName())){
				return i;
			}
		}
		return -1;
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
        Player player = ScrabbleScoreboardApplication.getScoreboard().getCurrentPlayer();
        player.setCurrentWord(new Word(word, score));
        return score;		
	}
	
    public static int doubleLetter (Character c){
		Game game = ScrabbleScoreboardApplication.getScoreboard();
		Word word = game.getCurrentPlayer().getCurrentWord();
		int score = word.getPoints();
		int extraMultiplier = 1;
		for (Bonus bonus : word.getBonuses()) {
			switch (bonus.getOptionName()){
				case ("Double Word"):
					extraMultiplier *= 2;
					break;
				case ("Triple Word"):
					extraMultiplier *= 3;
					break;
				default:
					extraMultiplier *= 1;
					break;
			}
		}
    	Bonus doubleLetter = new Bonus("Double Letter", 2);
    	word.addBonus(doubleLetter);
        score += extraMultiplier * getLetterScore(c);
        word.setPoints(score);
        return score;	
    }
    
    public static int tripleLetter (Character c){
		Game game = ScrabbleScoreboardApplication.getScoreboard();
		Word word = game.getCurrentPlayer().getCurrentWord();
		int score = word.getPoints();
		int extraMultiplier = 1;
		for (Bonus bonus : word.getBonuses()) {
			switch (bonus.getOptionName()){
				case ("Double Word"):
					extraMultiplier *= 2;
					break;
				case ("Triple Word"):
					extraMultiplier *= 3;
					break;
				default:
					extraMultiplier *= 1;
					break;
			}
		}
    	Bonus tripleLetter = new Bonus("Triple Letter", 3);
    	word.addBonus(tripleLetter);
        score += 2 * extraMultiplier * getLetterScore(c);
        word.setPoints(score);
        return score;	
    }
    
    public static int doubleWord (){
		Game game = ScrabbleScoreboardApplication.getScoreboard();
		Word word = game.getCurrentPlayer().getCurrentWord();
		int score = word.getPoints();
    	Bonus doubleWord = new Bonus("Double Word", 2);
    	word.addBonus(doubleWord);
    	score = score * 2;
        word.setPoints(score);
        return score;	
    }
    
    public static int tripleWord (){
    	Game game = ScrabbleScoreboardApplication.getScoreboard();
    	Word word = game.getCurrentPlayer().getCurrentWord();
    	int score = word.getPoints();
    	Bonus tripleWord = new Bonus("Triple Word", 3);
    	word.addBonus(tripleWord);
		score = score * 3;
		word.setPoints(score);
        return score;	
    }

	public static int confirmWordAndAddAnother () {
		Game game = ScrabbleScoreboardApplication.getScoreboard();
		Player player = game.getCurrentPlayer();
		player.addWord(player.getCurrentWord());
		int score = player.getCurrentWord().getPoints();
		player.setCurrentWord(null);
		player.setPoints(player.getPoints() + score);
		return player.getPoints();
	}

	public static int confirmWord () {
		Game game = ScrabbleScoreboardApplication.getScoreboard();
		Player player = game.getCurrentPlayer();
		player.addWord(player.getCurrentWord());
		int score = player.getCurrentWord().getPoints();
		player.setCurrentWord(null);
		player.setPoints(player.getPoints() + score);
		setNextPlayer(game);
		return player.getPoints();
	}

	public static int addNegativeLettersToScore(int negativeScore, int playerIndex) {
		Game game = ScrabbleScoreboardApplication.getScoreboard();
		Player player = game.getPlayer(playerIndex);
		player.setPoints(player.getPoints() + negativeScore);
		return player.getPoints();
	}

	public static int getGameWinner() {
		Game game = ScrabbleScoreboardApplication.getScoreboard();
		int[] score = new int[game.getNumberOfPlayers()];
		for (int i = 0; i < score.length; i++) {
			score[i] = game.getPlayer(i).getPoints();
		}
		int gameWinner = -1;
		int[] ranking = new int[score.length];
		for (int i = 0; i < score.length; i++) {
			ranking[i] = 1;
			for (int value : score) {
				if (score[i] < value) {
					ranking[i] += 1;
				}
			}
			game.getPlayer(i).setCurrentRanking(ranking[i]);
			if (ranking[i] == 1){
				gameWinner = i;
			}
		}
		return gameWinner;
	}

	public static boolean isWordValid(int challengerIndex) throws Exception {
    	Game game = ScrabbleScoreboardApplication.getScoreboard();
    	String word = game.getCurrentPlayer().getCurrentWord().getSpelling();
		boolean result = URLReader.checkValidWord(word);
    	if (!result){
			setNextPlayer(game);
		}
    	else {
    		game.getPlayer(challengerIndex).setInvalidChallenge(true);
		}
    	return result;
	}

	private static void setNextPlayer(Game game) {
		Player player = game.getCurrentPlayer();
		int turn = 0;
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			if (player.getName().equals(game.getPlayer(i).getName())) {
				turn = (i+1) % game.getNumberOfPlayers();
				while (game.getPlayer(turn).getInvalidChallenge()){
					game.getPlayer(turn).setInvalidChallenge(false);
					turn = (turn+1) % game.getNumberOfPlayers();
				}
				break;
			}
		}
		game.setCurrentPlayer(game.getPlayer(turn));
	}

	public static int removeNegativeLetterScore() {
    	Game game = ScrabbleScoreboardApplication.getScoreboard();
    	Player player = game.getCurrentPlayer();
    	String letters = player.getCurrentWord().getSpelling();
    	int negativeScore = player.getCurrentWord().getPoints();
		RemainingLetters remainingLetters = new RemainingLetters(letters, negativeScore);
		player.setRemainingLetters(remainingLetters);
		player.setPoints(player.getPoints() - negativeScore);
		player.setCurrentWord(null);
		setNextPlayer(game);
		return player.getPoints();
	}
}
