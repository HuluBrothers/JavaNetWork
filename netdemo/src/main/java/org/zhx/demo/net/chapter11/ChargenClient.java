package org.zhx.demo.net.chapter11;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class ChargenClient {

    public static int DEFAULt_PORT = 19;

    public static void main(String args[]){
        String host = "rama.poly.edu";
        host = "127.0.0.1";
        int port = 19;

        try{
            SocketAddress address = new InetSocketAddress(host,port);
            SocketChannel client = SocketChannel.open(address);

            ByteBuffer buffer = ByteBuffer.allocate(74);
            WritableByteChannel out = Channels.newChannel(System.out);

            //阻塞读取
            while(client.read(buffer) != -1){
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }

            //非阻塞读取
            client.configureBlocking(false);
            while(true){
                //把每次循环要运行的代码都放在这里，无论有没有读到数据
                int n = client.read(buffer);
                if(n > 0){
                    buffer.flip();
                    out.write(buffer);
                    buffer.clear();
                }else if(n == -1){
                    //这里不应该发生，因为服务器是一直发送数据的
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
