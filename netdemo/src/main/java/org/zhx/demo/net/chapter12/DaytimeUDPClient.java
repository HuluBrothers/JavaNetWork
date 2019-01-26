package org.zhx.demo.net.chapter12;

import java.io.IOException;
import java.net.*;

public class DaytimeUDPClient {
    private final static int PORT = 13;
    //private static final String HOST = "time.nist.gov";
    private static final String HOST = "127.0.0.1";
    public static void main(String args[]){
        try{
            DatagramSocket socket = new DatagramSocket(0);
            socket.setSoTimeout(10*1000);
            System.out.println("创建一个UDP的 socket" + socket);

            InetAddress host = InetAddress.getByName(HOST);
            DatagramPacket request = new DatagramPacket(new byte[1],1,host,PORT);
            DatagramPacket response = new DatagramPacket(new byte[1024],1024);

            socket.send(request);
            socket.receive(response);

            String result = new String(response.getData(),0,response.getLength(),"US-ASCII");

            System.out.println(result);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
