package SKJ;

import java.io.IOException;

import java.net.ServerSocket;



public class LobbyServer {
    private ServerSocket serverSocket;
    private int port;
    private String name;
    private boolean listening=true;

    public LobbyServer(int port, String name) {
        this.port = port;
        this.name = name;
        try {
            this.serverSocket = new ServerSocket(port);
            while(listening)
                new ServerThread(serverSocket.accept()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LobbyServer ls = new LobbyServer(Integer.parseInt(args[0]), "Kuba");

    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }
}
