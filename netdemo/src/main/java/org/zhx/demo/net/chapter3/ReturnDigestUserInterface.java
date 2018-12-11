package org.zhx.demo.net.chapter3;

import javax.xml.bind.DatatypeConverter;

/**
 * 这样的调用会严重依赖竞态条件
 */
public class ReturnDigestUserInterface {

    public static void main(String agrs[]){
        String filenames[] = {
                "./src/main/java/org/zhx/source/net/DigestThread.java ",
                "./src/main/java/org/zhx/source/net/ChargenClient.java"
        };

        for(String file : filenames){
            //计算摘要
            ReturnDigest dr = new ReturnDigest(file);
            dr.start();

            try{
                //现在显示结果
                StringBuilder result = new StringBuilder(file);
                result.append(":");
                byte[] digest = dr.getDigest();
                result.append(DatatypeConverter.printHexBinary(digest));

                System.out.println(result);
            }catch (NullPointerException e){
                e.printStackTrace();
                System.out.println("竞态条件");
            }

        }
    }
}
