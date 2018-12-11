package org.zhx.demo.net.chapter3;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 从线程返回信息
 * 方法一：存储在结果对象的一个字段中,轮询访问（最好使用定时器）
 */
public class ReturnDigest extends Thread{

    private String filename;
    private byte[] digest;

    public ReturnDigest(String name){
        this.filename = name;
    }

    @Override
    public void run() {
        FileInputStream in = null;
        DigestInputStream din = null;
        try{
            in = new FileInputStream(this.filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            din = new DigestInputStream(in,sha);

            while(din.read() != -1){
            }
            this.digest = sha.digest();
            System.out.println("[Thread] "+DatatypeConverter.printHexBinary(digest));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if(in != null){
                    in.close();
                }
                din.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public byte[] getDigest(){
        return this.digest;
    }
}
