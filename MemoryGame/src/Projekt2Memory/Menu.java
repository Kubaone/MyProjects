package Projekt2Memory;

import javax.swing.*;

import java.awt.*;

public class Menu extends JFrame {

    public Menu(){
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Memory The Game");

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setPreferredSize(new Dimension(600,150));
        text.setBackground(Color.WHITE);

        text.add(Box.createRigidArea(new Dimension(0, 23)));

        JLabel jl1 = new JLabel("Memory The Game",JLabel.CENTER);
        jl1.setFont(new Font("Times Roman", Font.PLAIN,30));
        jl1.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        text.add(jl1);

        text.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel jl2= new JLabel("s16776",JLabel.CENTER);
        jl2.setFont(new Font("Times Roman", Font.PLAIN,30));
        jl2.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        text.add(jl2);

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons,BoxLayout.Y_AXIS));
        buttons.setPreferredSize(new Dimension(600,300));
        buttons.setBackground(Color.WHITE);

        MenuButton jb1 = new MenuButton("New Game", buttons);
        jb1.NewGameAction(this);
        buttons.add(Box.createRigidArea(new Dimension(0, 20)));
        MenuButton jb2 = new MenuButton("High Scores", buttons);
        jb2.HighScoresAction(this);
        buttons.add(Box.createRigidArea(new Dimension(0, 20)));
        MenuButton jb3 = new MenuButton("Exit", buttons);
        jb3.ExitAction(this);


        add(text);
        add(buttons,BorderLayout.PAGE_END);
        pack();
        setLocationRelativeTo(null);

    }



}
