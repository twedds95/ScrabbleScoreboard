package ca.scrabblescoreboard.view;

import ca.scrabblescoreboard.ScrabbleScoreboardApplication;
import ca.scrabblescoreboard.controller.Controller;
import ca.scrabblescoreboard.model.Game;
import ca.scrabblescoreboard.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OptionsPanel extends JPanel {
    private JButton JBgameFinished = new JButton("<html><p style =\"text-align: center\">Finish Game (Player Finished)</p></html>"),
                    JBgameFinishedNoMoves = new JButton("<html><p style =\"text-align: center\">Finish Game (No Moves)</p></html>"),
                    JBnewGame = new JButton("New Game"),
                    JBreset = new JButton("Reset");

    private JButton JBconfirm = new JButton("Confirm");

    private JLabel JLwinningMessage = new JLabel();
    private int finalTurn = 1;
    private int finishingPlayer;

    public OptionsPanel(SpringLayout optionsLayout) {
        super(optionsLayout);
        Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
        optionsLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBreset,
                scoreboard.getFrameWidth() / 4,
                SpringLayout.WEST, this);
        optionsLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBgameFinished,
                scoreboard.getFrameWidth() * 2 / 4,
                SpringLayout.WEST, this);
        optionsLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBgameFinishedNoMoves,
                scoreboard.getFrameWidth() * 3 / 4,
                SpringLayout.WEST, this);
        optionsLayout.putConstraint(SpringLayout.NORTH, JBreset,
                40,
                SpringLayout.NORTH, this);
        optionsLayout.putConstraint(SpringLayout.NORTH, JBgameFinished,
                40,
                SpringLayout.NORTH, this);
        optionsLayout.putConstraint(SpringLayout.NORTH, JBgameFinishedNoMoves,
                40,
                SpringLayout.NORTH, this);
        JBreset.setPreferredSize(new Dimension(120, 50));
        JBgameFinished.setPreferredSize(new Dimension(150, 50));
        JBgameFinishedNoMoves.setPreferredSize(new Dimension(130, 50));

        JBreset.setVisible(true);
        JBgameFinished.setVisible(true);
        JBgameFinishedNoMoves.setVisible(true);
        JBreset.addActionListener(resetAction());
        JBgameFinished.addActionListener(gameFinishedAction());
        JBgameFinishedNoMoves.addActionListener(gameFinishedAction());

        this.add(JBreset);
        this.add(JBgameFinished);
        this.add(JBgameFinishedNoMoves);
    }

    private ActionListener resetAction() {
        return e -> {
            Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
            int numPlayers = ScrabbleScoreboardApplication.getCurrentGame().getNumberOfPlayers();
            ScrabbleScoreboardApplication.getCurrentGame().delete();
            Controller.newGame(numPlayers);

            scoreboard.getPlayersPanel().setVisible(false);
            scoreboard.getWordPanel().setVisible(false);
            scoreboard.getOptionsPanel().setVisible(false);

            scoreboard.remove(scoreboard.getPlayersPanel());
            scoreboard.remove(scoreboard.getWordPanel());
            scoreboard.remove(scoreboard.getOptionsPanel());

            initializeNewGame(scoreboard);
        };
    }

    static void initializeNewGame(Scoreboard scoreboard) {
        scoreboard.setPlayersPanel(new PlayersPanel(new SpringLayout()));
        scoreboard.setOptionsPanel(new OptionsPanel(new SpringLayout()));
        scoreboard.setWordPanel(new WordPanel(new SpringLayout()));
        scoreboard.add(scoreboard.getPlayersPanel());
        scoreboard.add(scoreboard.getWordPanel());
        scoreboard.add(scoreboard.getOptionsPanel());
    }

    private ActionListener gameFinishedAction() {
        return e -> {
            if (e.getSource()==JBgameFinishedNoMoves){
                JBconfirm.addActionListener(confirmActionNoMoves());
            }
            else  {
                JBconfirm.addActionListener(confirmAction());
            }
            Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
            PlayersPanel playersPanel = scoreboard.getPlayersPanel();
            WordPanel wordPanel = scoreboard.getWordPanel();
            OptionsPanel optionsPanel = scoreboard.getOptionsPanel();

            for (ActionListener listener : playersPanel.getJTword().getActionListeners()) {
                playersPanel.getJTword().removeActionListener(listener);
            }
            for (Component component : wordPanel.getComponents()) {
                component.setVisible(false);
            }
            for (Component component : optionsPanel.getComponents()) {
                component.setVisible(false);
            }

            int numPlayer = ScrabbleScoreboardApplication.getCurrentGame().getNumberOfPlayers();
            playersPanel.getJTword().setText("Enter remaining Letters");
            playersPanel.getJTword().addActionListener(remainingLettersAction());
            finishingPlayer = (Controller.getCurrentPlayer() + numPlayer -1) % numPlayer;
            SpringLayout wordLayout = (SpringLayout) wordPanel.getLayout();
            wordLayout.putConstraint(SpringLayout.WEST, JBconfirm, 20, SpringLayout.EAST, wordPanel.getJLplayedScore());
            wordLayout.putConstraint(SpringLayout.VERTICAL_CENTER, JBconfirm, 0, SpringLayout.VERTICAL_CENTER, wordPanel.getJLplayedScore());
            JBconfirm.setVisible(false);
            playersPanel.getJTword().requestFocusInWindow();
            wordPanel.add(JBconfirm);
            JBnewGame.addActionListener(newGameAction());
        };
    }

    private ActionListener newGameAction() {
        return arg0 -> {
            Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
            scoreboard.getPlayersPanel().setVisible(false);
            scoreboard.getWordPanel().setVisible(false);
            scoreboard.getOptionsPanel().setVisible(false);
            scoreboard.remove(scoreboard.getPlayersPanel());
            scoreboard.remove(scoreboard.getWordPanel());
            scoreboard.remove(scoreboard.getOptionsPanel());
            scoreboard.newGame();
        };
    }

    private ActionListener confirmAction() {
        return e -> {
            Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
            PlayersPanel playersPanel = scoreboard.getPlayersPanel();
            playersPanel.getJTword().setText("Enter remaining Letters:");
            Game game = ScrabbleScoreboardApplication.getCurrentGame();
            int negativeScore = game.getCurrentPlayer().getCurrentWord().getPoints();
            int negativePlayer = Controller.getCurrentPlayer();
            int newScore = Controller.removeNegativeLetterScore();
            Controller.addNegativeLettersToScore(negativeScore, finishingPlayer);
            playersPanel.getJLscores()[negativePlayer].setText(Integer.toString(newScore));
            negativePlayer = Controller.getCurrentPlayer();
            playersPanel.getJTword().setBackground(Scoreboard.getPLAYERCOLOR(negativePlayer));
            negativeScore(finishingPlayer);
            int numPlayers = game.getNumberOfPlayers();
            if (finalTurn == numPlayers) {
                gameFinishedFinalScreen();
            }
        };
    }

    private ActionListener confirmActionNoMoves() {
        return e -> {
            PlayersPanel playersPanel = ScrabbleScoreboardApplication.getCurrentScoreboard().getPlayersPanel();
            playersPanel.getJTword().setText("Enter remaining Letters:");
            int negativePlayer = Controller.getCurrentPlayer();
            Controller.removeNegativeLetterScore();
            negativeScore(negativePlayer);
            negativePlayer = Controller.getCurrentPlayer();
            playersPanel.getJTword().setBackground(Scoreboard.getPLAYERCOLOR(negativePlayer));
            int numPlayers = ScrabbleScoreboardApplication.getCurrentGame().getNumberOfPlayers();
            if (finalTurn == numPlayers+1) {
                gameFinishedFinalScreen();
            }
        };
    }

    private ActionListener remainingLettersAction() {
        return e -> {
            Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
            PlayersPanel playersPanel = scoreboard.getPlayersPanel();
            WordPanel wordPanel = scoreboard.getWordPanel();
            String playedWord = playersPanel.getJTword().getText();
            try {
                JBgameFinished.setVisible(false);
                JBreset.setVisible(false);
                wordPanel.calculateScore(playedWord, wordPanel.getJBtiles());
                playersPanel.getJTword().setText("Modify Remaining Letters:");
                JBconfirm.setVisible(true);
                playersPanel.getJTword().requestFocusInWindow();
            } catch (Exception ArrayIndexOutOfBoundsException) {
                playersPanel.getJTword().setText("Illegal Letter. Try Again.");
                for (int i = 0; i < playedWord.length(); i++) {
                    wordPanel.getJBtiles()[i].setVisible(false);
                }
            }
        };
    }

    private void gameFinishedFinalScreen() {
        Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
        PlayersPanel playersPanel =scoreboard.getPlayersPanel();
        playersPanel.getJTword().setText("Game Finished");
        playersPanel.getJTword().setBackground(Scoreboard.getPLAYERCOLOR(Controller.getGameWinner()));
        playersPanel.getJTword().setEditable(false);
        JBconfirm.setVisible(false);
        scoreboard.getWordPanel().remove(JBconfirm);
        JBnewGame.setPreferredSize(new Dimension(160, 50));
        OptionsPanel JPoptions = scoreboard.getOptionsPanel();
        SpringLayout optionsLayout = (SpringLayout) JPoptions.getLayout();
        optionsLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBnewGame, 0, SpringLayout.HORIZONTAL_CENTER, JPoptions);
        optionsLayout.putConstraint(SpringLayout.NORTH, JBnewGame, 0, SpringLayout.NORTH, JPoptions);
        JBnewGame.setVisible(true);
        JPoptions.add(JBnewGame);
        Player winningPlayer = ScrabbleScoreboardApplication.getCurrentGame().getPlayer(Controller.getGameWinner());
        JLwinningMessage.setText("Congratulations " + winningPlayer.getName() + "!");
        WordPanel JPword = scoreboard.getWordPanel();
        SpringLayout wordLayout = (SpringLayout) JPword.getLayout();
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLwinningMessage, 0, SpringLayout.HORIZONTAL_CENTER, JPword);
        wordLayout.putConstraint(SpringLayout.VERTICAL_CENTER, JLwinningMessage, 0, SpringLayout.VERTICAL_CENTER, JPword);
        JLwinningMessage.setFont(Scoreboard.getScrabbleFont());
        JBnewGame.requestFocusInWindow();
        JPword.add(JLwinningMessage);
    }

    private void negativeScore(int negativePlayer) {
        Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
        PlayersPanel playersPanel = scoreboard.getPlayersPanel();
        WordPanel wordPanel = scoreboard.getWordPanel();
        int score = ScrabbleScoreboardApplication.getCurrentGame().getPlayer(negativePlayer).getPoints();
        playersPanel.getJLscores()[negativePlayer].setText(Integer.toString(score));
        finalTurn++;
        JBconfirm.setVisible(false);
        for (int i = 0; i < 7; i++) {
            wordPanel.getJBtiles()[i].setVisible(false);
            wordPanel.getJLplayedScore().setVisible(false);
        }
        playersPanel.getJTword().requestFocusInWindow();
    }



}
