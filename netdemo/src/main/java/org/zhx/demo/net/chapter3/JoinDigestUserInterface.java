package org.zhx.demo.net.chapter3;

import javax.xml.bind.DatatypeConverter;

public class JoinDigestUserInterface {
    public static void main(String args[]) throws InterruptedException {
        String filenames[] = {
                "./src/main/java/org/zhx/source/net/DigestThread.java ",
                "./src/main/java/org/zhx/source/net/ChargenClient.java"
        };

        ReturnDigest digests[] = new ReturnDigest[filenames.length];
        for (int i = 0; i < filenames.length; i++) {
            digests[i] = new ReturnDigest(filenames[i]);
            digests[i].setPriority(10);
            digests[i].start();
        }

        for (int i = 0; i < filenames.length; i++) {
            digests[i].join();
            byte[] digest = digests[i].getDigest();

            StringBuilder sb = new StringBuilder(filenames[i]);
            sb.append(": ");
            sb.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(sb);

        }
    }
}
