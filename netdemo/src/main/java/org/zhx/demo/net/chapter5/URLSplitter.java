package org.zhx.demo.net.chapter5;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * 了解URL 的 租车部分
 */
public class URLSplitter {
    public static void main(String args[]){
        String urlStr = "ftp://mp3:mp3@138.247.121.61:21000/c%3a";
        //urlStr = "http://www.oreilly.com/";
        //urlStr = "http://ibiblio.org/nywc/compositions.phtml?category=Piano";
        //urlStr = "http://admin@www.blackstar.com:8080/";

        try{
            URL url = new URL(urlStr);
            System.out.println("The URL is " + url);
            System.out.println("The scheme is " + url.getProtocol());
            System.out.println("The user info is " + url.getUserInfo());

            String host = url.getHost();
            if(host != null){
                int atSign = host.indexOf("@");
                if(atSign != -1)
                    host = host.substring(atSign+1);

                System.out.println("The host is " + host);
            }

            System.out.println("The port is " + url.getPort());
            System.out.println("The path is " + url.getPath());
            System.out.println("The ref is " + url.getRef());
            System.out.println("The query is " + url.getQuery());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
