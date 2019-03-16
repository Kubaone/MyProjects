package SKJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class AgentListenThread extends Thread {
    private final ServerSocket serverSocket;
    private Socket outSocket;
    private String score;


    public AgentListenThread(ServerSocket serverSocket, InetAddress serverIP, int serverPort){
        super("AgentListenThread");
        this.serverSocket=serverSocket;
        try {
                outSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(outSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                outSocket.getInputStream()));
                String command=in.readLine();
                if (command.equalsIgnoreCase("play"))
                    new AgentGameThread(outSocket);
                else if(command.equals("Send iip and port of server"))
                    new AgentSendingIpThread(outSocket, serverIP, serverPort).start();
                else
                    out.print("No such command");
            serverSocket.close();
            outSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getScore(){return score;}
}
