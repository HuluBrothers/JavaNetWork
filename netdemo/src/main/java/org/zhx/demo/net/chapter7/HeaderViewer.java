package org.zhx.demo.net.chapter7;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class HeaderViewer {

    public static void main(String[] args) {
        args = new String[4];
        args[0] = "http://www.baidu.com/";
        args[1] = "http://www.alibaba.com/";
        args[2] = "https://www.qq.com";
        args[3] = "http://www.oreilly.com/";

        for (int i = 0; i < args.length; i++) {
            try {
                URL u = new URL(args[i]);
                System.out.println(u);
                URLConnection uc = u.openConnection();
                System.out.println("Content-type: " + uc.getContentType());
                if (uc.getContentEncoding() != null) {
                    System.out.println("Content-encoding: "
                            + uc.getContentEncoding());
                }
                if (uc.getDate() != 0) {
                    System.out.println("Date: " + new Date(uc.getDate()));
                }
                if (uc.getLastModified() != 0) {
                    System.out.println("Last modified: "
                            + new Date(uc.getLastModified()));
                }
                if (uc.getExpiration() != 0) {
                    System.out.println("Expiration date: "
                            + new Date(uc.getExpiration()));
                }
                if (uc.getContentLength() != -1) {
                    System.out.println("Content-length: " + uc.getContentLength());
                }

                Thread.sleep(1000);
            } catch (MalformedURLException ex) {
                System.err.println(args[i] + " is not a URL I understand");
            } catch (IOException ex) {
                System.err.println(ex);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }
}
