package org.zhx.demo.net.chapter8;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Daytime {
    public static String hostname = "time.nist.gov";
    public static int hostPort = 13;


    public static void main(String args[]) {
        Date date = getDateFromNetwork();
        if(date != null)
            System.out.println(date.toString());
        else
            System.out.print("not get time");
    }

    public static Date getDateFromNetwork() {
        Socket socket = null;
        StringBuilder time = new StringBuilder();
        try {
            socket = new Socket(hostname, hostPort);
            socket.setSoTimeout(15 * 1000);

            Reader reader = new InputStreamReader(new BufferedInputStream(socket.getInputStream()));
            int c = -1;
            while ((c = reader.read()) != -1) {
                time.append((char) c);
            }
            System.out.println(time.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return parseDate(time.toString());
    }

    public static Date parseDate(String s) {
        String[] pieces = s.split(" ");

        try {
            if (pieces.length > 2) {
                String dateTime = pieces[1] + " " + pieces[2] + " UTC";
                DateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss z");
                return format.parse(dateTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
