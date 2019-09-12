/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblescoreboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author user
 */
public class JTname extends JTextField {
    
    public JTname (String name){
        this.setText(name);
        setBackground(Color.white);
        setHorizontalAlignment(JTextField.CENTER);
        setPreferredSize(new Dimension(125, 40));
        setMaximumSize(new Dimension(200, 40));
        setFont(new Font("gothic standard", Font.BOLD, 24)); 
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
                setForeground(Color.white);
            }
        });
    }
    
    
    
    
}
