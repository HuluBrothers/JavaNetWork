package org.zhx.demo.net.chapter4;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * getCanonicalHostName 方法，任何时候都会与DNS系统交互
 */
public class ReverseTest {

    public static void main(String args[]){
        try {
            InetAddress address = InetAddress.getByName("208.201.239.100");
            System.out.println(address.getCanonicalHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
