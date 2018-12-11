package org.zhx.demo.net.chapter7;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class Last24 {
    public static void main(String[] args) {

        // Initialize a Date object with the current date and time
        Date today = new Date();
        long millisecondsPerDay = 24 * 60 * 60 * 1000;
        String urlStr = "http://www.baidu.com/";
        try {
            URL u = new URL(urlStr);
            URLConnection uc = u.openConnection();
            System.out.println("Original if modified since: " + new Date(uc.getIfModifiedSince()));
            uc.setIfModifiedSince((new Date(today.getTime() - millisecondsPerDay)).getTime());
            System.out.println("Will retrieve file if it's modified since " + new Date(uc.getIfModifiedSince()));
            try (InputStream in = new BufferedInputStream(uc.getInputStream())) {
                Reader r = new InputStreamReader(in, "GBK");
                int c;
                while ((c = r.read()) != -1) {
                    System.out.print((char) c);
                }
                System.out.println();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
