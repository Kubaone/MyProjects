package Projekt2Memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;

public class GetName extends JFrame {

    public GetName(int score){
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(450,300));
        setTitle("Your Name?");
        setLayout(new BorderLayout());

        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
        jp.setBackground(Color.GREEN);


        JTextField tf = new JTextField("Enter your name here");
        tf.setMaximumSize(new Dimension(300,45));
        tf.setBackground(Color.WHITE);
        tf.setFont(new Font("Times Romes", Font.PLAIN,23));
        jp.add(Box.createRigidArea(new Dimension(100, 30)));
        tf.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        tf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }
            @Override
            public void keyPressed(KeyEvent e) {
                tf.setText("");
                tf.removeKeyListener(this);
            }

            @Override
            public void keyReleased(KeyEvent e) { }
        });
        jp.add(tf);
        jp.add(Box.createRigidArea(new Dimension(100, 25)));

        JButton jb1 = new JButton("Show me how I placed");
        jb1.setMaximumSize(new Dimension(getWidth(),getHeight()));
        jb1.setAlignmentX(JButton.CENTER_ALIGNMENT);



        jb1.addActionListener(e -> {
            try {
                FileWriter save = new FileWriter("HighScores.txt",true);
                save.write(tf.getText() + " score : " + Integer.toString(score) + "\n");
                save.flush();
                save.close();
            } catch (IOException d) {
                d.printStackTrace();
            }

            new HighScores();

            this.dispose();
        });
        jp.add(jb1);
        jp.add(Box.createRigidArea(new Dimension (100, 15)));
        JButton jb2 = new JButton("Go back to menu");
        jb2.setAlignmentX(JButton.CENTER_ALIGNMENT);
        jb2.setMaximumSize(new Dimension(getWidth(),getHeight()));
        jb2.addActionListener(e -> {
            try {
                FileWriter save = new FileWriter("HighScores.txt");
                save.write(tf.getText() + " score : " + Integer.toString(score) + "\n");
                save.close();
            } catch (IOException f) {
                f.printStackTrace();
            }
            this.dispose();
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Menu();
                }
            });
        });
        jp.add(jb2);
        jp.add(Box.createRigidArea(new Dimension (100, 15)));


        add(jp,BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);


    }
}