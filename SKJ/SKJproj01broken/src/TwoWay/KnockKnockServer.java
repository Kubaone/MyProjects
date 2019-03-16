package TwoWay;

import java.net.*;
import java.io.*;

public class KnockKnockServer {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
        }

        int portNumber = Integer.parseInt(args[0]);

        try{
            while (true) {
                ServerSocket serverSocket = new ServerSocket(portNumber);
                new ServerThread(serverSocket.accept()).start();
                portNumber=(int)(Math.random()*10000);
                System.out.println(portNumber);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}