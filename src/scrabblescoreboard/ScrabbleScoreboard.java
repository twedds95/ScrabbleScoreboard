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

    public static int Width = 600, Height = 600;

    public static void main(String[] args) {
 
        Scoreboard board = new Scoreboard(Width, Height);
 
        JFrame Frame = new JFrame();
        Frame.setBounds(100, 100, Width, Height);
        Frame.setTitle("Scrabble Scoreboard");
        Frame.setResizable(false);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.add(board);
        Frame.setVisible(true);
    }

    public static int getWidth() {
        return Width;
    }

    public static void setWidth(int Width) {
        ScrabbleScoreboard.Width = Width;
    }

    public static int getHeight() {
        return Height;
    }

    public static void setHeight(int Height) {
        ScrabbleScoreboard.Height = Height;
    }

}
