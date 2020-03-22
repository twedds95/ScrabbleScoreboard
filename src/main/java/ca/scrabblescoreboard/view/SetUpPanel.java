package ca.scrabblescoreboard.view;

import ca.scrabblescoreboard.ScrabbleScoreboardApplication;
import ca.scrabblescoreboard.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SetUpPanel extends JPanel {

    private JLabel JLintroMessage = new JLabel("<html><p style =\"text-align: center\">Welcome to Scrabble Scoreboard! Select the number of Players:</p></html>");
    private String[] selectNumPlayers = {"2", "3", "4"};
    private JList<String> JLnumpLayers = new JList<>(selectNumPlayers);
    private JButton JBstartGame = new JButton("Start Game");

    public SetUpPanel(SpringLayout setupLayout) {
        super(setupLayout);
        JLintroMessage.setPreferredSize(new Dimension(400, 100));
        setupLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLintroMessage, 0, SpringLayout.HORIZONTAL_CENTER, this);
        setupLayout.putConstraint(SpringLayout.NORTH, JLintroMessage, 30, SpringLayout.NORTH, this);
        JLintroMessage.setFont(Scoreboard.getScrabbleFont());
        JLintroMessage.setVisible(true);
        this.add(JLintroMessage);

        setupLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLnumpLayers, 0, SpringLayout.HORIZONTAL_CENTER, this);
        setupLayout.putConstraint(SpringLayout.NORTH, JLnumpLayers, 30, SpringLayout.SOUTH, JLintroMessage);
        JLnumpLayers.setFixedCellWidth(50);
        JLnumpLayers.setFixedCellHeight(50);
        JLnumpLayers.setFont(Scoreboard.getScrabbleFont());
        JLnumpLayers.setCursor(Scoreboard.getScrabbleCursor());
        JLnumpLayers.setVisible(true);
        this.add(JLnumpLayers);

        JBstartGame.setPreferredSize(new Dimension(200, 60));
        setupLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBstartGame, 0, SpringLayout.HORIZONTAL_CENTER, this);
        setupLayout.putConstraint(SpringLayout.NORTH, JBstartGame, 30, SpringLayout.SOUTH, JLnumpLayers);
        JBstartGame.setFont(Scoreboard.getScrabbleFont());
        JBstartGame.setCursor(Scoreboard.getScrabbleCursor());
        JBstartGame.setVisible(true);
        this.add(JBstartGame);
        JBstartGame.addActionListener(startGameAction());
        this.setVisible(true);
    }

    private ActionListener startGameAction() {
        return arg0 -> {
            int index = JLnumpLayers.getSelectedIndex();
            Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
            if (index == 0 || index == 1 || index == 2) {
                Controller.newGame(index + 2);
                this.setVisible(false);
                scoreboard.remove(this);
                OptionsPanel.initializeNewGame(scoreboard);
            }
        };
    }

}
