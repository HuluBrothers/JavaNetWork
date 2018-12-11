package org.zhx.demo.net.chapter7;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SourceViewer3 {
    public static void main(String[] args) {
        String urlStr = "http://www.baidu.com/";
        try {
            // Open the URLConnection for reading
            URL u = new URL(urlStr);
            HttpURLConnection uc = (HttpURLConnection) u.openConnection();
            int code = uc.getResponseCode();
            String response = uc.getResponseMessage();
            System.out.println("HTTP/1.x " + code + " " + response);
            for (int j = 1; ; j++) {
                String header = uc.getHeaderField(j);
                String key = uc.getHeaderFieldKey(j);
                if (header == null || key == null) break;
                System.out.println(uc.getHeaderFieldKey(j) + ": " + header);
            }
            System.out.println();

            try (InputStream in = new BufferedInputStream(uc.getInputStream())) {
                // chain the InputStream to a Reader
                Reader r = new InputStreamReader(in);
                int c;
                while ((c = r.read()) != -1) {
                    System.out.print((char) c);
                }
            }
        } catch (MalformedURLException ex) {
            System.err.println(urlStr + " is not a parseable URL");
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
