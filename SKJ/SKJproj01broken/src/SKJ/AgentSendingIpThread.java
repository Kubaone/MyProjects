package SKJ;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class AgentSendingIpThread extends Thread{
    private final Socket socket;
    private final InetAddress iip;
    private final int port;
    public AgentSendingIpThread(Socket socket, InetAddress iip, int port){
        this.socket=socket;
        this.iip=iip;
        this.port=port;
    }

    public void run(){
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            //trzeba sprawdzic czy wysłanie inet adress wten sposob dziala
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(iip);
            out.print(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
