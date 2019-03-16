package skj02;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Callable;

public class MyThread implements Callable<IPResult> {
    private int port;
    private StringBuilder result;
    private InetAddress ip;

    public MyThread(int port) {
        this.port = port;
    }

    @Override
    public IPResult call() throws Exception {
        // receive packet from Client, save it's port, ip and message
        try {
            byte[] buf = new byte[15];
            StringBuilder result = new StringBuilder();
            DatagramSocket udpSocket2 = new DatagramSocket(port, InetAddress.getLocalHost());
            DatagramPacket packet2 = new DatagramPacket(buf, buf.length);
            udpSocket2.receive(packet2);
            port = packet2.getPort();
            ip = packet2.getAddress();
            for (int i = 0; i < packet2.getLength(); i++)
                result.append((char) packet2.getData()[i]);
            this.result = result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return new IPResult class object with necessary info
        System.out.println("Thread ended with result :" + result);
        return new IPResult(ip, result, port);

    }
}