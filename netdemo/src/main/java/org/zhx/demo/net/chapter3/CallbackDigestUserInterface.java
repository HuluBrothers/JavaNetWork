package org.zhx.demo.net.chapter3;

import javax.xml.bind.DatatypeConverter;

public class CallbackDigestUserInterface {

    public static void receiveDigest(String file,byte[] digest){
        StringBuilder sb = new StringBuilder(file);
        sb.append(": ");
        sb.append(DatatypeConverter.printHexBinary(digest));

        System.out.println(sb.toString());

    }

    public static void main(String agrs[]){
        String filenames[] = {
                "./src/main/java/org/zhx/source/net/DigestThread.java ",
                "./src/main/java/org/zhx/source/net/ChargenClient.java"
        };

        for(String file : filenames){
            Thread t = new Thread(new CallbackDigest(file));
            t.start();
        }
    }
}
