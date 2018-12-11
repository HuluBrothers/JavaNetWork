package org.zhx.demo.net.chapter5;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * URL 获取数据的第三种方法
 * getContext
 */
public class ContentGetter {

    public static void main(String[] args){
        String urlStr = "http://www.oreilly.com/graphics_new/animation.gif";
        urlStr = "http://www.cafeaulait.org/RelativeURLTest.class";
        urlStr = "http://www.cafeaulait.org/course/week9/spacemusic.au";

        try{
            URL url = new URL(urlStr);
            Object o = url.getContent();
            System.out.println("I got a " + o.getClass().getName());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
