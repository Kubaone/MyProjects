package Projekt2Memory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HighScores extends JFrame {

    public HighScores() {
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("High Scores");

        JPanel jp = new JPanel();
        jp.setPreferredSize(new Dimension(700, 500));
        jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));

        FileReader file = null;
        try {
            file = new FileReader("HighScores.txt");
        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku z wynikami, może jeszcze nie rozegrałeś żadnej gry?");
        }
        BufferedReader bfr = new BufferedReader(file);
        ArrayList array= new ArrayList();
        String line;
        try {
            while((line=bfr.readLine()) != null){
                array.add(line);
            }
        } catch (IOException e) {
            System.out.println("BŁĄD ODCZYTU Z PLIKU!");
        }

        JList list = new JList(array.toArray());


        list.setCellRenderer(new MyListCellRenderer());

        JScrollPane scroll = new JScrollPane(list);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        list.setPreferredSize(new Dimension(500, 500));
        list.setFont(new Font("Times Roman", Font.PLAIN, 20));

        JButton menu = new JButton("Go to menu");
        menu.addActionListener(e -> {
            new Menu();
            this.dispose();
        });
        menu.setFont(new Font ("Times Roman",Font.PLAIN,24));
        menu.setPreferredSize(new Dimension(50,60));
        this.add(menu, BorderLayout.SOUTH);
        jp.add(scroll, list);
        add(jp);
        pack();
        setLocationRelativeTo(null);

    }

}