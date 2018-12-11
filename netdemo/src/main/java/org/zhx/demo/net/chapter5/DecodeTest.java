package org.zhx.demo.net.chapter5;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class DecodeTest {
    public static final String character = "UTF-8";

    public static void main(String[] args) {
        String str = "";

        try {
            str = URLEncoder.encode("This string has spaces", "UTF-8");
            System.out.println(str);
            System.out.println(URLDecoder.decode(str,character));
            str = URLEncoder.encode("This*string*has*asterisks", "UTF-8");
            System.out.println(str);
            System.out.println(URLDecoder.decode(str,character));
            str = URLEncoder.encode("This%string%has%percent%signs", "UTF-8");
            System.out.println(str);
            System.out.println(URLDecoder.decode(str,character));
            str = URLEncoder.encode("This+string+has+pluses", "UTF-8");
            System.out.println(str);
            System.out.println(URLDecoder.decode(str,character));
            str = URLEncoder.encode("This/string/has/slashes", "UTF-8");
            System.out.println(str);
            System.out.println(URLDecoder.decode(str,character));
            str = URLEncoder.encode("This\"string\"has\"quote\"marks", "UTF-8");
            System.out.println(str);
            System.out.println(URLDecoder.decode(str,character));
            str = URLEncoder.encode("This:string:has:colons", "UTF-8");
            System.out.println(str);
            System.out.println(URLDecoder.decode(str,character));
            str = URLEncoder.encode("This~string~has~tildes", "UTF-8");
            System.out.println(str);
            System.out.println(URLDecoder.decode(str,character));
            str = URLEncoder.encode("This(string)has(parentheses)", "UTF-8");
            System.out.println(str);
            System.out.println(URLDecoder.decode(str,character));
            str = URLEncoder.encode("This.string.has.periods", "UTF-8");
            System.out.println(str);
            System.out.println(URLDecoder.decode(str,character));
            str = URLEncoder.encode("This=string=has=equals=signs", "UTF-8");
            System.out.println(str);
            System.out.println(URLDecoder.decode(str,character));
            str = URLEncoder.encode("This&string&has&ampersands", "UTF-8");
            System.out.println(str);
            System.out.println(URLDecoder.decode(str,character));
            str = URLEncoder.encode("Thiséstringéhasénon-ASCII characters", "UTF-8");
            System.out.println(str);
            System.out.println(URLDecoder.decode(str,character));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }
}
