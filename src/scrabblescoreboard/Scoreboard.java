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
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Patrick Tweddell
 */
public class Scoreboard extends JPanel implements ActionListener {

    /* A = 1, B = 3, C = 3, etc. */
    private static final int[] LETTER_SCORES = {
        1, 3, 3, 2, 1, 4, 2, 4, 1, 8,
        5, 1, 3, 1, 1, 3, 10, 1, 1, 1,
        1, 4, 4, 8, 4, 10};

    Font Fscrabble = new Font("gothic standard", Font.BOLD, 24);

    JTextField JTname1 = new JTextField("Player 1", 15);
    JTextField JTname2 = new JTextField("Player 2", 15);
    JTextField JTword = new JTextField("Enter word", 15);
    JTextField JTscore = new JTextField();
    JTextField JTscore1 = new JTextField("0");
    JTextField JTscore2 = new JTextField("0");
    JButton[] JBtiles = new JButton[15];

    private int score1 = 0;
    private int score2 = 0;
    private int turn = 0;

    public void paint(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(1, 1, getWidth(), getHeight());
    }

    public Scoreboard(int width, int height) {

        setLayout(null);

        for (int i = 0; i < 15; i++) {
            JBtiles[i] = new JButton();
        }

        JTname1.setBackground(Color.red);
        JTname2.setBackground(Color.blue);
        JTname1.setHorizontalAlignment(JTextField.CENTER);
        JTname2.setHorizontalAlignment(JTextField.CENTER);
        JTname1.setBounds(width / 4 - 60, 20, 120, 40);
        JTname2.setBounds(width * 3 / 4 - 60, 20, 120, 40);
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
        JTscore1.setBackground(Color.white);
        JTscore2.setBackground(Color.white);
        JTscore1.setEditable(false);
        JTscore2.setEditable(false);
        JTscore1.setEnabled(false);
        JTscore2.setEnabled(false);
        JTscore1.setDisabledTextColor(Color.black);
        JTscore2.setDisabledTextColor(Color.black);
        JTscore1.setFont(Fscrabble);
        JTscore2.setFont(Fscrabble);
        JTscore1.setBorder(BorderFactory.createLineBorder(Color.red, 3));
        JTscore2.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
        JTscore1.setHorizontalAlignment(JTextField.CENTER);
        JTscore2.setHorizontalAlignment(JTextField.CENTER);
        JTscore1.setBounds(width / 4 - 25, 70, 50, 50);
        JTscore2.setBounds(3 * width / 4 - 25, 70, 50, 50);

        add(JTname1);
        add(JTname2);
        add(JTscore1);
        add(JTscore2);
        
        JTword.setBackground(Color.red);
        JTword.setBounds(width / 2 - 50, height / 5, 100, 50);
        JTword.setHorizontalAlignment(JTextField.CENTER);
        add(JTword);
        JTword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turn++;
                String pword = JTword.getText();
                int pscore = calculateScore(pword, JBtiles);
                JTword.setText("Modify Word");
       
            }
        });
        // for action listener of Confirm button
        //                if (turn % 2 == 1) {
//                    word.setBackground(Color.blue);
//                    score1 += calculateScore(pword);
//
//                } else {
//                    word.setBackground(Color.red);
//                    score2 += calculateScore(pword);
//                }
    }

    public int calculateScore(String word, JButton[] JBtile) {
        int score = 0;
        int i = 0;
        for (char letter : word.toCharArray()) {
            JBtile[i].setText(String.valueOf(Character.toUpperCase(letter)));
            JBtile[i].setBackground(new Color(255, 255, 153));
            JBtile[i].setHorizontalAlignment(JButton.CENTER);
            JBtile[i].setFont(Fscrabble);
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

        JTscore.setText(Integer.toString(score));
        JTscore.setBackground(Color.white);
        JTscore.setHorizontalAlignment(JTextField.CENTER);
        JTscore.setFont(Fscrabble);
        JTscore.setBounds(getWidth() / 2 - 25, getHeight() / 3 + 200, 50, 50);
        JTscore.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        JTscore.setForeground(Color.black);
        JTscore.setEditable(false);
        add(JTscore);
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
