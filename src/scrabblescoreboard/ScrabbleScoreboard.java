/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblescoreboard;

import javax.swing.JFrame;

/**
 *
 * @author Patrick Tweddell
 */
public class ScrabbleScoreboard {

    public static int WIDTH = 600, HEIGHT = 600;

    public static void main(String[] args) {
 
        //Create frame
        Scoreboard board = new Scoreboard(WIDTH, HEIGHT);
        board.setBounds(100, 100, WIDTH, HEIGHT);
        board.setTitle("Scrabble Scoreboard");
        board.setResizable(false);
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setVisible(true);
 
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static void setWidth(int WIDTH) {
        ScrabbleScoreboard.WIDTH = WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static void setHeight(int HEIGHT) {
        ScrabbleScoreboard.HEIGHT = HEIGHT;
    }

}
