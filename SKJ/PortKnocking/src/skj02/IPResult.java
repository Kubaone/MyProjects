package skj02;

import java.net.InetAddress;

//this class is used as a wrapper for ip, port and message of packet
public class IPResult {
    private InetAddress ip;
    private StringBuilder result;
    private int port;

    public InetAddress getIp() {
        return ip;
    }

    public StringBuilder getResult() {
        return result;
    }

    public int getPort() {
        return port;
    }

    public IPResult(InetAddress ip, StringBuilder result, int port) {
        this.ip = ip;
        this.result = result;
        this.port = port;
    }
}
