package org.zhx.demo.net.chapter5;

import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * URL 的第一个数据读取方式
 * openStream
 */
public class SourceViewer {

    public static void main(String args[]){

        String urlStr = "http://ouyang3287.blog.163.com/blog/static/108234277201810642121/";
        //urlStr = "http://www.baidu.com";
        urlStr = "https://www.oreilly.com";
        InputStream in = null;
        BufferedInputStream bin = null;
        Reader rin = null;

        try{
            URL url = new URL(urlStr);

            in = url.openStream();
            bin = new BufferedInputStream(in);
            rin = new InputStreamReader(bin);

            int c = 0;
            while( (c = rin.read()) != -1){
                System.out.print((char)c);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(rin != null){
                    rin.close();
                }
                if(bin != null){
                    bin.close();
                }
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
