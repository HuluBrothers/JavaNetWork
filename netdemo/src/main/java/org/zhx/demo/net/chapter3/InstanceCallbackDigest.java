package org.zhx.demo.net.chapter3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 从线程返回信息
 * 方法二： 使用回调来显示（拥有实例）
 */
public class InstanceCallbackDigest implements Runnable{

    private InstanceCallbackDigestUserInterface callback;
    private String filename;

    public InstanceCallbackDigest(String file,InstanceCallbackDigestUserInterface instance){
        this.filename = file;
        this.callback = instance;
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
            callback.receiveDigest(digest);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
