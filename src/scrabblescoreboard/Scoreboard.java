/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblescoreboard;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

/**
 *
 * @author Patrick Tweddell
 */
public class Scoreboard extends JFrame implements ActionListener {

    /* A = 1, B = 3, C = 3, etc. */
    private static final int[] LETTER_SCORES = {
        1, 3, 3, 2, 1, 4, 2, 4, 1, 8,
        5, 1, 3, 1, 1, 3, 10, 1, 1, 1,
        1, 4, 4, 8, 4, 10};

    Font Fscrabble = new Font("gothic standard", Font.BOLD, 24);

    Cursor hoverCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    JPanel JPplayers = new JPanel(),
            JPword = new JPanel(),
            JPoptions = new JPanel();

    SpringLayout playersLayout = new SpringLayout(),
            wordLayout = new SpringLayout(),
            optionsLayout = new SpringLayout();

    JTextField JTname1 = new JTextField("Player 1", 15),
            JTname2 = new JTextField("Player 2", 15),
            JTword = new JTextField("Enter word", 15);

    JLabel JLscore = new JLabel("", JLabel.CENTER),
            JLscore1 = new JLabel("0", JLabel.CENTER),
            JLscore2 = new JLabel("0", JLabel.CENTER),
            JLmultiplier = new JLabel("", JLabel.CENTER);

    JButton[] JBtiles = new JButton[15];

    JButton JBdoubleWord = new JButton("2x"),
            JBtripleWord = new JButton("3x"),
            JBbonusUndo = new JButton("Undo"),
            JBendTurn = new JButton("End Turn"),
            JBaddWord = new JButton("Add Another Word");

    JToggleButton JTBdoubleLetter = new JToggleButton("2L"),
            JTBtripleLetter = new JToggleButton("3L");

    ButtonGroup BGletterBonus = new ButtonGroup();

    protected int score1 = 0,
            score2 = 0,
            playedScore = 0,
            letterMultiplier = 0,
            multiplier = 1,
            turn = 0;

    protected String playedWord;

