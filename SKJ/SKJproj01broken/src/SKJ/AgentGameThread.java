package SKJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AgentGameThread extends Thread {
    private Socket socket;
    private String score;
    private boolean first;

    public AgentGameThread(Socket socket){
        super("AgentGameThread"); this.socket=socket;
    }

    public void run(){
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            System.out.println("Give me a number");
            Scanner sc = new Scanner(System.in);
            int player;
            while(!sc.hasNextInt()) {
                System.out.println("Not a proper number");
            }
            player = sc.nextInt();
            out.print("Give me a number");
            int opponent = in.read();
            //Od którego gracza rozpocząć odliczanie
            first = Math.random() > 0.5;
            if(first) {
                if ((player + opponent) % 2 == 0) {
                    System.out.print("You lost");
                    out.print("Won");
                }else {
                    System.out.println("You won");
                    out.print("Lost");
                }
            }else
                if((player + opponent) % 2 == 0) {
                System.out.print("You won");
                out.print("Lost");
            }else {
                System.out.println("You lost");
                out.print("Won");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String getScore(){
        return score;
    }
}
