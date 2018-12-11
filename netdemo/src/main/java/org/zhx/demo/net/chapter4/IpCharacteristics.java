package org.zhx.demo.net.chapter4;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP 地址的一些标准
 */
public class IpCharacteristics {

    public static void main(String args[]){
        String addresses[] = {
           "127.0.0.1",
            "192.168.254.32",
                "www.oreilly.com",
                "224.0.2.1",
                "FF01:0:0:0:0:0:0:0:1",
                "FF05:0:0:0:0:0:0:0:101",
                "0::1"

        };

        for(String addName : addresses){
            try {
                InetAddress address = InetAddress.getByName(addName);

                if (address.isAnyLocalAddress()) {
                    System.out.println(address + " is a wildcard address. 通配地址");
                }
                if (address.isLoopbackAddress()) {
                    System.out.println(address + " is loopback address. 回送地址");
                }

                if (address.isLinkLocalAddress()) {
                    System.out.println(address + " is a link-local address. 本地链接地址");
                } else if (address.isSiteLocalAddress()) {
                    System.out.println(address + " is a site-local address. 本地网站地址");
                } else {
                    System.out.println(address + " is a global address. 否则全球地址");
                }

                if (address.isMulticastAddress()) {//组播地址
                    if (address.isMCGlobal()) {
                        System.out.println(address + " is a global multicast address. 全球组播地址");
                    } else if (address.isMCOrgLocal()) {
                        System.out.println(address
                                + " is an organization wide multicast address. 组织范围组播地址");
                    } else if (address.isMCSiteLocal()) {
                        System.out.println(address + " is a site wide multicast address. 网站范围组播地址");
                    } else if (address.isMCLinkLocal()) {
                        System.out.println(address + " is a subnet wide multicast address. 子网范围组播地址");
                    } else if (address.isMCNodeLocal()) {
                        System.out.println(address
                                + " is an interface-local multicast address. 本地接口组播地址");
                    } else {
                        System.out.println(address + " is an unknown multicast address type. 不知道类型的组播地址");
                    }
                } else {
                    System.out.println(address + " is a unicast address. 单播地址");
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }finally {
                System.out.println("==================>");
            }
        }

    }
}
