package org.zhx.demo.net.chapter11;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

public class IntgenClient {

    public static int DEFAULT_PROT = 1919;

    public static void main(String args[]){
        int port = DEFAULT_PROT;
        String host = "127.0.0.1";

        try{
            //创建通道
            InetSocketAddress address = new InetSocketAddress(host,port);
            SocketChannel client = SocketChannel.open(address);
            //获得缓冲区和视图缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(4);
            IntBuffer view = buffer.asIntBuffer();

            for(int exception = 0; ;exception++){
                //从网络中读取数据
                client.read(buffer);
                //获取数据内容
                int actual = view.get();
                //重置网络缓冲区中读取数据的position和limit
                buffer.clear();
                //重置视图缓冲区中position
                view.rewind();

                //期望数据
                if(actual != exception){
                    System.out.println("Exception:" + exception + ": was " + actual);
                    break;
                }
                System.out.println(actual);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("连接失败");
        }

    }
}
