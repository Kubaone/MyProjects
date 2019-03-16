package Projekt2Memory;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Game extends JFrame {
    private int minutes=0;
    private int seconds=0;
    private Timer clock;
    private int size;


    public Game(int size){
        this.size=size;
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Game");
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(size,size));
        String [] tab = {"abra.png","bulbasaur.png","charmander.png","diglett.png","gastly.png","geodude.png","jigglypuff.png","jynx.png",
                "machop.png","magikarp.png","mewtwo.png","pikachu.png","rattata.png","snorlax.png","squirtle.png"};
        ArrayList<ImageIcon> images= new ArrayList<>();

        Collections.shuffle(Arrays.asList(tab));
        for(int k=0,l=0; k <size*size/2; k++,l++) {
            if(l>=tab.length)
                l=0;
            ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\Projekt2Memory\\Obrazy\\" + tab[l]);
            ImageIcon copy = new ImageIcon(System.getProperty("user.dir") + "\\src\\Projekt2Memory\\Obrazy\\" + tab[l]);
            images.add(icon);
            images.add(copy);

        }
        Collections.shuffle(images);


        for(int i=0; i<size*size; i++){
            Card card=new Card(images.get(i));
            card.addAction(this);
            jp.add(card);
        }


        JPanel clockPanel = new JPanel();
        clockPanel.setPreferredSize(new Dimension(100,55));
        JLabel jl = new JLabel("0 : 00");
        clockPanel.add(jl);

        jl.setFont(new Font("Times Roman",Font.PLAIN,40));
        this.clock = new Timer(1000,e -> {
            seconds++;
            if(seconds>59) {
                minutes++;
                seconds-=60;
            }
            if(seconds<10)
                jl.setText(Integer.toString(minutes) + " : 0" + Integer.toString(seconds));
            else
                jl.setText(Integer.toString(minutes  )+ " : " + Integer.toString(seconds));

        });
           clock.start();

        add(clockPanel, BorderLayout.SOUTH);
        add(jp,BorderLayout.CENTER);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }

    public Timer getTimer(){
        return clock;
    }

    public int getMinutes(){
        return minutes;
    }
    public int getSeconds(){
        return seconds;
    }
    public int getGridSize(){
        return size;
    }


}
