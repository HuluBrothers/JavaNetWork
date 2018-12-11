package org.zhx.demo.net.chapter3;

import javax.xml.bind.DatatypeConverter;

/**
 * 轮询的方法活得返回值
 * 这显然不是一个好主意，主线程轮询会花费大量的时间，甚至可能造成
 * 其他线程没有执行时间
 */
public class ReturnDigestUserInterface_2 {

    public static void main(String args[]){
        String filenames[] = {
                "./src/main/java/org/zhx/source/net/DigestThread.java ",
                "./src/main/java/org/zhx/source/net/ChargenClient.java"
        };

        ReturnDigest digests[] = new ReturnDigest[filenames.length];
        for(int i=0;i<filenames.length;i++){
            digests[i] = new ReturnDigest(filenames[i]);
            digests[i].setPriority(10);
            digests[i].start();
        }

        for(int i=0;i<filenames.length;i++){
            while(true){
                byte[] digest = digests[i].getDigest();
                if(digest != null){
                   StringBuilder sb = new StringBuilder(filenames[i]);
                   sb.append(": ");
                   sb.append(DatatypeConverter.printHexBinary(digest));
                   System.out.println(sb);
                   break;
                }
                //如果主线程一直轮询会造成 其它线程没有执行时间
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
