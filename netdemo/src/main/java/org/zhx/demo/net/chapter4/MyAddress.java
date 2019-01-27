package org.zhx.demo.net.chapter4;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyAddress {

    public static  void main(String args[]){

        try{
            /**
             * 首先尝试连接DNS系统，如果有主机名和IP地址返回
             * 否则会送本机地址 local host 和 127.0.0.1（之前会会送本机的局域网地址）
             */
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address);
            System.out.println(address.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
