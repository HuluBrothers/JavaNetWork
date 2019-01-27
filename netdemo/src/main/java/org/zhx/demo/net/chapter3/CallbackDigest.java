package org.zhx.demo.net.chapter3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 从线程返回信息
 * 方法二： 使用回调来显示（静态方法）
 */
public class CallbackDigest implements Runnable{

    private String filename;

    public CallbackDigest(String name){
        this.filename = name;
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

            din.close();
            byte[] digest = sha.digest();
            CallbackDigestUserInterface.receiveDigest(this.filename,digest);

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
