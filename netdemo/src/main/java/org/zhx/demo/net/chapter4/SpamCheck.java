package org.zhx.demo.net.chapter4;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SpamCheck {

    public static final String BLACKHOLE = "sbl.spamhaus.org";

    public static void main(String args[]){
        String testInstance[] = {
                "207.34.56.23",
                "125.12.32.4",
                "130.130.130.130"
        };

        for(String instance : testInstance){
            if(SpamCheck.isSpamer(instance)){
                System.out.println(instance + " 已知的垃圾邮件发送者(is a known spammer)");
            }else{
                System.out.println(instance + " 看起来不是(appears legitimate.)");
            }
        }
    }

    public static boolean isSpamer(String arg){

        try{
            InetAddress address = InetAddress.getByName(arg);
            byte[] quad = address.getAddress();
            String query = BLACKHOLE;
            for(byte octer : quad){
                int unsignedByte = octer < 0 ? octer + 256 : octer;
                query = unsignedByte + "." + query;
            }
            InetAddress.getByName(query);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
