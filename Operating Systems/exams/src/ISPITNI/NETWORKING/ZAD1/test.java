package ISPITNI.NETWORKING.ZAD1;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class test {
    public static void main(String[] args) {
        try {
            System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            System.out.println("e");
        }
    }
}
