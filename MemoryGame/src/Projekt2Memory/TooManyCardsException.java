package Projekt2Memory;

public class TooManyCardsException extends Exception {
    public TooManyCardsException(){
        System.out.println("Nie oszukuj! Nie możesz odkryć więcej niż dwóch kart na raz.");
    }
}
