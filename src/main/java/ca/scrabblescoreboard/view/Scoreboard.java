/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.scrabblescoreboard.view;

import ca.scrabblescoreboard.ScrabbleScoreboardApplication;
import ca.scrabblescoreboard.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 *
 * @author user
 */
public class Scoreboard extends JFrame {

    private Font Fscrabble = new Font("gothic standard", Font.BOLD, 24);
    private Cursor hoverCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    private final Color PLAYER1COLOR = Color.RED;
    private final Color PLAYER2COLOR = Color.CYAN;
    private final Color PLAYER3COLOR = Color.GREEN;
    private final Color PLAYER4COLOR = Color.YELLOW;

    /* A = 1, B = 3, C = 3, etc. */
    private static final int[] LETTER_SCORES = {
        1, 3, 3, 2, 1, 4, 2, 4, 1, 8,
        5, 1, 3, 1, 1, 3, 10, 1, 1, 1,
        1, 4, 4, 8, 4, 10
        };

    private SpringLayout setupLayout = new SpringLayout();
    private SpringLayout playersLayout = new SpringLayout();
    private SpringLayout wordLayout = new SpringLayout();
    private SpringLayout optionsLayout = new SpringLayout();

    private JPanel JPsetup = new JPanel(setupLayout);
    private JLabel JLintroMessage = new JLabel("<html><p style =\"text-align: center\">Welcome to Scrabble Scoreboard! Select the number of Players:</p></html>");
    private String[] selectNumPlayers = {"2", "3", "4"};
    private JList<String> JLnumpLayers = new JList<>(selectNumPlayers);
    private JButton JBstartGame = new JButton("Start Game");
    private int numPlayers;

    private JPanel JPplayers = new JPanel(playersLayout);
    private JTextField[] JTnames = new JTextField[4];
    private JButton[] JBchallenge = new JButton[4];
    private JTextField JTword = new JTextField("Enter word", 15);
    private JLabel[] JLscores = new JLabel[4];
    private int[] scores = new int[4];

    private JPanel JPword = new JPanel(wordLayout);
    private JButton JBskipTurn = new JButton("<html><p style=\"text-align:center\"> Change Letters (Skip Turn) </p> </html>");
    private JLabel JLmultiplier = new JLabel("", JLabel.CENTER);
    private JButton[] JBtiles = new JButton[15];
    private JLabel JLplayedScore = new JLabel("", JLabel.CENTER);
    
    private ImageIcon doubleWordImage = new ImageIcon("src\\main\\resources\\doubleLetterScore.png");
    private ImageIcon tripleWordImage = new ImageIcon("src\\main\\resources\\tripleWordScore.png");
    private ImageIcon doubleLetterImage = new ImageIcon("src\\main\\resources\\doubleLetterScore.png");
    private ImageIcon tripleLetterImage = new ImageIcon("src\\main\\resources\\tripleLetterScore.png");
    
