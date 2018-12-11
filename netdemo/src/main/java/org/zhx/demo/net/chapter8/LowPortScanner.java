package org.zhx.demo.net.chapter8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class LowPortScanner {
    public static void main(String[] args) {

        String host = args.length > 0 ? args[0] : "47.104.183.53";

        for (int i = 1; i < 1024; i++) {
            try {
                Socket s = new Socket(host, i);
                s.setSoTimeout(200);
                System.out.println("There is a server on port " + i + " of "+ host);
                s.close();
            }  catch (IOException ex) {
                // must not be a server on this port
                System.out.println("这个端口上不是一个服务 "+ host + ":" + i);
            }
        }
    }
}
