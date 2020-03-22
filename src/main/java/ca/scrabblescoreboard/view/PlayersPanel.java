package ca.scrabblescoreboard.view;

import ca.scrabblescoreboard.ScrabbleScoreboardApplication;
import ca.scrabblescoreboard.controller.Controller;
import ca.scrabblescoreboard.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlayersPanel extends JPanel {


    private JButton[] JBchallenge = new JButton[4];
    private JTextField JTword = new JTextField("Enter word", 15);
    private JLabel[] JLscores = new JLabel[4];


    public PlayersPanel(SpringLayout playersLayout) {
        super(playersLayout);
        int numPlayers = ScrabbleScoreboardApplication.getCurrentGame().getNumberOfPlayers();
        Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
        JTname[] JTnames = new JTname[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            JTnames[i] = new JTname("Player " + Integer.toString(i + 1));
            JLscores[i] = new JLabel("0", JLabel.CENTER);
            JBchallenge[i] = new JButton("Challenge Word");
            JBchallenge[i].setCursor(Scoreboard.getScrabbleCursor());
            JBchallenge[i].addActionListener(challengeWordAction(i));
            JTnames[i].setBackground(Scoreboard.getPLAYERCOLOR(i));
            playersLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JTnames[i],
                    scoreboard.getFrameWidth() * (i + 1) / (numPlayers + 1),
                    SpringLayout.WEST, this);
            JBchallenge[i].setBackground(Scoreboard.getPLAYERCOLOR(i));
            playersLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBchallenge[i],
                    0,
                    SpringLayout.HORIZONTAL_CENTER, JTnames[i]);
            JBchallenge[i].setPreferredSize(new Dimension(150, 30));
            JLscores[i].setFont(Scoreboard.getScrabbleFont());
            JLscores[i].setBorder(BorderFactory.createLineBorder(Scoreboard.getPLAYERCOLOR(i), 3));
            JLscores[i].setPreferredSize(new Dimension(40, 40));
            JLscores[i].setOpaque(true);
            playersLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLscores[i],
                    0,
                    SpringLayout.HORIZONTAL_CENTER, JTnames[i]);
            playersLayout.putConstraint(SpringLayout.NORTH, JBchallenge[i], 10, SpringLayout.SOUTH, JLscores[i]);
            playersLayout.putConstraint(SpringLayout.NORTH, JLscores[i], 10, SpringLayout.SOUTH, JTnames[i]);
            JBchallenge[i].setVisible(false);
            this.add(JTnames[i]);
            this.add(JLscores[i]);
            this.add(JBchallenge[i]);
        }
        JTword.setBackground(scoreboard.getPLAYERCOLOR(0));
        JTword.setHorizontalAlignment(JTextField.CENTER);
        JTword.setPreferredSize(new Dimension(200, 50));
        playersLayout.putConstraint(SpringLayout.NORTH, JTword,
                10,
                SpringLayout.SOUTH, JBchallenge[0]);
        playersLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JTword,
                scoreboard.getFrameWidth() / 2,
                SpringLayout.WEST, this);
        JTword.addFocusListener(getFocusListener());
        JTword.addActionListener(wordAction());
        this.add(JTword);
        JTword.requestFocusInWindow();
    }

    private ActionListener challengeWordAction(int i) {
        return e -> {
            try {
                Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
                WordPanel wordPanel = scoreboard.getWordPanel();
                boolean result = Controller.isWordValid(i);
                wordPanel.getChallengeResultLabel().setVisible(true);
                if (!result){
                    wordPanel.setScreenForNextPlayer();
                    wordPanel.getChallengeResultLabel().setText("<html>Challenge Result:<br/>Not valid!</html>");
                }
                else {
                    wordPanel.getChallengeResultLabel().setText("<html>Challenge Result:<br/>Valid!</html>");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        };
    }

    private ActionListener wordAction() {
        return e -> {
           String playedWord = JTword.getText();
           Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
           Game game = ScrabbleScoreboardApplication.getCurrentGame();
           WordPanel wordPanel = scoreboard.getWordPanel();
            try {
               int playedScore = wordPanel.calculateScore(playedWord, scoreboard.getWordPanel().getJBtiles());
                int currentPlayer = Controller.getCurrentPlayer();
                for (int i = 0; i < game.getNumberOfPlayers(); i++) {
                    if (i != currentPlayer){
                        JBchallenge[i].setVisible(true);
                    }
                }
                JTword.setText("Modify Word");
                wordPanel.setVisibleForPlayedWord();
            } catch (Exception ArrayIndexOutOfBoundsException) {
                JTword.setText("Illegal Word. Try Again.");
                for (int i = 0; i < playedWord.length(); i++) {
                    wordPanel.getJBtiles()[i].setVisible(false);
                }
            }
        };
    }

    private FocusListener getFocusListener() {
        return new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                JTword.select(0, JTword.getText().length());
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTword.select(0, 0);
            }
        };
    }

    public JButton[] getJBchallenge() {
        return JBchallenge;
    }

    public void setJBchallenge(JButton[] JBchallenge) {
        this.JBchallenge = JBchallenge;
    }

    public JTextField getJTword() {
        return JTword;
    }

    public void setJTword(JTextField JTword) {
        this.JTword = JTword;
    }

    public JLabel[] getJLscores() {
        return JLscores;
    }

    public void setJLscores(JLabel[] JLscores) {
        this.JLscores = JLscores;
    }

}