    private Image doubleWordImg = doubleWordImage.getImage();
    private ImageIcon doubleWordIcon = new ImageIcon(doubleWordImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ));
    private Image tripleWordImg = tripleWordImage.getImage();
    private ImageIcon tripleWordIcon = new ImageIcon(tripleWordImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ));
    private Image doubleLetterImg = doubleLetterImage.getImage();
    private ImageIcon doubleLetterIcon = new ImageIcon(doubleLetterImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ));
    private Image tripleLetterImg = tripleLetterImage.getImage();
    private ImageIcon tripleLetterIcon = new ImageIcon(tripleLetterImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ));

    private JLabel challengeResultLabel = new JLabel("Challenge Result:");
    private JButton JBdoubleWord = new JButton(doubleWordIcon),
            JBtripleWord = new JButton(tripleWordIcon),
            JBbonusUndo = new JButton("Undo"),
            JBendTurn = new JButton("End Turn"),
            JBaddWord = new JButton("Add Another Word"),
            JBgameFinished = new JButton("<html><p style =\"text-align: center\">Finish Game (Player Finished)</p></html>"),
            JBgameFinishedNoMoves = new JButton("<html><p style =\"text-align: center\">Finish Game (No Moves)</p></html>"),
            JBnewGame = new JButton("New Game"),
            JBreset = new JButton("Reset");

    private JButton JBconfirm = new JButton("Confirm");

    private JLabel JLwinningMessage = new JLabel();

    private JToggleButton JTBdoubleLetter = new JToggleButton(doubleLetterIcon),
            JTBtripleLetter = new JToggleButton(tripleLetterIcon);

    private ButtonGroup BGletterBonus = new ButtonGroup();

    private int playedScore = 0,
            letterMultiplier = 0,
            multiplier = 1,
            turn = 0,
            finalTurn = 1,
            finishingPlayer, negativePlayer, negativeScore;

    private String playedWord = "";

    private JPanel JPoptions = new JPanel(optionsLayout);

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
        NewGame();
    }

    private void NewGame() {
        JLintroMessage.setPreferredSize(new Dimension(400, 100));
        setupLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLintroMessage, 0, SpringLayout.HORIZONTAL_CENTER, JPsetup);
        setupLayout.putConstraint(SpringLayout.NORTH, JLintroMessage, 30, SpringLayout.NORTH, JPsetup);
        JLintroMessage.setFont(getFont());
        JLintroMessage.setVisible(true);
        JPsetup.add(JLintroMessage);

        setupLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLnumpLayers, 0, SpringLayout.HORIZONTAL_CENTER, JPsetup);
        setupLayout.putConstraint(SpringLayout.NORTH, JLnumpLayers, 30, SpringLayout.SOUTH, JLintroMessage);
        JLnumpLayers.setFixedCellWidth(50);
        JLnumpLayers.setFixedCellHeight(50);
        JLnumpLayers.setFont(getFont());
        JLnumpLayers.setCursor(getCursor());
        JLnumpLayers.setVisible(true);
        JPsetup.add(JLnumpLayers);

        JBstartGame.setPreferredSize(new Dimension(200, 60));
        setupLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBstartGame, 0, SpringLayout.HORIZONTAL_CENTER, JPsetup);
        setupLayout.putConstraint(SpringLayout.NORTH, JBstartGame, 30, SpringLayout.SOUTH, JLnumpLayers);
        JBstartGame.setFont(getFont());
        JBstartGame.setCursor(getCursor());
        JBstartGame.setVisible(true);
        JPsetup.add(JBstartGame);
        JBstartGame.addActionListener(startGameAction());

        JPsetup.setVisible(true);
        this.add(JPsetup);

    }

    private ActionListener startGameAction() {
        return arg0 -> {
            int index = JLnumpLayers.getSelectedIndex();
            if (index == 0 || index == 1 || index == 2) {
                numPlayers = index + 2;
                Controller.newGame(numPlayers);
                JPsetup.setVisible(false);
                this.remove(JPsetup);
                this.add(getJPplayers());
                this.add(getJPword());
                this.add(getJPoptions());
                JTword.requestFocusInWindow();
            }
        };
    }

    private ActionListener endTurnAction() {
        return e -> {
            turn = Controller.getCurrentPlayer();
            scores[turn] = Controller.confirmWord();
            JLscores[turn].setText(Integer.toString(scores[turn]));
            setScreenForNextPlayer();

        };
    }

    private ActionListener resetAction() {
        return e -> {
            turn = 0;
            JTword.setBackground(getPLAYERCOLOR(0));
            JTword.addActionListener(wordAction());
            JTBdoubleLetter.setVisible(false);
            JTBtripleLetter.setVisible(false);
            JBdoubleWord.setVisible(false);
            JBtripleWord.setVisible(false);
            JBbonusUndo.setVisible(false);
            JBaddWord.setVisible(false);
            JBendTurn.setVisible(false);
            JLplayedScore.setVisible(false);
            JLmultiplier.setVisible(false);
            JTword.setText("Enter Word");
            for (int i = 0; i < playedWord.length(); i++) {
                JBtiles[i].setVisible(false);
            }
            int numPlayers = getNumPlayers();
            for (int i = 0; i < numPlayers; i++) {
                scores[i] = 0;
                JLscores[i].setText(Integer.toString(scores[i]));
                JTnames[i].setText("Player " + (i + 1));
            }
            JTword.requestFocusInWindow();
            ScrabbleScoreboardApplication.getScoreboard().delete();
            Controller.newGame(numPlayers);
        };
    }

    private ActionListener addWordAction() {
        return e -> {
            int selectedPlayer = Controller.getCurrentPlayer();
            scores[selectedPlayer] = Controller.confirmWordAndAddAnother();
            JLscores[selectedPlayer].setText(Integer.toString(scores[selectedPlayer]));
            playedScore = 0;
            multiplier = 1;
            JTword.setText("Enter Word");
            for (Component component : JPword.getComponents()) {
                component.setVisible(false);
            }
            JBskipTurn.setVisible(true);
            JTword.requestFocusInWindow();
        };
    }

    private ActionListener remainingLettersAction() {
        return e -> {
            playedWord = JTword.getText();
            try {
                JBgameFinished.setVisible(false);
                JBreset.setVisible(false);
                negativeScore = calculateScore(playedWord, JBtiles);
                JTword.setText("Modify Remaining Letters:");
                negativePlayer = Controller.getCurrentPlayer();
                JBconfirm.setVisible(true);
                JTword.requestFocusInWindow();
            } catch (Exception ArrayIndexOutOfBoundsException) {
                JTword.setText("Illegal Letter. Try Again.");
                for (int i = 0; i < playedWord.length(); i++) {
                    JBtiles[i].setVisible(false);
                }
            }
        };
    }

    private int getWinningPlayer() {
        return Controller.getGameWinner();
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

    private ActionListener challengeWordAction(int i) {
        return e -> {
            try {
                boolean result = Controller.isWordValid(i);
                if (!result){
                    setScreenForNextPlayer();
                    challengeResultLabel.setVisible(true);
                    challengeResultLabel.setText("<html>Challenge Result:<br/>Not valid!</html>");
                }
                else {
                    challengeResultLabel.setVisible(true);
                    challengeResultLabel.setText("<html>Challenge Result:<br/>Valid!</html>");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        };
    }

    private void setScreenForNextPlayer() {
        JTword.setBackground(getPLAYERCOLOR(Controller.getCurrentPlayer()));
        playedScore = 0;
        multiplier = 1;
        JTBdoubleLetter.setVisible(false);
        JTBtripleLetter.setVisible(false);
        JBdoubleWord.setVisible(false);
        JBtripleWord.setVisible(false);
        JBbonusUndo.setVisible(false);
        JBaddWord.setVisible(false);
        JBendTurn.setVisible(false);
        JLplayedScore.setVisible(false);
        JLmultiplier.setVisible(false);
        challengeResultLabel.setVisible(false);
        JBskipTurn.setVisible(true);
        JTword.setText("Enter Word");
        for (int i = 0; i < getNumPlayers(); i++) {
            JBchallenge[i].setVisible(false);
        }
        for (int i = 0; i < playedWord.length(); i++) {
            JBtiles[i].setVisible(false);
        }
        JTword.requestFocusInWindow();
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
            } else if (JTBtripleLetter.isSelected()) {
                letterMultiplier = 2;
                JTBtripleLetter.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                JTBdoubleLetter.setBorder(null);
            } else {
                letterMultiplier = 0;
                JTBdoubleLetter.setBorder(null);
                JTBtripleLetter.setBorder(null);
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

    private JPanel getJPplayers() {

        for (int i = 0; i < getNumPlayers(); i++) {
            JTnames[i] = new JTname("Player " + Integer.toString(i + 1));
            JLscores[i] = new JLabel("0", JLabel.CENTER);
            JBchallenge[i] = new JButton("Challenge Word");
            JBchallenge[i].setCursor(getCursor());
            JBchallenge[i].addActionListener(challengeWordAction(i));
            scores[i] = 0;
            JTnames[i].setBackground(getPLAYERCOLOR(i));
            playersLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JTnames[i],
                    getWidth() * (i + 1) / (getNumPlayers() + 1),
                    SpringLayout.WEST, JPplayers);
            JBchallenge[i].setBackground(getPLAYERCOLOR(i));
            playersLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBchallenge[i],
                    0,
                    SpringLayout.HORIZONTAL_CENTER, JTnames[i]);
            JBchallenge[i].setPreferredSize(new Dimension(150, 30));
            JLscores[i].setFont(getFont());
            JLscores[i].setBorder(BorderFactory.createLineBorder(getPLAYERCOLOR(i), 3));
            JLscores[i].setPreferredSize(new Dimension(70, 40));
            JLscores[i].setOpaque(true);
            playersLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLscores[i],
                    0,
                    SpringLayout.HORIZONTAL_CENTER, JTnames[i]);
            playersLayout.putConstraint(SpringLayout.NORTH, JBchallenge[i], 10, SpringLayout.SOUTH, JLscores[i]);
            playersLayout.putConstraint(SpringLayout.NORTH, JLscores[i], 10, SpringLayout.SOUTH, JTnames[i]);
            JBchallenge[i].setVisible(false);
            JPplayers.add(JTnames[i]);
            JPplayers.add(JLscores[i]);
            JPplayers.add(JBchallenge[i]);
        }
        JTword.setBackground(getPLAYERCOLOR(0));
        JTword.setHorizontalAlignment(JTextField.CENTER);
        JTword.setPreferredSize(new Dimension(200, 50));
        playersLayout.putConstraint(SpringLayout.NORTH, JTword,
                10,
                SpringLayout.SOUTH, JBchallenge[0]);
        playersLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JTword,
                getWidth() / 2,
                SpringLayout.WEST, JPplayers);
        JTword.addFocusListener(getFocusListener());
        JTword.addActionListener(wordAction());
        JPplayers.add(JTword);
        return JPplayers;
    }

    private JPanel getJPword() {
        //Intialize max length scrabble word for tiles
        for (int i = 0; i < 15; i++) {
            JBtiles[i] = new JButton();
        }

        JBskipTurn.setPreferredSize(new Dimension(200, 50));
        wordLayout.putConstraint(SpringLayout.NORTH, JBskipTurn, 50, SpringLayout.NORTH, JPword);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBskipTurn, 0, SpringLayout.HORIZONTAL_CENTER, JPword);
        JBskipTurn.addActionListener(endTurnAction());
        JBskipTurn.setVisible(true);
        JPword.add(JBskipTurn);
        
        //Setup JPword panel, so only need to set visible when Word is entered
        JLplayedScore.setBackground(Color.white);
        JLplayedScore.setFont(getFont());
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLplayedScore, getWidth() / 2 - 75, SpringLayout.WEST, JPword);
        wordLayout.putConstraint(SpringLayout.NORTH, JLplayedScore, 135, SpringLayout.NORTH, JPword);
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
        JTBtripleLetter.setPreferredSize(new Dimension(50, 50));
        JBdoubleWord.setPreferredSize(new Dimension(50, 50));
        JBtripleWord.setPreferredSize(new Dimension(50, 50));
        JBbonusUndo.setPreferredSize(new Dimension(75, 50));
        JBaddWord.setPreferredSize(new Dimension(150, 50));
        JBendTurn.setPreferredSize(new Dimension(150, 50));
        challengeResultLabel.setPreferredSize(new Dimension(200, 50));
        challengeResultLabel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        challengeResultLabel.setForeground(Color.black);
        challengeResultLabel.setBackground(Color.white);
        challengeResultLabel.setHorizontalAlignment(JTextField.CENTER);


        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLmultiplier, getWidth() * 6 / 7, SpringLayout.WEST, JPword);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JTBdoubleLetter, getWidth() / 7, SpringLayout.WEST, JPword);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JTBtripleLetter, getWidth() * 2 / 7, SpringLayout.WEST, JPword);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBdoubleWord, getWidth() * 3 / 7, SpringLayout.WEST, JPword);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBtripleWord, getWidth() * 4 / 7, SpringLayout.WEST, JPword);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBbonusUndo, getWidth() * 5 / 7, SpringLayout.WEST, JPword);
        wordLayout.putConstraint(SpringLayout.WEST, JBendTurn, 10, SpringLayout.EAST, JLplayedScore);
        wordLayout.putConstraint(SpringLayout.WEST, JBaddWord, 10, SpringLayout.EAST, JBendTurn);
        wordLayout.putConstraint(SpringLayout.WEST, JBendTurn, 10, SpringLayout.EAST, JLplayedScore);
        wordLayout.putConstraint(SpringLayout.VERTICAL_CENTER, JLmultiplier, 0, SpringLayout.VERTICAL_CENTER, JBdoubleWord);
        wordLayout.putConstraint(SpringLayout.NORTH, JTBdoubleLetter, 10, SpringLayout.NORTH, JPword);
        wordLayout.putConstraint(SpringLayout.NORTH, JTBtripleLetter, 10, SpringLayout.NORTH, JPword);
        wordLayout.putConstraint(SpringLayout.NORTH, JBdoubleWord, 10, SpringLayout.NORTH, JPword);
        wordLayout.putConstraint(SpringLayout.NORTH, JBtripleWord, 10, SpringLayout.NORTH, JPword);
        wordLayout.putConstraint(SpringLayout.NORTH, JBbonusUndo, 10, SpringLayout.NORTH, JPword);
        wordLayout.putConstraint(SpringLayout.NORTH, JBaddWord, 0, SpringLayout.NORTH, JLplayedScore);
        wordLayout.putConstraint(SpringLayout.NORTH, JBendTurn, 0, SpringLayout.NORTH, JLplayedScore);
        wordLayout.putConstraint(SpringLayout.NORTH, challengeResultLabel, 0, SpringLayout.NORTH, JLplayedScore);
        wordLayout.putConstraint(SpringLayout.EAST, challengeResultLabel, -10, SpringLayout.WEST, JLplayedScore);
        JTBdoubleLetter.setCursor(getCursor());
        JTBtripleLetter.setCursor(getCursor());
        JBdoubleWord.setCursor(getCursor());
        JBdoubleWord.setCursor(getCursor());
        JBbonusUndo.setCursor(getCursor());
        JBaddWord.setCursor(getCursor());
        JBendTurn.setCursor(getCursor());
        JTBdoubleLetter.setVisible(false);
        JTBtripleLetter.setVisible(false);
        JBdoubleWord.setVisible(false);
        JBtripleWord.setVisible(false);
        JBbonusUndo.setVisible(false);
        JBaddWord.setVisible(false);
        JBendTurn.setVisible(false);
        JLmultiplier.setVisible(false);
        challengeResultLabel.setVisible(false);
        BGletterBonus.add(JTBdoubleLetter);
        BGletterBonus.add(JTBtripleLetter);
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

        JPword.add(JLplayedScore);
        JPword.add(JTBdoubleLetter);
        JPword.add(JTBtripleLetter);
        JPword.add(JBdoubleWord);
        JPword.add(JBtripleWord);
        JPword.add(JBbonusUndo);
        JPword.add(JBendTurn);
        JPword.add(JBaddWord);
        JPword.add(JLmultiplier);
        JPword.add(challengeResultLabel);
        return JPword;
    }

    private JPanel getJPoptions() {
        //Setup Options panel
        optionsLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBreset,
                getWidth() / 4,
                SpringLayout.WEST, JPoptions);
        optionsLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBgameFinished,
                getWidth() * 2 / 4,
                SpringLayout.WEST, JPoptions);
        optionsLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBgameFinishedNoMoves,
                getWidth() * 3 / 4,
                SpringLayout.WEST, JPoptions);
        optionsLayout.putConstraint(SpringLayout.NORTH, JBreset,
                40,
                SpringLayout.NORTH, JPoptions);
        optionsLayout.putConstraint(SpringLayout.NORTH, JBgameFinished,
                40,
                SpringLayout.NORTH, JPoptions);
        optionsLayout.putConstraint(SpringLayout.NORTH, JBgameFinishedNoMoves,
                40,
                SpringLayout.NORTH, JPoptions);
        JBreset.setPreferredSize(new Dimension(120, 50));
        JBgameFinished.setPreferredSize(new Dimension(150, 50));
        JBgameFinishedNoMoves.setPreferredSize(new Dimension(130, 50));

        JBreset.setVisible(true);
        JBgameFinished.setVisible(true);
        JBgameFinishedNoMoves.setVisible(true);
        JBreset.addActionListener(resetAction());
        JBgameFinished.addActionListener(gameFinishedAction());
        JBgameFinishedNoMoves.addActionListener(gameFinishedAction());

        JPoptions.add(JBreset);
        JPoptions.add(JBgameFinished);
        JPoptions.add(JBgameFinishedNoMoves);
        return JPoptions;
    }

    private Color getPLAYERCOLOR(int num) {
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

    private int getNumPlayers() {
        return numPlayers;
    }

    public Cursor getCursor() {
        return hoverCursor;
    }

    public Font getFont() {
        return Fscrabble;
    }

    public int getWidth() {
        return this.getContentPane().getWidth();
    }

    public int getHeight() {
        return this.getContentPane().getHeight();
    }

    private int calculateScore(String word, JButton[] JBtile) {
        int score;
        int i = 0;
        for (char letter : word.toCharArray()) {
            JBtile[i].setText(String.valueOf(Character.toUpperCase(letter)));
            JBtile[i].setBackground(new Color(255, 255, 153));
            JBtile[i].setHorizontalAlignment(JButton.CENTER);
            JBtile[i].setFont(getFont());
            JPword.add(JBtile[i]);
            wordLayout.putConstraint(SpringLayout.NORTH, JBtile[i], 70, SpringLayout.NORTH, JPword);
            wordLayout.putConstraint(SpringLayout.WEST, JBtile[i],
                    (getWidth() / 2 - 25 * word.length() + 50 * i),
                    SpringLayout.WEST, JPword);
            JBtile[i].setBorder(BorderFactory.createLineBorder(Color.gray, 2));
            JBtile[i].setForeground(Color.black);
            JBtile[i].setCursor(getCursor());
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

    private ActionListener gameFinishedAction() {
        return e -> {
            if (e.getSource()==JBgameFinishedNoMoves){
                JBconfirm.addActionListener(confirmActionNoMoves());
            }
            else  {
                JBconfirm.addActionListener(confirmAction());
            }

            for (ActionListener listener : JTword.getActionListeners()) {
                JTword.removeActionListener(listener);
            }
            for (Component component : JPword.getComponents()) {
                component.setVisible(false);
            }
            for (Component component : JPoptions.getComponents()) {
                component.setVisible(false);
            }


            JTword.setText("Enter remaining Letters");
            JTword.addActionListener(remainingLettersAction());
            finishingPlayer = (Controller.getCurrentPlayer() + getNumPlayers() -1) % getNumPlayers();
            wordLayout.putConstraint(SpringLayout.WEST, JBconfirm, 20, SpringLayout.EAST, JLplayedScore);
            wordLayout.putConstraint(SpringLayout.VERTICAL_CENTER, JBconfirm, 0, SpringLayout.VERTICAL_CENTER, JLplayedScore);
            JBconfirm.setVisible(false);
            JTword.requestFocusInWindow();
            JPword.add(JBconfirm);
            JBnewGame.addActionListener(newGameAction());
        };
    }

    private ActionListener newGameAction() {
        return arg0 -> {
            this.dispose();

            new Scoreboard();
        };
    }

    private ActionListener confirmAction() {
        return e -> {

            JTword.setText("Enter remaining Letters:");
            scores[negativePlayer] = Controller.removeNegativeLetterScore();
            scores[finishingPlayer] = Controller.addNegativeLettersToScore(negativeScore, finishingPlayer);
            JLscores[negativePlayer].setText(Integer.toString(scores[negativePlayer]));
            negativePlayer = Controller.getCurrentPlayer();
            JTword.setBackground(getPLAYERCOLOR(negativePlayer));
            negativeScore(finishingPlayer);

            if (finalTurn == getNumPlayers()) {
                gameFinishedFinalScreen();
            }
        };
    }
    
    private ActionListener confirmActionNoMoves() {
        return e -> {
            JTword.setText("Enter remaining Letters:");
            scores[negativePlayer] = Controller.removeNegativeLetterScore();
            negativeScore(negativePlayer);
            negativePlayer = Controller.getCurrentPlayer();
            JTword.setBackground(getPLAYERCOLOR(negativePlayer));

            if (finalTurn == getNumPlayers()+1) {
                gameFinishedFinalScreen();
            }
        };
    }

    private void gameFinishedFinalScreen() {
        JTword.setText("Game Finished");
        JTword.setBackground(getPLAYERCOLOR(getWinningPlayer()));
        JTword.setEditable(false);
        JBconfirm.setVisible(false);
        JPword.remove(JBconfirm);
        JBnewGame.setPreferredSize(new Dimension(160, 50));
        optionsLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBnewGame, 0, SpringLayout.HORIZONTAL_CENTER, JPoptions);
        optionsLayout.putConstraint(SpringLayout.NORTH, JBnewGame, 0, SpringLayout.NORTH, JPoptions);
        JBnewGame.setVisible(true);
        JPoptions.add(JBnewGame);
        JLwinningMessage.setText("Congratulations " + JTnames[getWinningPlayer()].getText() + "!");
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLwinningMessage, 0, SpringLayout.HORIZONTAL_CENTER, JPword);
        wordLayout.putConstraint(SpringLayout.VERTICAL_CENTER, JLwinningMessage, 0, SpringLayout.VERTICAL_CENTER, JPword);
        JLwinningMessage.setFont(getFont());
        JBnewGame.requestFocusInWindow();
        JPword.add(JLwinningMessage);
    }

    private void negativeScore(int negativePlayer) {
        JLscores[negativePlayer].setText(Integer.toString(scores[negativePlayer]));
        negativeScore = 0;
        finalTurn++;
        JBconfirm.setVisible(false);
        for (int i = 0; i < 7; i++) {
            JBtiles[i].setVisible(false);
            JLplayedScore.setVisible(false);
        }
        JTword.requestFocusInWindow();
    }

    private ActionListener wordAction() {
        return e -> {
            playedWord = JTword.getText();
            try {
                playedScore = calculateScore(playedWord, JBtiles);
                int currentPlayer = Controller.getCurrentPlayer();
                for (int i = 0; i < getNumPlayers(); i++) {
                    if (i != currentPlayer){
                        JBchallenge[i].setVisible(true);
                    }
                }
                JTword.setText("Modify Word");
                multiplier = 1;
                challengeResultLabel.setVisible(false);
                JBskipTurn.setVisible(false);
                JLmultiplier.setVisible(false);
                JTBdoubleLetter.setVisible(true);
                JTBtripleLetter.setVisible(true);
                JBdoubleWord.setVisible(true);
                JBtripleWord.setVisible(true);
                JBbonusUndo.setVisible(true);
                JBendTurn.setVisible(true);
                JBaddWord.setVisible(true);
            } catch (Exception ArrayIndexOutOfBoundsException) {
                JTword.setText("Illegal Word. Try Again.");
                for (int i = 0; i < playedWord.length(); i++) {
                    JBtiles[i].setVisible(false);
                }
            }
        };
    }

   public void actionPerformed(ActionEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
