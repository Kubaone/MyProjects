package skj02;

import java.util.ArrayList;

public class ServerMain {

    public static void main(String[] args) {

        ArrayList<Integer> portList = new ArrayList<>();
        //check if arguments are valid port numbers
        for (int i = 0; i < args.length; i++) {
            if (args[i].matches("\\d+")) {
                if (!portList.contains(Integer.parseInt(args[i])) && Integer.parseInt(args[i]) > 1024)
                    portList.add(Integer.parseInt(args[i]));
            } else {
                System.out.println("Wrong arguments");
                System.exit(1);
            }
        }
        new Server(portList);

    }
}