    public Scoreboard(int width, int height) {

        //Set layout for panels
        setLayout(new GridLayout(0, 1));
        JPplayers.setLayout(playersLayout);
        JPword.setLayout(wordLayout);
        JPoptions.setLayout(optionsLayout);

        //Intialize max length scrabble word for tiles
        for (int i = 0; i < 15; i++) {
            JBtiles[i] = new JButton();
        }

        //Initialize players and scores
        JTname1.setBackground(Color.red);
        JTname2.setBackground(Color.blue);
        JTname1.setHorizontalAlignment(JTextField.CENTER);
        JTname2.setHorizontalAlignment(JTextField.CENTER);

        playersLayout.putConstraint(SpringLayout.WEST, JTname1,
                width / 5,
                SpringLayout.WEST, JPplayers);
        playersLayout.putConstraint(SpringLayout.WEST, JTname2,
                2 * width / 5 - 200,
                SpringLayout.EAST, JTname1);
        playersLayout.putConstraint(SpringLayout.NORTH, JTname1,
                10,
                SpringLayout.NORTH, JPplayers);
        playersLayout.putConstraint(SpringLayout.NORTH, JTname2,
                10,
                SpringLayout.NORTH, JPplayers);
        JTname1.setPreferredSize(new Dimension(100, 40));
        JTname2.setPreferredSize(new Dimension(100, 40));
        JTname1.setMaximumSize(new Dimension(100, 40));
        JTname2.setMaximumSize(new Dimension(100, 40));
        JTname1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTname1.setFont(Fscrabble);
                JTname1.setForeground(Color.white);
            }
        });
        JTname2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTname2.setFont(Fscrabble);
                JTname2.setForeground(Color.white);
            }
        });
        //initialize scores on panel
        JLscore1.setBackground(Color.white);
        JLscore2.setBackground(Color.white);
        JLscore1.setForeground(Color.black);
        JLscore2.setForeground(Color.black);
        JLscore1.setFont(Fscrabble);
        JLscore2.setFont(Fscrabble);
        JLscore1.setBorder(BorderFactory.createLineBorder(Color.red, 3));
        JLscore2.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
        JLscore1.setPreferredSize(new Dimension(50, 50));
        JLscore2.setPreferredSize(new Dimension(50, 50));
        JLscore1.setOpaque(true);
        JLscore2.setOpaque(true);
        playersLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLscore1,
                0,
                SpringLayout.HORIZONTAL_CENTER, JTname1);
        playersLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLscore2,
                0,
                SpringLayout.HORIZONTAL_CENTER, JTname2);
        playersLayout.putConstraint(SpringLayout.NORTH, JLscore1,
                10,
                SpringLayout.SOUTH, JTname1);
        playersLayout.putConstraint(SpringLayout.NORTH, JLscore2,
                10,
                SpringLayout.SOUTH, JTname2);

        JTword.setBackground(Color.red);
        JTword.setHorizontalAlignment(JTextField.CENTER);
        JTword.setPreferredSize(new Dimension(100, 40));
        playersLayout.putConstraint(SpringLayout.NORTH, JTword,
                25,
                SpringLayout.SOUTH, JLscore1);
        playersLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JTword,
                width / 2,
                SpringLayout.WEST, JPplayers);

        //Setup JPword panel, so only need to set visible when Word is entered
        JLscore.setBackground(Color.white);
        JLscore.setFont(Fscrabble);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLscore, width / 2 - 75, SpringLayout.WEST, JPword);
        wordLayout.putConstraint(SpringLayout.NORTH, JLscore, 135, SpringLayout.NORTH, JPword);
        JLscore.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        JLscore.setForeground(Color.black);
        JLscore.setPreferredSize(new Dimension(50, 50));
        JLscore.setVisible(false);

        JLmultiplier.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        JLmultiplier.setForeground(Color.black);
        JLmultiplier.setVisible(false);
        JLmultiplier.setPreferredSize(new Dimension(50, 50));
        JTBdoubleLetter.setPreferredSize(new Dimension(50, 50));
        JTBtripleLetter.setPreferredSize(new Dimension(50, 50));
        JBdoubleWord.setPreferredSize(new Dimension(50, 50));
        JBtripleWord.setPreferredSize(new Dimension(50, 50));
        JBbonusUndo.setPreferredSize(new Dimension(75, 50));
        JBaddWord.setPreferredSize(new Dimension(150, 50));
        JBendTurn.setPreferredSize(new Dimension(150, 50));
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JLmultiplier, width * 6 / 7, SpringLayout.WEST, JPword);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JTBdoubleLetter, width / 7, SpringLayout.WEST, JPword);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JTBtripleLetter, width * 2 / 7, SpringLayout.WEST, JPword);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBdoubleWord, width * 3 / 7, SpringLayout.WEST, JPword);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBtripleWord, width * 4 / 7, SpringLayout.WEST, JPword);
        wordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, JBbonusUndo, width * 5 / 7, SpringLayout.WEST, JPword);
        wordLayout.putConstraint(SpringLayout.WEST, JBendTurn, 10, SpringLayout.EAST, JLscore);
        wordLayout.putConstraint(SpringLayout.WEST, JBaddWord, 10, SpringLayout.EAST, JBendTurn);
        wordLayout.putConstraint(SpringLayout.WEST, JBendTurn, 10, SpringLayout.EAST, JLscore);
        wordLayout.putConstraint(SpringLayout.VERTICAL_CENTER, JLmultiplier, 0, SpringLayout.VERTICAL_CENTER, JBdoubleWord);
        wordLayout.putConstraint(SpringLayout.NORTH, JTBdoubleLetter, 10, SpringLayout.NORTH, JPword);
        wordLayout.putConstraint(SpringLayout.NORTH, JTBtripleLetter, 10, SpringLayout.NORTH, JPword);
        wordLayout.putConstraint(SpringLayout.NORTH, JBdoubleWord, 10, SpringLayout.NORTH, JPword);
        wordLayout.putConstraint(SpringLayout.NORTH, JBtripleWord, 10, SpringLayout.NORTH, JPword);
        wordLayout.putConstraint(SpringLayout.NORTH, JBbonusUndo, 10, SpringLayout.NORTH, JPword);
        wordLayout.putConstraint(SpringLayout.NORTH, JBaddWord, 0, SpringLayout.NORTH, JLscore);
        wordLayout.putConstraint(SpringLayout.NORTH, JBendTurn, 0, SpringLayout.NORTH, JLscore);
        JTBdoubleLetter.setCursor(hoverCursor);
        JTBtripleLetter.setCursor(hoverCursor);
        JBdoubleWord.setCursor(hoverCursor);
        JBdoubleWord.setCursor(hoverCursor);
        JBbonusUndo.setCursor(hoverCursor);
        JBaddWord.setCursor(hoverCursor);
        JBendTurn.setCursor(hoverCursor);
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

        JPplayers.add(JTname1);
        JPplayers.add(JTname2);
        JPplayers.add(JLscore1);
        JPplayers.add(JLscore2);
        JPplayers.add(JTword);

        JPword.add(JLscore);
        JPword.add(JTBdoubleLetter);
        JPword.add(JTBtripleLetter);
        JPword.add(JBdoubleWord);
        JPword.add(JBtripleWord);
        JPword.add(JBbonusUndo);
        JPword.add(JBendTurn);
        JPword.add(JBaddWord);
        JPword.add(JLmultiplier);

        getContentPane().add(JPplayers);
        getContentPane().add(JPword);
        getContentPane().add(JPoptions);

        // Action Listeners for Various Cmmponents        
        ActionListener letterBonusListener = new ActionListener() {
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
        JTBdoubleLetter.addActionListener(letterBonusListener);
        JTBtripleLetter.addActionListener(letterBonusListener);
        for (int l = 0; l < 15; l++) {
            final int index = l;
            JBtiles[l].addActionListener(
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e
                ) {
                    playedScore += multiplier * letterMultiplier * getLetterScore(JBtiles[index].getText().toCharArray()[0]);
                    JLscore.setText(Integer.toString(playedScore));
                }
            }
            );
        }
        JBbonusUndo.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                playedScore = calculateScore(playedWord, JBtiles);
                JLscore.setText(Integer.toString(playedScore));
                multiplier = 1;
                letterMultiplier = 0;
                JLmultiplier.setVisible(false);
            }
        }
        );

        JBdoubleWord.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                playedScore *= 2;
                JLscore.setText(Integer.toString(playedScore));
                multiplier *= 2;
                JLmultiplier.setText("x " + Integer.toString(multiplier));
                JLmultiplier.setVisible(true);
            }
        }
        );
        JBtripleWord.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                playedScore *= 3;
                JLscore.setText(Integer.toString(playedScore));
                multiplier *= 3;
                JLmultiplier.setText("x " + Integer.toString(multiplier));
                JLmultiplier.setVisible(true);
            }
        }
        );

        JBaddWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (turn % 2 == 0) {
                    score1 += playedScore;
                    JLscore1.setText(Integer.toString(score1));

                } else {
                    score2 += playedScore;
                    JLscore2.setText(Integer.toString(score2));
                }
                playedScore = 0;
                multiplier = 1;
                letterMultiplier = 0;
                JTBdoubleLetter.setVisible(false);
                JTBtripleLetter.setVisible(false);
                JBdoubleWord.setVisible(false);
                JBtripleWord.setVisible(false);
                JBbonusUndo.setVisible(false);
                JBaddWord.setVisible(false);
                JBendTurn.setVisible(false);
                JLscore.setVisible(false);
                JLmultiplier.setVisible(false);
                JTword.setText("Enter Word");
                for (int i = 0; i < playedWord.length(); i++) {
                    JBtiles[i].setVisible(false);
                }

            }
        }
        );

        JBendTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turn++;
                if (turn % 2 == 1) {
                    JTword.setBackground(Color.blue);
                    score1 += playedScore;
                    JLscore1.setText(Integer.toString(score1));

                } else {
                    JTword.setBackground(Color.red);
                    score2 += playedScore;
                    JLscore2.setText(Integer.toString(score2));
                }
                playedScore = 0;
                multiplier = 1;
                letterMultiplier = 0;
                JTBdoubleLetter.setVisible(false);
                JTBtripleLetter.setVisible(false);
                JBdoubleWord.setVisible(false);
                JBtripleWord.setVisible(false);
                JBbonusUndo.setVisible(false);
                JBaddWord.setVisible(false);
                JBendTurn.setVisible(false);
                JLscore.setVisible(false);
                JLmultiplier.setVisible(false);
                JTword.setText("Enter Word");
                for (int i = 0; i < playedWord.length(); i++) {
                    JBtiles[i].setVisible(false);
                }

            }
        }
        );

        JTword.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                playedWord = JTword.getText();
                playedScore = calculateScore(playedWord, JBtiles);
                JTword.setText("Modify Word");
                multiplier = 1;
                JLmultiplier.setVisible(false);
                JTBdoubleLetter.setVisible(true);
                JTBtripleLetter.setVisible(true);
                JBdoubleWord.setVisible(true);
                JBtripleWord.setVisible(true);
                JBbonusUndo.setVisible(true);
                JBendTurn.setVisible(true);
                JBaddWord.setVisible(true);
            }
        }
        );

    }

    /**
     *
     * @param word
     * @param JBtile
     * @return
     */
    public int calculateScore(String word, JButton[] JBtile) {
        int score = 0;
        int i = 0;
        for (char letter : word.toCharArray()) {
            JBtile[i].setText(String.valueOf(Character.toUpperCase(letter)));
            JBtile[i].setBackground(new Color(255, 255, 153));
            JBtile[i].setHorizontalAlignment(JButton.CENTER);
            JBtile[i].setFont(Fscrabble);
            JPword.add(JBtile[i]);
            wordLayout.putConstraint(SpringLayout.NORTH, JBtile[i], 70, SpringLayout.NORTH, JPword);
            wordLayout.putConstraint(SpringLayout.WEST, JBtile[i],
                    (getWidth() / 2 - 25 * word.length() + 50 * i),
                    SpringLayout.WEST, JPword);
            JBtile[i].setBorder(BorderFactory.createLineBorder(Color.gray, 2));
            JBtile[i].setForeground(Color.black);
            JBtile[i].setCursor(hoverCursor);
            JBtile[i].setPreferredSize(new Dimension(50, 50));
            JBtile[i].setVisible(true);
            score += getLetterScore(letter);
            i++;
        }
        for (int j = word.length(); j < 15; j++) {
            JBtile[j].setVisible(false);
        }

        JLscore.setText(Integer.toString(score));
        JLscore.setVisible(true);
        return score;
    }

    static public int getLetterScore(char letter) {
        char upperCase = Character.toUpperCase(letter);
        return LETTER_SCORES[upperCase - 'A'];
    }

    /**
     *
     * @param arg0
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
