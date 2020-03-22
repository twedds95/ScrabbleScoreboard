package ca.scrabblescoreboard.view;

import ca.scrabblescoreboard.ScrabbleScoreboardApplication;
import ca.scrabblescoreboard.controller.Controller;
import ca.scrabblescoreboard.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WordPanel extends JPanel {

    private JButton JBskipTurn = new JButton("<html><p style=\"text-align:center\"> Change Letters (Skip Turn) </p> </html>");
    private JLabel JLmultiplier = new JLabel("", JLabel.CENTER);
    private JButton[] JBtiles = new JButton[15];
    private JLabel JLplayedScore = new JLabel("", JLabel.CENTER);

    private ImageIcon doubleWordImage = new ImageIcon("src\\main\\resources\\doubleLetterScore.png");
    private ImageIcon tripleWordImage = new ImageIcon("src\\main\\resources\\tripleWordScore.png");
    private ImageIcon doubleLetterImage = new ImageIcon("src\\main\\resources\\doubleLetterScore.png");
    private ImageIcon tripleLetterImage = new ImageIcon("src\\main\\resources\\tripleLetterScore.png");
    private ImageIcon blankTileImage = new ImageIcon("src\\main\\resources\\blankTile_Scrabble.png");

    private Image doubleWordImg = doubleWordImage.getImage();
    private ImageIcon doubleWordIcon = new ImageIcon(doubleWordImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ));
    private Image tripleWordImg = tripleWordImage.getImage();
    private ImageIcon tripleWordIcon = new ImageIcon(tripleWordImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ));
    private Image doubleLetterImg = doubleLetterImage.getImage();
    private ImageIcon doubleLetterIcon = new ImageIcon(doubleLetterImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ));
    private Image tripleLetterImg = tripleLetterImage.getImage();
    private ImageIcon tripleLetterIcon = new ImageIcon(tripleLetterImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ));
    private Image blankTileImg = blankTileImage.getImage();
    private ImageIcon blankTileIcon = new ImageIcon(blankTileImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ));

    private JLabel challengeResultLabel = new JLabel("Challenge Result:");
    private JButton JBdoubleWord = new JButton(doubleWordIcon),
            JBtripleWord = new JButton(tripleWordIcon),
            JBbonusUndo = new JButton("Undo"),
            JBendTurn = new JButton("End Turn"),
            JBaddWord = new JButton("Add Another Word");

    private JToggleButton JTBdoubleLetter = new JToggleButton(doubleLetterIcon),
            JTBtripleLetter = new JToggleButton(tripleLetterIcon),
             blankLetter = new JToggleButton(blankTileIcon);

    private int playedScore = 0,
            letterMultiplier = 0,
            multiplier = 1;

    private SpringLayout wordLayout;

    private String playedWord = "";

    public WordPanel(SpringLayout wordLayout) {
        super(wordLayout);
        this.wordLayout = wordLayout;
        Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();

        for (int i = 0; i < 15; i++) {
            JBtiles[i] = new JButton();
        }

        JBskipTurn.setPreferredSize(new Dimension(200, 50));
        wordLayout.putConstraint(SpringLayout.NORTH, JBskipTurn, 50, SpringLayout.NORTH, this);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBskipTurn, 0, SpringLayout.HORIZONTAL_CENTER, this);
        JBskipTurn.addActionListener(endTurnAction());
        JBskipTurn.setVisible(true);
        this.add(JBskipTurn);

        //Setup this panel, so only need to set visible when Word is entered
        JLplayedScore.setBackground(Color.white);
        JLplayedScore.setFont(Scoreboard.getScrabbleFont());
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLplayedScore, scoreboard.getFrameWidth() / 2 - 75, SpringLayout.WEST, this);
        wordLayout.putConstraint(SpringLayout.NORTH, JLplayedScore, 135, SpringLayout.NORTH, this);
        JLplayedScore.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        JLplayedScore.setForeground(Color.black);
        JLplayedScore.setPreferredSize(new Dimension(50, 50));
        JLplayedScore.setVisible(false);

        JLmultiplier.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        JLmultiplier.setForeground(Color.black);
        JLmultiplier.setVisible(false);
        JLmultiplier.setPreferredSize(new Dimension(50, 50));
        JTBdoubleLetter.setPreferredSize(new Dimension(50, 50));
        JTBdoubleLetter.setSelected(false);
        JTBtripleLetter.setSelected(false);
        blankLetter.setSelected(false);
        JTBtripleLetter.setPreferredSize(new Dimension(50, 50));
        JBdoubleWord.setPreferredSize(new Dimension(50, 50));
        JBtripleWord.setPreferredSize(new Dimension(50, 50));
        blankLetter.setPreferredSize(new Dimension(50, 50));
        JBbonusUndo.setPreferredSize(new Dimension(75, 50));
        JBaddWord.setPreferredSize(new Dimension(150, 50));
        JBendTurn.setPreferredSize(new Dimension(150, 50));
        challengeResultLabel.setPreferredSize(new Dimension(200, 50));
        challengeResultLabel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        challengeResultLabel.setForeground(Color.black);
        challengeResultLabel.setBackground(Color.white);
        challengeResultLabel.setHorizontalAlignment(JTextField.CENTER);


        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLmultiplier, scoreboard.getFrameWidth() * 7 / 8, SpringLayout.WEST, this);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JTBdoubleLetter, scoreboard.getFrameWidth() / 8, SpringLayout.WEST, this);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JTBtripleLetter, scoreboard.getFrameWidth() * 2 / 8, SpringLayout.WEST, this);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBdoubleWord, scoreboard.getFrameWidth() * 4 / 8, SpringLayout.WEST, this);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBtripleWord, scoreboard.getFrameWidth() * 5 / 8, SpringLayout.WEST, this);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, blankLetter, scoreboard.getFrameWidth() * 3 / 8, SpringLayout.WEST, this);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBbonusUndo, scoreboard.getFrameWidth() * 6 / 8, SpringLayout.WEST, this);
        wordLayout.putConstraint(SpringLayout.WEST, JBendTurn, 10, SpringLayout.EAST, JLplayedScore);
        wordLayout.putConstraint(SpringLayout.WEST, JBaddWord, 10, SpringLayout.EAST, JBendTurn);
        wordLayout.putConstraint(SpringLayout.WEST, JBendTurn, 10, SpringLayout.EAST, JLplayedScore);
        wordLayout.putConstraint(SpringLayout.VERTICAL_CENTER, JLmultiplier, 0, SpringLayout.VERTICAL_CENTER, JBdoubleWord);
        wordLayout.putConstraint(SpringLayout.NORTH, JTBdoubleLetter, 10, SpringLayout.NORTH, this);
        wordLayout.putConstraint(SpringLayout.NORTH, JTBtripleLetter, 10, SpringLayout.NORTH, this);
        wordLayout.putConstraint(SpringLayout.NORTH, JBdoubleWord, 10, SpringLayout.NORTH, this);
        wordLayout.putConstraint(SpringLayout.NORTH, JBtripleWord, 10, SpringLayout.NORTH, this);
        wordLayout.putConstraint(SpringLayout.NORTH, blankLetter, 10, SpringLayout.NORTH, this);
        wordLayout.putConstraint(SpringLayout.NORTH, JBbonusUndo, 10, SpringLayout.NORTH, this);
        wordLayout.putConstraint(SpringLayout.NORTH, JBaddWord, 0, SpringLayout.NORTH, JLplayedScore);
        wordLayout.putConstraint(SpringLayout.NORTH, JBendTurn, 0, SpringLayout.NORTH, JLplayedScore);
        wordLayout.putConstraint(SpringLayout.NORTH, challengeResultLabel, 0, SpringLayout.NORTH, JLplayedScore);
        wordLayout.putConstraint(SpringLayout.EAST, challengeResultLabel, -10, SpringLayout.WEST, JLplayedScore);
        JTBdoubleLetter.setCursor(Scoreboard.getScrabbleCursor());
        JTBtripleLetter.setCursor(Scoreboard.getScrabbleCursor());
        JBdoubleWord.setCursor(Scoreboard.getScrabbleCursor());
        JBtripleWord.setCursor(Scoreboard.getScrabbleCursor());
        blankLetter.setCursor(Scoreboard.getScrabbleCursor());
        JBbonusUndo.setCursor(Scoreboard.getScrabbleCursor());
        JBaddWord.setCursor(Scoreboard.getScrabbleCursor());
        JBendTurn.setCursor(Scoreboard.getScrabbleCursor());
        JTBdoubleLetter.setVisible(false);
        JTBtripleLetter.setVisible(false);
        JBdoubleWord.setVisible(false);
        JBtripleWord.setVisible(false);
        blankLetter.setVisible(false);
        JBbonusUndo.setVisible(false);
        JBaddWord.setVisible(false);
        JBendTurn.setVisible(false);
        JLmultiplier.setVisible(false);
        challengeResultLabel.setVisible(false);
        ButtonGroup BGletterBonus = new ButtonGroup();
        BGletterBonus.add(JTBdoubleLetter);
        BGletterBonus.add(JTBtripleLetter);
        BGletterBonus.add(blankLetter);
        blankLetter.addActionListener(letterBonusListener());
        JTBdoubleLetter.addActionListener(letterBonusListener());
        JTBtripleLetter.addActionListener(letterBonusListener());
        JBaddWord.addActionListener(addWordAction());
        for (int l = 0; l < 15; l++) {
            int index = l;
            JBtiles[l].addActionListener(
                    e -> {
                        switch (letterMultiplier){
                            case (1):
                                playedScore = Controller.doubleLetter(JBtiles[index].getText().toCharArray()[0]);
                                break;
                            case (2):
                                playedScore = Controller.tripleLetter(JBtiles[index].getText().toCharArray()[0]);
                                break;
                            case (-1):
                                playedScore = Controller.blankTile(JBtiles[index].getText().toCharArray()[0]);
                                break;
                            default:
                                break;
                        }
                        JLplayedScore.setText(Integer.toString(playedScore));
                    }
            );
        }
        JBbonusUndo.addActionListener(getUndoAction());
        JBdoubleWord.addActionListener(doubleWordAction());
        JBtripleWord.addActionListener(tripleWordAction());
        JBendTurn.addActionListener(endTurnAction());

        this.add(JLplayedScore);
        this.add(JTBdoubleLetter);
        this.add(JTBtripleLetter);
        this.add(JBdoubleWord);
        this.add(JBtripleWord);
        this.add(blankLetter);
        this.add(JBbonusUndo);
        this.add(JBendTurn);
        this.add(JBaddWord);
        this.add(JLmultiplier);
        this.add(challengeResultLabel);
    }

    private ActionListener endTurnAction() {
        return e -> {
            Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
            PlayersPanel playersPanel = scoreboard.getPlayersPanel();
            if (e.getSource() == JBendTurn){
                int turn = Controller.getCurrentPlayer();
                int score = Controller.confirmWord();
                playersPanel.getJLscores()[turn].setText(Integer.toString(score));
                setScreenForNextPlayer();
            }
            else if (e.getSource() == JBskipTurn){
                Game game = ScrabbleScoreboardApplication.getCurrentGame();
                Controller.setNextPlayer(game);
                setScreenForNextPlayer();
            }
            else {
                throw new RuntimeException("not implemented yet");
            }

        };
    }


    private ActionListener addWordAction() {
        return e -> {
            Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
            PlayersPanel playersPanel = scoreboard.getPlayersPanel();
            int selectedPlayer = Controller.getCurrentPlayer();
            int score = Controller.confirmWordAndAddAnother();
            playersPanel.getJLscores()[selectedPlayer].setText(Integer.toString(score));
            playedScore = 0;
            multiplier = 1;
            playersPanel.getJTword().setText("Enter Word");
            for (Component component : this.getComponents()) {
                component.setVisible(false);
            }
            JBskipTurn.setVisible(true);
            playersPanel.getJTword().requestFocusInWindow();
        };
    }

    private ActionListener doubleWordAction() {
        return e -> {
            playedScore = Controller.doubleWord();
            JLplayedScore.setText(Integer.toString(playedScore));
            multiplier *= 2;
            JLmultiplier.setText("x " + multiplier);
            JLmultiplier.setVisible(true);
        };
    }

    private ActionListener tripleWordAction() {
        return e -> {
            playedScore = Controller.tripleWord();
            JLplayedScore.setText(Integer.toString(playedScore));
            multiplier *= 3;
            JLmultiplier.setText("x " + multiplier);
            JLmultiplier.setVisible(true);
        };
    }

    void setScreenForNextPlayer() {
        Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
        PlayersPanel playersPanel = scoreboard.getPlayersPanel();
        playersPanel.getJTword().setBackground(Scoreboard.getPLAYERCOLOR(Controller.getCurrentPlayer()));
        playedScore = 0;
        multiplier = 1;
        for (int i = 0; i < ScrabbleScoreboardApplication.getCurrentGame().getNumberOfPlayers(); i++) {
            playersPanel.getJBchallenge()[i].setVisible(false);
        }
        playersPanel.getJTword().setText("Enter Word");
        for (Component component : this.getComponents()) {
            component.setVisible(false);
        }
        JBskipTurn.setVisible(true);
        playersPanel.getJTword().requestFocusInWindow();
    }

    private ActionListener getUndoAction() {
        return e -> {
            playedScore = calculateScore(playedWord, JBtiles);
            JLplayedScore.setText(Integer.toString(playedScore));
            multiplier = 1;
            JLmultiplier.setVisible(false);
        };
    }

    private ActionListener letterBonusListener() {
        return e -> {
            if (JTBdoubleLetter.isSelected()) {
                letterMultiplier = 1;
                JTBdoubleLetter.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                JTBtripleLetter.setBorder(null);
                blankLetter.setBorder(null);
            } else if (JTBtripleLetter.isSelected()) {
                letterMultiplier = 2;
                JTBtripleLetter.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                JTBdoubleLetter.setBorder(null);
                blankLetter.setBorder(null);
            } else if (blankLetter.isSelected()) {
                letterMultiplier = -1;
                blankLetter.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                JTBdoubleLetter.setBorder(null);
                JTBtripleLetter.setBorder(null);
            } else {
                letterMultiplier = 0;
                blankLetter.setBorder(null);
                JTBdoubleLetter.setBorder(null);
                JTBtripleLetter.setBorder(null);
            }
        };
    }

    public int calculateScore(String word, JButton[] JBtile) {
        Scoreboard scoreboard = ScrabbleScoreboardApplication.getCurrentScoreboard();
        int score;
        int i = 0;
        for (char letter : word.toCharArray()) {
            JBtile[i].setText(String.valueOf(Character.toUpperCase(letter)));
            JBtile[i].setBackground(new Color(255, 255, 153));
            JBtile[i].setHorizontalAlignment(JButton.CENTER);
            JBtile[i].setFont(Scoreboard.getScrabbleFont());
            this.add(JBtile[i]);
            wordLayout.putConstraint(SpringLayout.NORTH, JBtile[i], 70, SpringLayout.NORTH, this);
            wordLayout.putConstraint(SpringLayout.WEST, JBtile[i],
                    (scoreboard.getFrameWidth() / 2 - 25 * word.length() + 50 * i),
                    SpringLayout.WEST, this);
            JBtile[i].setBorder(BorderFactory.createLineBorder(Color.gray, 2));
            JBtile[i].setForeground(Color.black);
            JBtile[i].setCursor(Scoreboard.getScrabbleCursor());
            JBtile[i].setPreferredSize(new Dimension(50, 50));
            JBtile[i].setVisible(true);
            i++;
        }

        for (int j = word.length(); j < 15; j++) {
            JBtile[j].setVisible(false);
        }
        score = Controller.playWord(word);

        JLplayedScore.setText(Integer.toString(score));
        JLplayedScore.setVisible(true);
        return score;
    }

    public void setVisibleForPlayedWord() {
        challengeResultLabel.setVisible(false);
        JBskipTurn.setVisible(false);
        JLmultiplier.setVisible(false);
        JTBdoubleLetter.setVisible(true);
        JTBtripleLetter.setVisible(true);
        JBdoubleWord.setVisible(true);
        JBtripleWord.setVisible(true);
        blankLetter.setVisible(true);
        JBbonusUndo.setVisible(true);
        JBendTurn.setVisible(true);
        JBaddWord.setVisible(true);
    }

    public JButton[] getJBtiles() {
        return JBtiles;
    }

    public JLabel getChallengeResultLabel() {
        return challengeResultLabel;
    }

    public JLabel getJLplayedScore() {
        return JLplayedScore;
    }
}
