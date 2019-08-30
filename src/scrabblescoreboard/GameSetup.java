/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblescoreboard;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Patrick Tweddell
 */
public class GameSetup extends JPanel implements ActionListener {

    String[] numberPlayers = {"2", "3", "4"};
    JLabel JLnumPlayers = new JLabel("Number of Players:", JLabel.CENTER);
    JTextField[] JTplayers = new JTextField[4];
    JList JnumPlayers = new JList(numberPlayers);

    JButton JBreset = new JButton("Reset"),
            JBstart = new JButton("Start Game");

    public GameSetup() {

        GridLayout layout = new GridLayout(0, 2);
        setLayout(layout);

        add(JLnumPlayers);
        add(JnumPlayers);

        for (int i = 0; i < 4; i++) {
            JTplayers[i] = new JTextField();
            JTplayers[i].setText("Player " + 1 + i);
            add(JTplayers[i]);
        }

        JBreset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameSetup();
            }
        });

        JBstart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Scoreboard(getWidth(), getHeight());
                
            }
        });
        
        add(JBstart);
        add(JBreset);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
