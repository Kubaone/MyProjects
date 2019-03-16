package skj;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    private ArrayList<String> Agent1 = new ArrayList<>();
    private ArrayList<String> Agent2 = new ArrayList<>();
    private ServerSocket serverSocket;
    private DatagramSocket udpSocket;
    private Socket tcpSocket;


    public Server(final String ip, int tcpPort, int udpPort) throws IOException {

        serverSocket = new ServerSocket(tcpPort);
        tcpSocket = serverSocket.accept();
        new Thread(() -> {

            synchronized (serverSocket) {
                try {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(tcpSocket.getInputStream()));
                    String msg;
                    System.out.println("flag");
                    while (!(msg = in.readLine()).equals("quit")) {
                        System.out.println("Server got from tcp : " + msg);
                        Agent2.add(msg);
                        serverSocket.notify();
                        serverSocket.wait();
                    }
                    serverSocket.close();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();



        new Thread(() -> {

            synchronized (serverSocket) {
                try {
                    String msg="";
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(tcpSocket.getOutputStream()));
                    if (!Agent1.isEmpty()) {
                        while (!Agent1.isEmpty()) {
                            msg = Agent1.get(Agent1.size() - 1);
                            Agent1.remove(msg);
                            msg += "\n";
                        }
                        out.write(msg);
                        out.flush();
                        System.out.println("Server send to tcp " + msg);
                        serverSocket.notify();
                        serverSocket.wait();
                    }
                    serverSocket.close();

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();



        new Thread(() -> {
            try {

                byte[] buf = new byte[15];
                DatagramSocket udpSocket = new DatagramSocket(udpPort, InetAddress.getLocalHost());
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                String msg = "";
                Agent1.add("random");
                while (!msg.equals("quit")) {
                    if (!Agent1.isEmpty())
                        if (!msg.equals(Agent1.get(Agent1.size() - 1))) {
                            udpSocket.receive(packet);
                            msg = new String(buf, 0, packet.getLength());
                            System.out.println("Server got " + msg + " from udp");
                            Agent1.add(msg);

                            //wysylanie
                            if (!Agent2.isEmpty()) {
                                while (!Agent2.isEmpty()) {
                                    msg = Agent2.get(Agent2.size() - 1);
                                    Agent2.remove(msg);
                                    msg += "\n";
                                }
                                buf = msg.getBytes();
                                packet = new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort());
                                udpSocket.send(packet);
                                System.out.println("Server send " + msg + " to udp");

                            }
                        }
                }
                udpSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }).start();

    }

}
