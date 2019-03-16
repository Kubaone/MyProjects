package SKJ;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private final Socket clientSocket;
    private ArrayList<Agent> agents = new ArrayList<>();


    public ServerThread(Socket clientSocket) {
        super("ServerThread");
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine;
            out.println("You have been connected to LobbyServer");
            // obsługa komend dostawanych od klienta
            inputLine = in.readLine();
            System.out.println(inputLine);
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            System.out.println(in.readLine());

            switch (inputLine) {
                    case "Get players":
                        sendAgents();
                        break;
                    case "Set player":
                        addAgent();
                        System.out.println(agents);
                        break;
                    case "Quit":
                        remAgent();
                        clientSocket.close();
                        break;
                        default:
                            System.out.println("error");
                            break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //wysyłanie listy agentów do agenta
    private void sendAgents() {
        try {
            ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutput.writeObject(agents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //dodanie agenta do listy aktywnych agentów
    private void addAgent() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
            Agent agent = (Agent) objectInput.readObject();
            agents.add(agent);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //usunięcie agenta z listy aktywnych agentów
    private void remAgent() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
            Agent agent = (Agent) objectInput.readObject();
            agents.remove(agent);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}