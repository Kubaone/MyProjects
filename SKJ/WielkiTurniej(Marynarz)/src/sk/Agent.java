package sk;


import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Agent {
    private String name;
    private InetAddress IP;
    private int port;
    private Socket socket;
    private ServerSocket serverSocket;
    private HashMap<IPPort, Boolean> players = new HashMap<>();
    private HashMap<String, String> scores = new HashMap<>();

    //Stworzenie obiektu agenta, inicjalizacja jego pol( w tym serverSocket)
    public Agent(String name, int port) throws IOException {
        IP = InetAddress.getLocalHost();
        this.port = port;
        this.name = name;
        serverSocket = new ServerSocket(port, 0, IP);

    }

    /**
     * Start programu, jezeli podamy 2 argumenty(Nazwe klienta oraz jego port do nasluchu) to oznacza ze jest to pierwszy klient ( w grze jeszcze nia ma zadnego innego)
     * i bedzie on od poczatku tylko nasluchiwal nadchodzacych polączen
     * Jezeli podamy 4 argumenty(Nazwe klienta, jego port do nasluchu, IP istniejacego gracza oraz port istniejacego gracza) to dolaczy on do rozgrywki, zostana uruchomione
     * metody join() oraz play() (opis metod nad ich deklaracja)
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length == 2) {
            new Agent(args[0], Integer.parseInt(args[1])).listen();
        } else if (args.length == 4) {
            Agent agent = new Agent(args[0], Integer.parseInt(args[1]));
            agent.join(args[2], Integer.parseInt(args[3]));
            agent.listen();
        }
    }

    /**
     * Metoda nasluchujaca, przy każdym obrocie petli pyta uzytownika czy chce nadal grac, jesli nie to przerywa petle oraz drukuje wyniki wszystkich dotychczasowych gier,
     * jesli chce grac to gracz przechodzi w tryb nasluchiwania polaczen. Po otrzymaniu polaczenia klient,pobiera ip, wysyla swoja mape graczy nowo polączonemu klientowi, potem klient nastepujaco:
     * 0.Pobiera port gracza1.Losuje swoją liczbe. 2.Odbiera od gracza liczbe. 3.Odbiera od gracza jego nazwę. 4.Wysyla mu swoja nazwe. 5.Przeprowadza odliczanie.
     * 6.Zapisuje wynik w mapie scores(nazwa gracza,wynik) 7.Wysyla wynik swojemu przeciwnikowi. 8. Wrzuca przeciwnika do mapy players z oznaczeniem,
     * ze rozegral z nim gre. 9. Zamyka wszystkie potoki oraz polaczenie
     */

    public void listen() throws IOException {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Write QUIT if you want to leave, write anything else if you " +
                    "want to wait for other players.");
            if (sc.nextLine().equals("QUIT")) {
                if (scores.isEmpty())
                    System.out.println("No scores available");
                else
                    System.out.println(scores);
                break;
            }

            System.out.println("Player " + name + " with ip: " + serverSocket.getInetAddress().getHostAddress()
                    + " is waiting on port : " + serverSocket.getLocalPort());
            socket = serverSocket.accept();
            String opponentIp = socket.getInetAddress().getHostAddress();
            System.out.println("Server " + serverSocket.getInetAddress().getHostAddress() + " has made a connection with "
                    + socket.getInetAddress().getHostAddress());

            ObjectOutputStream outObj = new ObjectOutputStream(socket.getOutputStream());
            outObj.writeObject(players);
            outObj.flush();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            //Rozpoczęcie gry
            String opPort = in.readLine();
            int myNumber = (int) (Math.random() * 100);
            int opponentNumber = in.read();
            String opName = in.readLine();
            boolean first;
            first = Math.random() > 0.5;
            out.write(this.name + "\n");
            out.flush();
            if (first) {
                if ((myNumber + opponentNumber % 2) == 0) {
                    scores.put(opName + " " + opponentIp, "Game lost");
                    out.write("Game won" + "\n");
                } else {
                    scores.put(opName + " " + opponentIp, "Game won");
                    out.write("Game lost" + "\n");
                }
            } else {
                if ((myNumber + opponentNumber % 2) == 0) {
                    scores.put(opName + " " + opponentIp, "Game won");
                    out.write("Game lost" + "\n");
                } else {
                    scores.put(opName + " " + opponentIp, "Game lost");
                    out.write("Game won" + "\n");
                }
            }
            out.flush();
            System.out.println(scores);
            players.put(new IPPort(opponentIp, Integer.parseInt(opPort)), true);
            socket.close();
            out.close();
            in.close();
        }

    }

    /**
     * Metoda laczy nas z podanym jako argumenty klientem (próbuje się połączyć 3 razy, jeśli się nie uda daje komunikat że gracz wyszedł i usuwa go z listy graczy)
     * , pobiera od niego mape graczy, zmienia wartosci graczy w mapie na false (jeszcze nie rozegral z nimi gry)
     * Wywoluje metode play()
     */
    public void join(String ip, int port) throws IOException, ClassNotFoundException, InterruptedException {
        for(int i=0; i<3; i++) {
            try {
                socket = new Socket(InetAddress.getByName(ip), port);
                ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
                players = ((HashMap<IPPort, Boolean>) inObj.readObject());
                for (Map.Entry<IPPort, Boolean> entry : players.entrySet()) {
                    entry.setValue(false);
                }
                this.play(ip, port);
                inObj.close();
                i=3;
            } catch (ConnectException e) {
                if(i==2) {
                    System.out.println("Couldn't connect with " + ip + ", player has already left the game.");
                    players.remove(new IPPort(ip,port));
                    System.out.println(players);
                }
                else {
                    System.out.println("Player: " + ip + " is not responding, trying to connect again.");
                    Thread.sleep(1000);
                }
            }
        }

    }

    /**
     * Metoda zmienia wartosc socket na nowe(jesli port się rozni). Laczy nas z nasluchujacym agentem(próbuje się połączyć 3 razy,
     * jeśli się nie uda daje komunikat że gracz wyszedł i usuwa go z listy graczy), pobiera od niego mape graczy oraz jego port,wysyla mu losowa liczbe,
     * nazwe naszego gracza. Po czym dostaje nazwe przeciwnika oraz wynik rozegranej gry, wrzuca go do mapy scores oraz wrzuca dane gracza do mapy player z
     * parametrem true (ponieważ właśnie rozegrał z nim gre). Na koniec uruchamia się rekurencyjnie dla wszystkich graczy z mapy players z ktorymi
     * jeszcze nie zagral oraz zamyka socket i potoki.
     */
    public void play(String ip, int port) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("Trying to connect on port:" + port);
        //zmiana wartości socketu jeśli port się różni oraz odebranie niepotrzebnej listy graczy
        if (socket.getPort() != port) {
            for (int i = 0; i < 3; i++) {
                try {
                    socket = new Socket(ip, port);
                    i=3;
                } catch (ConnectException e) {
                    if (i == 2) {
                        System.out.println("Couldn't connect with " + ip + ", player has already left the game.");
                        players.remove(new IPPort(ip,port));
                        System.out.println(players);
                        break;
                    }
                    else {
                        System.out.println("Player: " + ip + " is not responding, trying to connect again.");
                        Thread.sleep(1000);
                    }
                }
            }
            ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
            inObj.readObject();
        }
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        //Rozpoczęcie gry
        out.write(this.port + "\n");
        out.flush();
        String opIp = socket.getInetAddress().getHostAddress();
        socket.getOutputStream().write((int) (Math.random() * 100));
        socket.getOutputStream().flush();
        out.write(this.name + "\n");
        out.flush();
        String opName = in.readLine();
        String result = in.readLine();
        scores.put(opName + " " + opIp, result);
        System.out.println(scores);
        players.put(new IPPort(opIp, port), true);

        for (Map.Entry<IPPort, Boolean> entry : players.entrySet()) {
            if (entry.getKey().getIp().equals(opIp) && entry.getKey().getPort() == port)
                entry.setValue(true);
            if (!entry.getValue()) {
                try {
                    this.play(entry.getKey().getIp(), entry.getKey().getPort());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        socket.close();
        out.close();
        in.close();
    }

}


