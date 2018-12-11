package org.zhx.demo.net.chapter8;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class DaytimeClient {

    public static void main(String args[]){
        String hostname = args.length > 0 ? args[0] : "time.nist.gov";
        int hostPort = 13;
        Socket socket = null;

        try{
            socket = new Socket(hostname,hostPort);
            socket.setSoTimeout(15*1000);
            InputStream in = socket.getInputStream();
            Reader reader = new InputStreamReader(new BufferedInputStream(in),"ASCII");
            //Reader reader = new InputStreamReader(in,"ASCII");
            StringBuilder time = new StringBuilder("REV:");
            int c = -1;
            while( (c = reader.read())!= -1 ){
                time.append((char) c);
            }

            System.out.print(time.toString());
            in.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
