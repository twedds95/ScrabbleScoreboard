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
	
	private static Game scoreboard;
   
    public static void setScoreboard(Game scoreboard) {
		ScrabbleScoreboardApplication.scoreboard = scoreboard;
	}

	public static void main(String[] args) {
    	// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Scoreboard().setVisible(true);
            }
        });
       
    }

	public static Game getScoreboard() {
		return scoreboard;
	}
    
}
