/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.scrabblescoreboard;

import ca.scrabblescoreboard.model.Game;
import ca.scrabblescoreboard.view.Scoreboard;

/**
 *
 * @author Patrick Tweddell
 */
public class ScrabbleScoreboardApplication {
	
	private static Game currentGame;

    public static Scoreboard getCurrentScoreboard() {
        return currentScoreboard;
    }

    public static void setCurrentScoreboard(Scoreboard currentScoreboard) {
        ScrabbleScoreboardApplication.currentScoreboard = currentScoreboard;
    }

    private static Scoreboard currentScoreboard;
   
    public static void setCurrentGame(Game currentGame) {
		ScrabbleScoreboardApplication.currentGame = currentGame;
	}

	public static void main(String[] args) {
    	// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                currentScoreboard = new Scoreboard();
                currentScoreboard.setVisible(true);
            }
        });
       
    }

	public static Game getCurrentGame() {
		return currentGame;
	}
    
}
