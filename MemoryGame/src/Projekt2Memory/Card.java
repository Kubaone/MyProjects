package Projekt2Memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Card extends JLabel{
    private static int isEnabled=0;
    private static ImageIcon pokeBall = new ImageIcon(System.getProperty("user.dir") + "\\src\\Projekt2Memory\\Obrazy\\pokeBall.png");
    private ImageIcon img;
    private  static ArrayList<Card> cards = new ArrayList<>();
    private boolean found=false;
    private static int counterOfFound =0;
    private static int counterOfEnabled=0;


    public Card(ImageIcon img){
        this.img=img;
        setIcon(pokeBall);
        setPreferredSize(new Dimension(pokeBall.getIconWidth(),pokeBall.getIconHeight()));
            this.setEnabled(false);
            cards.add(this);
    }
    public void addAction(Game game){
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                if (!found) {
                  if(isEnabled < 2) {
                          isEnabled++;
                          counterOfEnabled++;
                          setIcon(img);
                          setEnabled(true);
                          Timer tim = new Timer(2000, e1 -> {
                              if (!found) {
                                  setIcon(pokeBall);
                                  setEnabled(false);
                                  isEnabled--;
                              } else {
                                  isEnabled -= 1;
                                  setEnabled(false);
                              }
                          });
                          tim.setRepeats(false);
                          tim.start();

                  }
                 else {

                    try {
                        throw new TooManyCardsException();
                    } catch (TooManyCardsException e1) {

                    }
                }
                if(isEnabled == 2) {
                    for (int i = 0; i < cards.size(); i++) {
                        for (int k = i + 1; k < cards.size(); k++) {
                            if (cards.get(i).isEnabled() && cards.get(k).isEnabled())
                                if (cards.get(i).img.getDescription().equals(cards.get(k).img.getDescription())) {
                                    cards.get(i).setFound(true);
                                    cards.get(k).setFound(true);
                                    cards.get(k).removeMouseListener(this);

                                }
                        }
                        if (cards.get(i).getFound())
                            counterOfFound++;
                    }

                    if (counterOfFound == cards.size()) {
                        if (game.getMinutes() == 0)
                            new GetName((game.getGridSize() * game.getGridSize() * counterOfEnabled * 10) / (game.getSeconds()));
                        else {
                            if (game.getSeconds() == 0) {
                                new GetName(game.getGridSize() * game.getGridSize() * counterOfEnabled * 10 / game.getMinutes() * 60);
                            } else
                                new GetName(game.getGridSize() * game.getGridSize() * counterOfEnabled * 10 / (game.getSeconds() + game.getMinutes() * 60));

                        }


                        game.getTimer().stop();
                        game.dispose();
                    } else
                        counterOfFound = 0;
                    }
                }
            }@Override
            public void mouseReleased(MouseEvent e) { }@Override
            public void mouseEntered(MouseEvent e) { }@Override
            public void mouseExited(MouseEvent e) { }
        });
    }
    public void setFound(boolean bol){
        found=bol;
    }
    public boolean getFound(){
        return found;
    }


}
