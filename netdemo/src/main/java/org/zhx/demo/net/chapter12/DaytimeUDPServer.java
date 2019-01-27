package org.zhx.demo.net.chapter12;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;

public class DaytimeUDPServer {
    private final  static  int PORT = 13;

    public static void main(String args[]){
        try{
            DatagramSocket socket = new DatagramSocket(PORT);
            while(true){
                DatagramPacket request = new DatagramPacket(new byte[1024],1024);
                socket.receive(request);

                String daytime = new Date().toString();
                byte[] data = daytime.getBytes("US-ASCII");
                DatagramPacket response = new DatagramPacket(data,data.length,request.getAddress(),request.getPort());
                socket.send(response);

                System.out.println(daytime + " " + request.getAddress() + ":" + request.getPort());
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
