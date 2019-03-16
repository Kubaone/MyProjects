package skj;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private String ip;
    private  int port;
    private ServerSocket serverSocket;
    private final Socket tcpSocket;


    public Client(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        this.serverSocket = new ServerSocket();
        this.tcpSocket = new Socket(InetAddress.getByName(ip), port);

        new Thread(() -> {
            try {
                talk();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();



        new Thread(() -> {
            try {
                listen();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }
    public void talk() throws IOException, InterruptedException {

        synchronized (tcpSocket) {
            String msg;
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(tcpSocket.getOutputStream()));
            do {
                //msg="Hello \n";
                Scanner sc = new Scanner(System.in);
                msg=sc.next()+ "\n";
                out.write(msg);
                out.flush();
                System.out.println("tcp send: " + msg);
                tcpSocket.notify();
                tcpSocket.wait();
            }while (!msg.equals("quit"));
        }
    }


    public void listen() throws IOException, InterruptedException {

        synchronized (tcpSocket){
            String msg;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(tcpSocket.getInputStream()));
            System.out.println("tcp is listening");
            while (!(msg=in.readLine()).equals("quit")) {
                System.out.println("tcp got msg: " + msg);
                tcpSocket.notify();
                tcpSocket.wait();
            }
        }

    }
}
