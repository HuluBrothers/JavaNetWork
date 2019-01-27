package org.zhx.demo.net.chapter3;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 继承Thread 启动线程
 * 计算多个文件的安全散列算法（SHA）摘要
 */
public class DigestThread extends Thread{

    private String filename;
    public DigestThread(String fileName){
        this.filename = fileName;
    }

    @Override
    public void run() {
        //释放模式 (dispose pattern)
        FileInputStream in = null;
        try {
            in = new FileInputStream(this.filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in,sha);
            while(din.read() != -1){
            }
            din.close();
            byte[] digest = sha.digest();
            StringBuilder sb = new StringBuilder(this.filename);
            sb.append(": ").append(DatatypeConverter.printHexBinary(digest));

            System.out.println(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if(in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]){

        for(String fileName : args){
            new DigestThread(fileName).start();
        }
    }
}
