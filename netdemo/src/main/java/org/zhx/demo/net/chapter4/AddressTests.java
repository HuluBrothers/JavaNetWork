package org.zhx.demo.net.chapter4;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 测试一个地址是IPV4 还是 IPV6
 */
public class AddressTests {

    public static int getVersion(InetAddress ia){
        byte[] addrest = ia.getAddress();
        if(addrest.length == 4)
            return 4;
        else if(addrest.length == 6)
            return 6;
        else
            return -1;

    }

    public static void main(String args[]){
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address + " type=IPV" + AddressTests.getVersion(address));

            address = InetAddress.getByName("www.baidu.com");
            System.out.println(address + " type=IPV" + AddressTests.getVersion(address));


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
