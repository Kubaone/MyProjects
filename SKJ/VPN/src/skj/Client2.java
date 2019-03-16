package skj;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client2 {
    private String ip;
    private int port;
    private final DatagramSocket udpSocket;

    public Client2(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        this.udpSocket = new DatagramSocket();


        new Thread(() -> {
            try {
                listen();
                udpSocket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                talk();
                udpSocket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();





    }

    public void talk() throws IOException, InterruptedException {

        String msg;


        synchronized (udpSocket) {

            do {
                Scanner sc = new Scanner(System.in);
                msg=sc.next();
                byte[] buf = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), port);
                udpSocket.send(packet);
                System.out.println("UDP send " + msg);
                udpSocket.notify();
                udpSocket.wait();
            }
            while (!msg.equals("quit"));
        }
    }


    public void listen() throws IOException, InterruptedException {

        byte[] buf = new byte[64];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        synchronized (udpSocket) {
            String msg;
            do {
                System.out.println("UDP is listening");
                udpSocket.receive(packet);
                msg = new String(buf, 0, packet.getLength());
                System.out.println("UDP received " + msg);
                udpSocket.notify();
                udpSocket.wait();

            }
            while (!msg.equals("quit"));
        }

    }
}
