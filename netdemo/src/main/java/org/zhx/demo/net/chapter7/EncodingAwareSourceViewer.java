package org.zhx.demo.net.chapter7;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EncodingAwareSourceViewer {

    public static void main(String args[]){

        String urlStr = "https://www.qq.com/";

        InputStream in = null;
        BufferedInputStream bin = null;
        Reader read = null;
        try{
            //step 1 构造URL对象
            URL url = new URL(urlStr);

            //step2 调用这个URL对象的openConnection方法，获取对应的URLConnectionduixiang
            URLConnection uc = url.openConnection();

            //允许在建立stream前读取 首部信息
            String encoding = "iso-8859-1";
            String contentType = uc.getContentType();
            System.out.println(contentType);
            int encodingStart = contentType.indexOf("charset=");
            if(encodingStart != -1){
                encoding = contentType.substring(encodingStart + 8);
            }
            Map<String,List<String>> headerFelds = uc.getHeaderFields();
            Iterator<Map.Entry<String, List<String>>> iterator = headerFelds.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, List<String>> entry = iterator.next();
                System.out.println("首部信息: " + entry.getKey() + "=" + entry.getValue());
            }

            //step3 调用这个URLConnection的getInputStream() 方法
            in = uc.getInputStream();

            //step4 使用通常的流API 读取输入流
            bin = new BufferedInputStream(in);
            read = new InputStreamReader(bin,encoding);

            int c = -1;
            while( (c = read.read()) != -1){
                System.out.print((char) c);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(in != null){
                    in.close();
                }
                if(bin != null){
                    bin.close();
                }
                if(read!= null){
                    read.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
