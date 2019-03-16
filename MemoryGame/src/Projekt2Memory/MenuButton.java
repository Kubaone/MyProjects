package Projekt2Memory;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class MenuButton extends JButton{
    public MenuButton(String text, Container pane){
        this.setText(text);

        this.setMaximumSize(new Dimension(150,60));
        this.setAlignmentX(JButton.CENTER_ALIGNMENT);

        pane.add(this);
    }

    public void NewGameAction(JFrame owner){
        this.addActionListener(e -> {
            JDialog jd = new JDialog(owner, "New Game");
            jd.setVisible(true);
            jd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jd.setPreferredSize(new Dimension(350,200));
            jd.setLayout(new BorderLayout());

            JPanel jp1 = new JPanel();
            jp1.setLayout(new BoxLayout(jp1, BoxLayout.X_AXIS));

            JLabel jl = new JLabel("Select grid size :",JLabel.CENTER);
            jl.setFont(new Font("Times Roman",Font.PLAIN,24));
            jl.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            jp1.add(Box.createRigidArea(new Dimension(20, 0)));

            String [] tab={ "2x2","4x4","6x6","8x8"};
            JComboBox<String> jcb = new JComboBox<>(tab);
            jcb.setMaximumSize(new Dimension(100,40));

            JButton play = new JButton("Play");
            play.addActionListener(e1 -> {
                String [] parts=jcb.getSelectedItem().toString().split("x");
                new Game(Integer.parseInt(parts[0]));
                owner.dispose();

                jd.dispose();
            });

            jp1.add(jl);
            jp1.add(Box.createRigidArea(new Dimension(20, 0)));
            jp1.add(jcb);
            jd.add(jp1,BorderLayout.CENTER);
            jd.add(play,BorderLayout.SOUTH);
            jd.pack();
            jd.setLocationRelativeTo(null);

        });
    }
    public void HighScoresAction(Frame owner){
        this.addActionListener(e -> {
            new HighScores();
            owner.dispose();

        });

    }
    public void ExitAction(JFrame frame){
        this.addActionListener(e -> {
            frame.dispose();
        });
    }

}
