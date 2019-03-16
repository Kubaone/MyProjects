package sk;

import java.io.Serializable;
import java.util.Objects;

//Klasa przechowuje ip oraz porty

public class IPPort implements Serializable {
    private String ip;
    private int port;

    public IPPort(String ip,int port) {
        this.ip = ip;
        this.port=port;
    }

    @Override
    public String toString() {
        return "Player : " + ip + " port: " + Integer.toString(port);
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IPPort ipPort = (IPPort) o;
        return port == ipPort.port &&
                Objects.equals(ip, ipPort.ip);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ip, port);
    }
}
