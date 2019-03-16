package skj02;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;


public class Client {
    public Client(String ip, ArrayList<Integer> ports) {
        try {
            DatagramSocket udpSocket = new DatagramSocket();
            // send request
            String request = "Hello, can I connect?";
            for (int i = 0; i < ports.size(); i++) {
                int msgSize = request.length() / ports.size() * i;
                byte[] buf;
                InetAddress address = InetAddress.getLocalHost();
                if (i + 1 == ports.size()) {
                    msgSize = request.length() / ports.size() * (i - 1);
                    buf = request.substring(msgSize + request.length() / ports.size(), request.length()).getBytes();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), ports.get(i));
                    udpSocket.send(packet);
                } else {
                    buf = request.substring(msgSize, msgSize + request.length() / ports.size()).getBytes();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), ports.get(i));
                    udpSocket.send(packet);
                }
                Thread.sleep(1000);
            }

            //get tcp port number from server
            byte[] buf = new byte[4];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            udpSocket.setSoTimeout(500);
            udpSocket.receive(packet);
            ByteBuffer byteBuffer = ByteBuffer.wrap(packet.getData());
            int port = byteBuffer.getInt();
            System.out.println("skj02.Client: port received " + port);

            //make tcp connection and exchange few words
            ServerSocket serverSocket = new ServerSocket();
            Socket tcpSocket = new Socket(InetAddress.getByName(ip), port);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(tcpSocket.getOutputStream()));
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(tcpSocket.getInputStream()));
            out.write("Ça va? \n");
            out.flush();
            out("skj02.Server said: " + in.readLine());
            out.write("Ça va bien, merci.\n");
            out.flush();

            //close everything
            tcpSocket.close();
            serverSocket.close();
            udpSocket.close();

        } catch (SocketTimeoutException e) {
            System.err.println("Server is not responding");
            System.exit(-1);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

    public void out(String msg) {
        System.out.println("skj02.Client: " + msg);
    }
}
