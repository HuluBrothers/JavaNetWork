package org.zhx.demo.net.chapter4;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * InetAddress 对象实例的生成
 */
public class InetAddressByName {

    public static void main(String args[]){
        try {
            InetAddress address = InetAddress.getByName("www.baidu.com");
            System.out.println(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("不能找到 www.baidu.com");
        }

        try {
            InetAddress addresses[] = InetAddress.getAllByName("www.oreilly.com");
            for(InetAddress address : addresses) {
                System.out.println(address);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("不能找到 www.baidu.com");
        }

        try {
            //无法找到主机名字
            InetAddress address = InetAddress.getByName("61.135.169.121");
            System.out.println(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        try{
            InetAddress address = InetAddress.getByName("47.104.183.53");
            System.out.println(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
