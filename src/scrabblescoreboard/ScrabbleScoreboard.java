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
    
    public static int width = 800, height = 600;

    public static void main(String[] args) {

        //Create frame
        JFrame frame = new JFrame();
        Scoreboard board = new Scoreboard(width, height);
        frame.setBounds(100, 100, width, height);
        frame.setTitle("Scrabble Scoreboard");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(board);
        //GameSetup setup = new GameSetup();
        //frame.add(setup);

    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        ScrabbleScoreboard.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        ScrabbleScoreboard.height = height;
    }

}
