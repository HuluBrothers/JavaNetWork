package org.zhx.demo.net.chapter3;

import javax.xml.bind.DatatypeConverter;
import javax.xml.crypto.Data;

public class InstanceCallbackDigestUserInterface {
    private String fileName;
    private byte[] digest;

    public InstanceCallbackDigestUserInterface(String file){
        this.fileName = file;
    }

    public void calculateDigest(){
        new Thread(new InstanceCallbackDigest(this.fileName,this)).start();
    }

    public void receiveDigest(byte[] digest){
        this.digest = digest;
        System.out.println(this);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(fileName);
        sb.append(": ");
        sb.append(DatatypeConverter.printHexBinary(this.digest));

        return sb.toString();
    }

    public static void main(String args[]){
        String filenames[] = {
                "./src/main/java/org/zhx/source/net/DigestThread.java ",
                "./src/main/java/org/zhx/source/net/ChargenClient.java"
        };

        for(String file : filenames){
            InstanceCallbackDigestUserInterface instance =
                    new InstanceCallbackDigestUserInterface(file);
            instance.calculateDigest();
        }
    }
}
