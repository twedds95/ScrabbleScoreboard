/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.scrabblescoreboard.view;

import ca.scrabblescoreboard.ScrabbleScoreboardApplication;
import ca.scrabblescoreboard.controller.Controller;
import ca.scrabblescoreboard.model.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.JTextField;

/**
 *
 * @author user
 */
public class JTname extends JTextField {
    
    public JTname (String name){
        this.setText(name);
        setHorizontalAlignment(JTextField.CENTER);
        setPreferredSize(new Dimension(125, 30));
        setMaximumSize(new Dimension(200, 30));
        setFont(new Font("gothic standard", Font.BOLD, 16));
        addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                JTname.this.select(0, getText().length());
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTname.this.select(0, 0);
            }
        });
        
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = 0;
                List<Player> playerList = ScrabbleScoreboardApplication.getScoreboard().getPlayers();
                switch (JTname.this.getBackground().toString()){
                    case ("Red"):
                        index = 0;
                        break;
                    case ("Cyan"):
                        index = 1;
                        break;
                    case ("Green"):
                        index = 2;
                        break;
                    case ("Yellow"):
                        index = 3;
                        break;
                    default:
                        index = -1;
                        break;
                }
                if (index != -1){
                    Controller.changePlayerName(index, name);
                }
            }
        });
    }
    
    
    
    
}
