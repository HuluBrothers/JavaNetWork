package org.zhx.demo.net.chapter3;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 实现Runnable 接口实现多线程
 * 计算多个文件的安全散列算法（SHA）摘要
 */
public class DigestRunnable implements Runnable{

    private String filename;

    public DigestRunnable(String fileName){
        this.filename = fileName;
    }

    @Override
    public void run() {
        FileInputStream in = null;
        try{
            in = new FileInputStream(this.filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in,sha);

            while(din.read() != -1){
            }

            StringBuilder sb = new StringBuilder(this.filename);
            sb.append(": ");
            sb.append(DatatypeConverter.printHexBinary(sha.digest()));

            System.out.println(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static void main(String args[]){
        for(String filename : args){
            new Thread(new DigestRunnable(filename)).start();
        }
    }
}
