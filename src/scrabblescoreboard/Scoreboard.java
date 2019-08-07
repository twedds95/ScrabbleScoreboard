/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblescoreboard;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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

    JPanel JPplayers = new JPanel(),
            JPword = new JPanel(),
            JPoptions = new JPanel();

    SpringLayout playersLayout = new SpringLayout(),
            wordLayout = new SpringLayout(),
            optionsLayout = new SpringLayout();

    JTextField JTname1 = new JTextField("Player 1", 15),
            JTname2 = new JTextField("Player 2", 15),
            JTword = new JTextField("Enter word", 15);

    JLabel JLscore = new JLabel(),
            JLscore1 = new JLabel("0"),
            JLscore2 = new JLabel("0");

    JButton[] JBtiles = new JButton[15],
            JBbonus = new JButton[4];

    private int score1 = 0;
    private int score2 = 0;
    private int turn = 0;

    public void paint(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(1, 1, getWidth(), getHeight());
    }

    public Scoreboard(int width, int height) {
        //Create frame
        setBounds(100, 100, width, height);
        setTitle("Scrabble Scoreboard");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //Set layout for panels
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
        JTname1.setSize(100, 40);
        JTname2.setSize(100, 40);
        playersLayout.putConstraint(SpringLayout.WEST, JTname1,
                width / 3,
                SpringLayout.WEST, getContentPane());
        playersLayout.putConstraint(SpringLayout.EAST, JTname2,
                width / 3,
                SpringLayout.EAST, getContentPane());
        playersLayout.putConstraint(SpringLayout.NORTH, JTname1,
                10,
                SpringLayout.NORTH, getContentPane());
        playersLayout.putConstraint(SpringLayout.NORTH, JTname2,
                10,
                SpringLayout.NORTH, getContentPane());
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
        JLscore1.setFont(Fscrabble);
        JLscore2.setFont(Fscrabble);
        JLscore1.setBorder(BorderFactory.createLineBorder(Color.red, 3));
        JLscore2.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
        JLscore1.setHorizontalAlignment(JLabel.CENTER);
        JLscore2.setHorizontalAlignment(JLabel.CENTER);
        playersLayout.putConstraint(SpringLayout.WEST, JLscore1,
                width / 3,
                SpringLayout.WEST, getContentPane());
        playersLayout.putConstraint(SpringLayout.EAST, JLscore2,
                width / 3,
                SpringLayout.EAST, getContentPane());
        playersLayout.putConstraint(SpringLayout.NORTH, JLscore1,
                10,
                SpringLayout.SOUTH, JTname1);
        playersLayout.putConstraint(SpringLayout.NORTH, JLscore1,
                10,
                SpringLayout.SOUTH, JTname2);

        JTword.setBackground(Color.red);
        JTword.setHorizontalAlignment(JTextField.CENTER);
        wordLayout.putConstraint(SpringLayout.NORTH, JTword,
                10,
                SpringLayout.NORTH, JPword);
        JTword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pword = JTword.getText();
                int pscore = calculateScore(pword, JBtiles);
                JTword.setText("Modify Word");

            }
        });
        // for action listener of Confirm button
//          turn++;
//        if (turn % 2 == 1) {
//                    word.setBackground(Color.blue);
//                    score1 += calculateScore(pword);
//
//                } else {
//                    word.setBackground(Color.red);
//                    score2 += calculateScore(pword);
//                }

        JPplayers.add(JTname1);
        JPplayers.add(JTname2);
        JPplayers.add(JLscore1);
        JPplayers.add(JLscore2);
        add(JPplayers);
        JPplayers.setVisible(true);
    }

    public int calculateScore(String word, JButton[] JBtile) {
        int score = 0;
        int i = 0;
        for (char letter : word.toCharArray()) {
            JBtile[i].setText(String.valueOf(Character.toUpperCase(letter)));
            JBtile[i].setBackground(new Color(255, 255, 153));
            JBtile[i].setHorizontalAlignment(JButton.CENTER);
            JBtile[i].setFont(Fscrabble);
            wordLayout.putConstraint(SpringLayout.NORTH, JBtile[i], 10, SpringLayout.SOUTH, JTword);
            JBtile[i].setBounds(getWidth() / 2 - 25 * word.length() + 50 * i, getHeight() / 3 + 100, 50, 50);
            JBtile[i].setBorder(BorderFactory.createLineBorder(Color.gray, 2));
            JBtile[i].setForeground(Color.black);
            JBtile[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            JBtile[i].setVisible(true);
            add(JBtile[i]);
            JBtile[i].setVisible(true);
            score += getLetterScore(letter);
            i++;
        }
        for (int j = word.length(); j < 15; j++) {
            JBtile[j].setVisible(false);
        }

        JLscore.setText(Integer.toString(score));
        JLscore.setBackground(Color.white);
        JLscore.setHorizontalAlignment(JTextField.CENTER);
        JLscore.setFont(Fscrabble);
        JLscore.setBounds(getWidth() / 2 - 25, getHeight() / 3 + 200, 50, 50);
        JLscore.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        JLscore.setForeground(Color.black);
        add(JLscore);
        return score;
    }

    private int getLetterScore(char letter) {
        char upperCase = Character.toUpperCase(letter);
        return LETTER_SCORES[upperCase - 'A'];
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
