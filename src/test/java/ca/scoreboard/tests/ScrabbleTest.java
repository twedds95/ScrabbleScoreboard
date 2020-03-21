
package ca.scoreboard.tests;

import ca.scrabblescoreboard.controller.Controller;
import ca.scrabblescoreboard.model.Game;
import ca.scrabblescoreboard.model.Player;
import ca.scrabblescoreboard.model.Word;
import org.junit.Test;

import static org.junit.Assert.*;


public class ScrabbleTest {


    private final String VALID_SPELLING = "apple";
    private final Word VALID_WORD = new Word(VALID_SPELLING, 9);


    private final String INVALID_SPELLING = "zzjzj";
    private final Word INVALID_WORD = new Word(INVALID_SPELLING, 46);


    private String error = "";

    @Test
    public void challengeAValidWord(){
        final int NUMBER_PLAYERS = 4;
        final int CURRENT_PLAYER_INDEX = 3;
        final int CHALLENGER_PLAYER_INDEX = 2;

        Game game = Controller.newGame(NUMBER_PLAYERS);
        Player player = game.getPlayer(CURRENT_PLAYER_INDEX);
        game.setCurrentPlayer(player);
        player.setCurrentWord(VALID_WORD);

        try {
            Controller.isWordValid(CHALLENGER_PLAYER_INDEX);
        }catch (Exception e){
            error = e.getMessage();
        }

        assertEquals(game.getCurrentPlayer().getColor(), game.getPlayer(CURRENT_PLAYER_INDEX).getColor());
        assertEquals(game.getPlayer(CHALLENGER_PLAYER_INDEX).getInvalidChallenge(), true);
    }

    @Test
    public void challengeAnInvalidWord(){
        final int NUMBER_PLAYERS = 4;
        final int CURRENT_PLAYER_INDEX = 3;
        final int NEXT_PLAYER_INDEX = 0;
        final int CHALLENGER_PLAYER_INDEX = 2;

        Game game = Controller.newGame(NUMBER_PLAYERS);
        Player player = game.getPlayer(CURRENT_PLAYER_INDEX);
        game.setCurrentPlayer(player);
        player.setCurrentWord(INVALID_WORD);

        try {
            Controller.isWordValid(CHALLENGER_PLAYER_INDEX);
        }catch (Exception e){
            error = e.getMessage();
        }

        assertEquals(game.getCurrentPlayer().getColor(), game.getPlayer(NEXT_PLAYER_INDEX).getColor());
        assertEquals(game.getPlayer(CHALLENGER_PLAYER_INDEX).getInvalidChallenge(), false);
    }

    @Test
    public void skipPlayerAfterAnIncorrectChallenge(){
        final int NUMBER_PLAYERS = 3;
        final int CURRENT_PLAYER_INDEX = 2;
        final int PLAYER_TO_SKIP = 0;
        final int NEXT_PLAYER_INDEX = 1;

        Game game = Controller.newGame(NUMBER_PLAYERS);
        Player player = game.getPlayer(CURRENT_PLAYER_INDEX);
        game.setCurrentPlayer(player);
        player.setCurrentWord(VALID_WORD);
        game.getPlayer(PLAYER_TO_SKIP).setInvalidChallenge(true);
        Controller.confirmWord();
        assertEquals(game.getPlayer(NEXT_PLAYER_INDEX).getColor(), game.getCurrentPlayer().getColor());
    }

    @Test
    public void skipPlayerMultipleTurnsAway(){
        final int NUMBER_PLAYERS = 3;
        final int CURRENT_PLAYER_INDEX = 1;
        final int SECOND_PLAYER = 2;
        final int PLAYER_TO_SKIP = 0;
        final int NEXT_PLAYER_INDEX = 1;

        Game game = Controller.newGame(NUMBER_PLAYERS);
        Player player1 = game.getPlayer(CURRENT_PLAYER_INDEX);
        Player player2 = game.getPlayer(SECOND_PLAYER);
        game.setCurrentPlayer(player1);
        player1.setCurrentWord(VALID_WORD);
        player2.setCurrentWord(VALID_WORD);
        game.getPlayer(PLAYER_TO_SKIP).setInvalidChallenge(true);
        Controller.confirmWord();
        assertEquals(game.getPlayer(SECOND_PLAYER).getColor(), game.getCurrentPlayer().getColor());
        Controller.confirmWord();
        assertEquals(game.getPlayer(NEXT_PLAYER_INDEX).getColor(), game.getCurrentPlayer().getColor());
    }
    
}
