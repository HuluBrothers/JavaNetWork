package org.zhx.demo.net.chapter4;

import java.beans.Encoder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class QueryString {
    private StringBuilder query = new StringBuilder();

    public QueryString(){
    }

    public synchronized void add(String name,String value){
        query.append("&");
        encode(name,value);
    }

    public synchronized void encode(String name,String value){
        try{
            query.append(URLEncoder.encode(name,"UTF-8"));
            query.append("=");
            query.append(URLEncoder.encode(value,"UTF-8"));
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException("Broken VM does not support UTF_8");
        }
    }

    public synchronized String getQuery(){
        return query.toString();
    }

    @Override
    public String toString(){
        return getQuery();
    }
}
