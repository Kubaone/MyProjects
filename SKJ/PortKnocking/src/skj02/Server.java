package skj02;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Server {

    public Server(ArrayList<Integer> ports) {
        try {
            byte[] buf = new byte[15];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            //create threads and make them wait for packets

            ExecutorService threads = Executors.newFixedThreadPool(ports.size());
            ArrayList<Callable<IPResult>> list = new ArrayList<>();
            for (Integer port : ports) {
                list.add(new MyThread(port));
                port++;
            }
            new Server(ports);

            ArrayList<IPResult> list2 = new ArrayList<>();
            //get IPResult wrapper from threads
            for (Callable<IPResult> aList : list) {
                Future<IPResult> future = threads.submit(aList);
                list2.add(future.get());
            }
            threads.shutdown();
            //check if message from many threads is valid. If it is from the same ip, open new tcp socket and send its port by udp socket
            StringBuilder sb = new StringBuilder();
            ArrayList<IPResult> tmp = new ArrayList<>(list2);

            for (int i = 0; i < tmp.size(); i++) {
                for (IPResult ip : list2) {
                    if (tmp.get(i).getIp().equals(ip.getIp())) {
                        sb.append(ip.getResult());
                        if (sb.toString().equals("Hello, can I connect?")) {
                            new Thread(() -> {

                                try {
                                    DatagramSocket udpSocket = new DatagramSocket(0, InetAddress.getLocalHost());

                                    System.out.println();
                                    ServerSocket serverSocket = new ServerSocket(0);
                                    // send port by UDP

                                    ByteBuffer bb = ByteBuffer.allocate(4);
                                    bb.putInt(serverSocket.getLocalPort());
                                    byte[] tcpPort = bb.array();
                                    DatagramPacket packet1 = new DatagramPacket(tcpPort, tcpPort.length, ip.getIp(), ip.getPort());
                                    udpSocket.send(packet1);
                                    out("tcp port " + serverSocket.getLocalPort() + " send on " + ip.getIp() + " " + ip.getPort());

                                    //make a tcp connection and exchange few words

                                    Socket tcpSocket = serverSocket.accept();
                                    out("has made a connection");
                                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(tcpSocket.getOutputStream()));
                                    BufferedReader in = new BufferedReader(
                                            new InputStreamReader(tcpSocket.getInputStream()));
                                    out("skj02.Client said: " + in.readLine());
                                    out.write("Ça va bien, merci. Ça va? \n");
                                    out.flush();
                                    out("skj02.Client said: " + in.readLine());

                                    //close everything

                                    tcpSocket.close();
                                    serverSocket.close();
                                    udpSocket.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }).start();
                            sb = new StringBuilder();

                        }
                    }
                    tmp.remove(i);
                }
            }


        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }

    public void out(String msg) {
        System.out.println("skj02.Server: " + msg);
    }
}
