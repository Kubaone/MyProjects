package SKJ;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Agent2 implements Externalizable{
    private static boolean first=true;
    private String name;
    private int port;
    private InetAddress iip;
    private int iport;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Socket outSocket;
    private HashMap<Agent2,ArrayList<String>> scores = new HashMap<>();
    private ArrayList<Agent2> agents = new ArrayList<>();
    private int serverPort;
    private InetAddress serverIP;

    public Agent2(){}
    public Agent2(String name, int port, InetAddress iip, int iport) {
        this.name = name;
        this.port = port;
        this.iip = iip;
        this.iport = iport;/*
        try {
            serverSocket = new ServerSocket(port, 0, InetAddress.getByName("localhost"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    public static void main(String[] args) {
        try {
            Agent2 a = new Agent2("Agent2", 7000, InetAddress.getByName("localhost"), 6000);

            int decision=-1;
            Scanner sc = new Scanner(System.in);
            while (decision!=0) {
                System.out.println("What do you want to do?\nPress 0 to quit\nPress 1 to join lobby\nIf you are already in a lobby:\nPress 2 to play with someone ");
                if(sc.hasNextInt()){
                    decision=sc.nextInt();
                    switch(decision){
                        case 0:
                            //if który sprawdza czy rozegrał z każdym
                            for (Agent2 ag : a.agents){
                                if(!a.scores.containsKey(ag)) {
                                    decision = -2;
                                    System.err.println("You can't leave, u didnt play a game with " + ag.toString());
                                }
                            }
                            break;
                        case 1:
                            //pobranie listy graczy
                            //sprawdzenie czy na starej liście jest jakiś gracz, którego nie ma na nowej.
                            // Jeśli tak to usuwa obiekt tego gracza z wyników
                            //sprawdzenie czy już nie jest połączony
                            if(!a.agents.contains(a)){
                                if(!first)
                                    a.join();
                                a.joinLobby();
                                first=false;
                            }
                            break;
                        case 2:
                            //zaczęcie rozgrywki
                            System.out.println("Write name of player with who you want to play with?");
                            System.out.println(a.agents);
                            String name=sc.nextLine();
                            if(a.agents.toString().contains(name)) {
                                System.out.println("And now write his port ");
                                String port=sc.nextLine();
                                if(a.agents.toString().contains(port)){}
                                    /*new AgentGameThread(new Socket(name, port));
                                    zrobić z String InetAdress, pytanie jak?
                                     */
                                 else System.out.println("There is no player with such port");
                            }else System.out.println("There is no player with such name");
                            break;
                        case 3:
                            System.out.println(a.agents);
                        default:
                            System.out.println("No such command, try again!");
                   }
                }else {
                    System.out.println("Wrong number");
                    break;
                }
            }
            /*while(socket.isOpen()){
                switch(command):
                    case getAgents itp.
             */
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    // połączenie się z innym graczem w celu pobrania ip servera oraz portu
    public void join() {
        try {
            clientSocket = new Socket(iip, iport);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            out.print("Send iip and port of server");
            //serverIP= jak pobrać InetAdress sprawdzic czy dziala, rowniez w AgentSendingThread
            ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
            serverIP = (InetAddress) objectInput.readObject();
            serverPort=Integer.parseInt(in.readLine());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about player " + iip);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    iip);
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void joinLobby(){
        try {
            if (first)
                outSocket = new Socket(iip, iport);
            else
                outSocket = new Socket(serverIP, serverPort);
            this.sendAgent();
            //this.setAgents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //czekanie na połączenie, jeśli nadejdzie to obsługa komend (albo wysłanie InetAdress oraz portu servera albo rozpoczęcie gry) w nowym wątku
    public void listen() {
            //bez sensu bo nie wyjdzie z tęj pętli i zablokuje agenta
            // zrobić tak, że a outSocket = serverSocket.accept(); jest w innym wątku (on sam uruchamia nowe wątki)
            //później jeśli inGame zmieni się na false to wykombinować coś z interruptem threada który nasłuchuje
        try {
            serverSocket=new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AgentListenThread(serverSocket,serverIP,serverPort);

    }

    //pobranie listy Agentów z LobbyServer oraz sprawdzenie czy na starej liście jest jakiś gracz, którego nie ma na nowej.
    //Jeśli tak to usuwa obiekt tego gracza z wyników
    public void setAgents() {
        try {
            PrintWriter out = new PrintWriter(outSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(outSocket.getInputStream()));
            System.out.println(in.readLine());
            out.print("Get players");
            System.out.println("flag2");
            ObjectInputStream objectInput = new ObjectInputStream(outSocket.getInputStream());
            ArrayList<Agent2> agents2= (ArrayList<Agent2>) objectInput.readObject();
            System.out.println("flag3");
            agents2.forEach(obj -> {
                if(!agents.contains(obj))
                    scores.remove(obj);
            });
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // wysłanie obiektu tego Agenta do LobbyServer
    public void sendAgent() {
        try {
            PrintWriter out = new PrintWriter(outSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(outSocket.getInputStream()));
            System.out.println(in.readLine());
            out.print("Set player");
            out.print("Set player");
            out.print("Set player");
            ObjectOutputStream objectOutput = new ObjectOutputStream(outSocket.getOutputStream());
            objectOutput.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void quit(){
        //if(scores containsAll Agents)
        try {
            PrintWriter out = new PrintWriter(outSocket.getOutputStream(), true);
            out.print("Quit");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}
