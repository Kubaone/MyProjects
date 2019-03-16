package skj;

import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String [] args){

        try {
            //new Server(InetAddress.getLocalHost().toString(), 1111, 2222);

            //Client first =new Client("169.254.204.102", 1111);
            Client2 second =new Client2("169.254.204.102", 2222);



        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
