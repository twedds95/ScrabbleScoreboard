/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.scrabblescoreboard.view;

import ca.scrabblescoreboard.ScrabbleScoreboardApplication;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author user
 */
public class Scoreboard extends JFrame {

    private static Font scrabbleFont = new Font("gothic standard", Font.BOLD, 24);
    private static Cursor hoverCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    private static final Color PLAYER1COLOR = Color.RED;
    private static final Color PLAYER2COLOR = Color.CYAN;
    private static final Color PLAYER3COLOR = Color.GREEN;
    private static final Color PLAYER4COLOR = Color.YELLOW;

    private SetUpPanel setUpPanel;
    private PlayersPanel playersPanel;
    private WordPanel wordPanel;
    private OptionsPanel optionsPanel;

    public SetUpPanel getSetUpPanel() {
        return setUpPanel;
    }

    public void setSetUpPanel(SetUpPanel setUpPanel) {
        this.setUpPanel = setUpPanel;
    }

    public PlayersPanel getPlayersPanel() {
        return playersPanel;
    }

    public void setPlayersPanel(PlayersPanel playersPanel) {
        this.playersPanel = playersPanel;
    }

    public WordPanel getWordPanel() {
        return wordPanel;
    }

    public void setWordPanel(WordPanel wordPanel) {
        this.wordPanel = wordPanel;
    }

    public OptionsPanel getOptionsPanel() {
        return optionsPanel;
    }

    public void setOptionsPanel(OptionsPanel optionsPanel) {
        this.optionsPanel = optionsPanel;
    }



    private JPanel JPoptions = new JPanel(new SpringLayout());

    public Scoreboard() {
        this.setTitle("Scrabble Scoreboard");
        this.setResizable(false);
        int height = 900;
        int width = 800;
        this.setBounds(200, 0, width, height);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(0, 1));
        this.setVisible(true);
        ScrabbleScoreboardApplication.setCurrentScoreboard(this);
        newGame();
    }

    public void newGame() {
       SetUpPanel setUpPanel = new SetUpPanel(new SpringLayout());
       this.add(setUpPanel);
       setSetUpPanel(setUpPanel);
    }

    public static Color getPLAYERCOLOR(int num) {
        switch (num) {
            case 0:
                return PLAYER1COLOR;
            case 1:
                return PLAYER2COLOR;
            case 2:
                return PLAYER3COLOR;
            case 3:
                return PLAYER4COLOR;
            default:
                return Color.WHITE;
        }
    }

    public static Cursor getScrabbleCursor() {
        return hoverCursor;
    }

    public static Font getScrabbleFont() {
        return scrabbleFont;
    }

    public int getFrameWidth() {
        int w = this.getWidth();
        return w;
    }

}
