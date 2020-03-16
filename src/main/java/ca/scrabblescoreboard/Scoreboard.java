/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.scrabblescoreboard;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

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


    public JFrame frame = new JFrame("Scrabble Scoreboard");

    private SpringLayout setupLayout = new SpringLayout();
    private SpringLayout playersLayout = new SpringLayout();
    private SpringLayout wordLayout = new SpringLayout();
    private SpringLayout optionsLayout = new SpringLayout();

    private JPanel JPsetup = new JPanel(setupLayout);
    private JLabel JLintroMessage = new JLabel("<html><p style =\"text-align: center\">Welcome to Scrabble Scoreboard! Select the number of Players:</p></html>");
    private String[] selectNumPlayers = {"2", "3", "4"};
    private JList<String> JLnumpLayers = new JList<String>(selectNumPlayers);
    private JButton JBstartGame = new JButton("Start Game");
    private int numPlayers;
    private int width = 800, height = 600;

    private JPanel JPplayers = new JPanel(playersLayout);
    private JTextField[] JTnames = new JTextField[4];
    private JTextField JTword = new JTextField("Enter word", 15);
    private JLabel[] JLscores = new JLabel[4];
    private int[] scores = new int[4];

    private JPanel JPword = new JPanel(wordLayout);
    private JButton JBskipTurn = new JButton("<html><p style=\"text-align:center\"> Change Letters (Skip Turn) </p> </html>");
    private JLabel JLmultiplier = new JLabel("", JLabel.CENTER);
    private JButton[] JBtiles = new JButton[15];
    private JLabel JLplayedScore = new JLabel("", JLabel.CENTER);
    
    private ImageIcon doubleWordImage = new ImageIcon("C:\\Users\\user\\Documents\\NetBeansProjects\\ScrabbleScoreboard\\src\\scrabblescoreboard\\doubleWordScore.png");
    private ImageIcon tripleWordImage = new ImageIcon("C:\\Users\\user\\Documents\\NetBeansProjects\\ScrabbleScoreboard\\src\\scrabblescoreboard\\tripleWordScore.png");
    private ImageIcon doubleLetterImage = new ImageIcon("C:\\Users\\user\\Documents\\NetBeansProjects\\ScrabbleScoreboard\\src\\scrabblescoreboard\\doubleLetterScore.png");
    private ImageIcon tripleLetterImage = new ImageIcon("C:\\Users\\user\\Documents\\NetBeansProjects\\ScrabbleScoreboard\\src\\scrabblescoreboard\\tripleLetterScore.png");
    
    Image doubleWordImg = doubleWordImage.getImage();
    ImageIcon doubleWordIcon = new ImageIcon(doubleWordImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ));
    Image tripleWordImg = tripleWordImage.getImage();
    ImageIcon tripleWordIcon = new ImageIcon(tripleWordImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ));
    Image doubleLetterImg = doubleLetterImage.getImage();
    ImageIcon doubleLetterIcon = new ImageIcon(doubleLetterImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ));
    Image tripleLetterImg = tripleLetterImage.getImage();
    ImageIcon tripleLetterIcon = new ImageIcon(tripleLetterImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ));
    
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

    private String playedWord = new String();

    private JPanel JPoptions = new JPanel(optionsLayout);

    public Scoreboard() {
        frame.setResizable(false);
        frame.setBounds(100, 100, width, height);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(0, 1));
        frame.setVisible(true);
        NewGame();
    }

    public void NewGame() {
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
        frame.add(JPsetup);

    }

    public ActionListener startGameAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int index = JLnumpLayers.getSelectedIndex();
                if (index == 0 || index == 1 || index == 2) {
                    numPlayers = index + 2;
                    JPsetup.setVisible(false);
                    frame.remove(JPsetup);
                    frame.add(getJPplayers());
                    frame.add(getJPword());
                    frame.add(getJPoptions());
                    index = 0;
                }
            }
        };
    }

    public ActionListener endTurnAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turn++;
                if (turn % getNumPlayers() == 1) {
                    JTword.setBackground(getPLAYER2COLOR());
                    scores[0] += playedScore;
                    JLscores[0].setText(Integer.toString(scores[0]));

                } else if (turn % getNumPlayers() == 2) {
                    JTword.setBackground(getPLAYER3COLOR());
                    scores[1] += playedScore;
                    JLscores[1].setText(Integer.toString(scores[1]));
                } else if (turn % getNumPlayers() == 3) {
                    JTword.setBackground(getPLAYER4COLOR());
                    scores[2] += playedScore;
                    JLscores[2].setText(Integer.toString(scores[2]));
                } else {
                    JTword.setBackground(getPLAYER1COLOR());
                    scores[getNumPlayers() - 1] += playedScore;
                    JLscores[getNumPlayers() - 1].setText(Integer.toString(scores[getNumPlayers() - 1]));
                }
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
                JBskipTurn.setVisible(true);
                JTword.setText("Enter Word");
                for (int i = 0; i < playedWord.length(); i++) {
                    JBtiles[i].setVisible(false);
                }

            }
        };
    }

    public ActionListener resetAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turn = 0;
                JTword.setBackground(getPLAYER1COLOR());
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
                for (int i = 0; i < getNumPlayers(); i++) {
                    scores[i] = 0;
                    JLscores[i].setText(Integer.toString(scores[i]));
                    JTnames[i].setText("Player " + (i + 1));
                }
            }
        };
    }

    public ActionListener addWordAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedPlayer = (turn % getNumPlayers());
                scores[selectedPlayer] += playedScore;
                JLscores[selectedPlayer].setText(Integer.toString(scores[selectedPlayer]));
                playedScore = 0;
                multiplier = 1;
                JTword.setText("Enter Word");
                for (Component component : Arrays.asList(JPword.getComponents())) {
                    component.setVisible(false);
                }
                JBskipTurn.setVisible(true);
            }
        };
    }

    public ActionListener remainingLettersAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playedWord = JTword.getText();
                try {
                    JBgameFinished.setVisible(false);
                    JBreset.setVisible(false);
                    negativeScore = calculateScore(playedWord, JBtiles);
                    JTword.setText("Modify Remaining Letters:");
                    negativePlayer = (finishingPlayer + finalTurn) % getNumPlayers();
                    JBconfirm.setVisible(true);
                } catch (Exception ArrayIndexOutOfBoundsException) {
                    JTword.setText("Illegal Letter. Try Again.");
                    for (int i = 0; i < playedWord.length(); i++) {
                        JBtiles[i].setVisible(false);
                    }
                }
            }
        };
    }

    public int getwinningPlayer() {
        if (scores[0] > scores[1] && scores[0] > scores[2] && scores[0] > scores[3]) {
            return 0;
        } else if (scores[1] > scores[0] && scores[1] > scores[2] && scores[1] > scores[3]) {
            return 1;
        } else if (scores[2] > scores[1] && scores[2] > scores[0] && scores[2] > scores[3]) {
            return 2;
        } else if (scores[3] > scores[1] && scores[3] > scores[2] && scores[3] > scores[0]) {
            return 3;
        } else {
            return -1;
        }

    }

    public ActionListener doubleWordAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                playedScore *= 2;
                JLplayedScore.setText(Integer.toString(playedScore));
                multiplier *= 2;
                JLmultiplier.setText("x " + Integer.toString(multiplier));
                JLmultiplier.setVisible(true);
            }
        };
    }

    public ActionListener tripleWordAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                playedScore *= 3;
                JLplayedScore.setText(Integer.toString(playedScore));
                multiplier *= 3;
                JLmultiplier.setText("x " + Integer.toString(multiplier));
                JLmultiplier.setVisible(true);
            }
        };
    }

    public ActionListener getUndoAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                playedScore = calculateScore(playedWord, JBtiles);
                JLplayedScore.setText(Integer.toString(playedScore));
                multiplier = 1;
                JLmultiplier.setVisible(false);
            }
        };
    }

    public ActionListener letterBonusListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
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
            }
        };
    }

    public FocusListener getFocusListener() {
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

    public JPanel getJPplayers() {

        for (int i = 0; i < getNumPlayers(); i++) {
            JTnames[i] = new JTname("Player " + (i + 1));
            JLscores[i] = new JLabel("0", JLabel.CENTER);
            scores[i] = 0;
            JTnames[i].setBackground(getPLAYERCOLOR(i));
            playersLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JTnames[i],
                    getWidth() * (i + 1) / (getNumPlayers() + 1),
                    SpringLayout.WEST, JPplayers);
            JLscores[i].setFont(getFont());
            JLscores[i].setBorder(BorderFactory.createLineBorder(getPLAYERCOLOR(i), 3));
            JLscores[i].setPreferredSize(new Dimension(50, 50));
            JLscores[i].setOpaque(true);
            playersLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLscores[i],
                    0,
                    SpringLayout.HORIZONTAL_CENTER, JTnames[i]);
            playersLayout.putConstraint(SpringLayout.NORTH, JLscores[i], 10, SpringLayout.SOUTH, JTnames[i]);
            JPplayers.add(JTnames[i]);
            JPplayers.add(JLscores[i]);
        }
        JTword.setBackground(getPLAYER1COLOR());
        JTword.setHorizontalAlignment(JTextField.CENTER);
        JTword.setPreferredSize(new Dimension(200, 50));
        playersLayout.putConstraint(SpringLayout.NORTH, JTword,
                25,
                SpringLayout.SOUTH, JLscores[0]);
        playersLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JTword,
                getWidth() / 2,
                SpringLayout.WEST, JPplayers);
        JTword.addFocusListener(getFocusListener());
        JTword.addActionListener(wordAction());
        JPplayers.add(JTword);
        return JPplayers;
    }

    public JPanel getJPword() {
        //Intialize max length scrabble word for tiles
        for (int i = 0; i < 15; i++) {
            JBtiles[i] = new JButton();
        }
        JBskipTurn.setPreferredSize(new Dimension(150, 50));
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
        BGletterBonus.add(JTBdoubleLetter);
        BGletterBonus.add(JTBtripleLetter);
        JTBdoubleLetter.addActionListener(letterBonusListener());
        JTBtripleLetter.addActionListener(letterBonusListener());
        JBaddWord.addActionListener(addWordAction());
        for (int l = 0; l < 15; l++) {
            int index = l;
            JBtiles[l].addActionListener(
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e
                ) {
                    playedScore += multiplier * letterMultiplier * getLetterScore(JBtiles[index].getText().toCharArray()[0]);
                    JLplayedScore.setText(Integer.toString(playedScore));
                }
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
        return JPword;
    }

    public JPanel getJPoptions() {
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

    public Color getPLAYERCOLOR(int num) {
        switch (num) {
            case 0:
                return getPLAYER1COLOR();
            case 1:
                return getPLAYER2COLOR();
            case 2:
                return getPLAYER3COLOR();
            case 3:
                return getPLAYER4COLOR();
            default:
                return Color.WHITE;
        }
    }

    public Color getPLAYER1COLOR() {
        return PLAYER1COLOR;
    }

    public Color getPLAYER2COLOR() {
        return PLAYER2COLOR;
    }

    public Color getPLAYER3COLOR() {
        return PLAYER3COLOR;
    }

    public Color getPLAYER4COLOR() {
        return PLAYER4COLOR;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public Cursor getCursor() {
        return hoverCursor;
    }

    public Font getFont() {
        return Fscrabble;
    }

    public int getWidth() {
        return frame.getContentPane().getWidth();
    }

    public int getHeight() {
        return frame.getContentPane().getHeight();
    }

    public int getLetterScore(char letter) {
        char upperCase = Character.toUpperCase(letter);
        return LETTER_SCORES[upperCase - 'A'];
    }

    public int calculateScore(String word, JButton[] JBtile) {
        int _score = 0;
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
            _score += getLetterScore(letter);
            i++;
        }
        for (int j = word.length(); j < 15; j++) {
            JBtile[j].setVisible(false);
        }

        JLplayedScore.setText(Integer.toString(_score));
        JLplayedScore.setVisible(true);
        return _score;
    }

    public ActionListener gameFinishedAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==JBgameFinishedNoMoves){
                    JBconfirm.addActionListener(confirmActionNoMoves());
                }
                else  {
                    JBconfirm.addActionListener(confirmAction());
                }
                
                for (ActionListener listener : Arrays.asList(JTword.getActionListeners())) {
                    JTword.removeActionListener(listener);
                }
                for (Component component : Arrays.asList(JPword.getComponents())) {
                    component.setVisible(false);
                }
                for (Component component : Arrays.asList(JPoptions.getComponents())) {
                    component.setVisible(false);
                }

                
                JTword.setText("Enter remaining Letters");
                JTword.addActionListener(remainingLettersAction());
                finishingPlayer = (turn + getNumPlayers() - 1) % getNumPlayers();
                wordLayout.putConstraint(SpringLayout.WEST, JBconfirm, 20, SpringLayout.EAST, JLplayedScore);
                wordLayout.putConstraint(SpringLayout.VERTICAL_CENTER, JBconfirm, 0, SpringLayout.VERTICAL_CENTER, JLplayedScore);
                JBconfirm.setVisible(false);
                JPword.add(JBconfirm);
                JBnewGame.addActionListener(newGameAction());
            }
        };
    }

    public ActionListener newGameAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();

                new Scoreboard();
            }
        };
    }

    public ActionListener confirmAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTword.setBackground(getPLAYERCOLOR((negativePlayer + 1) % getNumPlayers()));
                JTword.setText("Enter remaining Letters:");
                scores[negativePlayer] -= negativeScore;
                scores[finishingPlayer] += negativeScore;
                JLscores[negativePlayer].setText(Integer.toString(scores[negativePlayer]));
                JLscores[finishingPlayer].setText(Integer.toString(scores[finishingPlayer]));
                negativeScore = 0;
                finalTurn++;
                JBconfirm.setVisible(false);
                for (int i = 0; i < 7; i++) {
                    JBtiles[i].setVisible(false);
                    JLplayedScore.setVisible(false);
                }

                if (finalTurn == getNumPlayers()) {
                    JTword.setText("Game Finished");
                    JTword.setBackground(getPLAYERCOLOR(getwinningPlayer()));
                    JTword.setEditable(false);
                    JBconfirm.setVisible(false);
                    JPword.remove(JBconfirm);
                    JBnewGame.setPreferredSize(new Dimension(160, 50));
                    optionsLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBnewGame, 0, SpringLayout.HORIZONTAL_CENTER, JPoptions);
                    optionsLayout.putConstraint(SpringLayout.VERTICAL_CENTER, JBnewGame, 0, SpringLayout.VERTICAL_CENTER, JPoptions);
                    JPoptions.add(JBnewGame);
                    JLwinningMessage.setText("Congratulations " + JTnames[getwinningPlayer()].getText() + "!");
                    wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLwinningMessage, 0, SpringLayout.HORIZONTAL_CENTER, JPword);
                    wordLayout.putConstraint(SpringLayout.VERTICAL_CENTER, JLwinningMessage, 0, SpringLayout.VERTICAL_CENTER, JPword);
                    JLwinningMessage.setFont(getFont());
                    JPword.add(JLwinningMessage);
                }
            }
        };
    }
    
    public ActionListener confirmActionNoMoves() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTword.setBackground(getPLAYERCOLOR((negativePlayer + 1) % getNumPlayers()));
                JTword.setText("Enter remaining Letters:");
                scores[negativePlayer] -= negativeScore;
                JLscores[negativePlayer].setText(Integer.toString(scores[negativePlayer]));
                negativeScore = 0;
                finalTurn++;
                JBconfirm.setVisible(false);
                for (int i = 0; i < 7; i++) {
                    JBtiles[i].setVisible(false);
                    JLplayedScore.setVisible(false);
                }

                if (finalTurn == getNumPlayers()+1) {
                    JTword.setText("Game Finished");
                    JTword.setBackground(getPLAYERCOLOR(getwinningPlayer()));
                    JTword.setEditable(false);
                    JBconfirm.setVisible(false);
                    JPword.remove(JBconfirm);
                    JBnewGame.setPreferredSize(new Dimension(160, 50));
                    optionsLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBnewGame, 0, SpringLayout.HORIZONTAL_CENTER, JPoptions);
                    optionsLayout.putConstraint(SpringLayout.VERTICAL_CENTER, JBnewGame, 0, SpringLayout.VERTICAL_CENTER, JPoptions);
                    JPoptions.add(JBnewGame);
                    JLwinningMessage.setText("Congratulations " + JTnames[getwinningPlayer()].getText() + "!");
                    wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLwinningMessage, 0, SpringLayout.HORIZONTAL_CENTER, JPword);
                    wordLayout.putConstraint(SpringLayout.VERTICAL_CENTER, JLwinningMessage, 0, SpringLayout.VERTICAL_CENTER, JPword);
                    JLwinningMessage.setFont(getFont());
                    JPword.add(JLwinningMessage);
                }
            }
        };
    }

    public ActionListener wordAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                playedWord = JTword.getText();
                try {
                    playedScore = calculateScore(playedWord, JBtiles);
                    JTword.setText("Modify Word");
                    multiplier = 1;
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
            }
        };
    }

   public void actionPerformed(ActionEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
