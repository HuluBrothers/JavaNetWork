package org.zhx.demo.net.chapter11;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class IntgenServer {

    public static int DEFAULTT_PORT = 1919;

    public static void main(String args[]){
        int port = DEFAULTT_PORT;
        System.out.println("Listening for connections on port=" + port);

        ServerSocketChannel serverChannel = null;
        Selector selector = null;

        try{
            //绑定端口
            serverChannel = ServerSocketChannel.open();
            ServerSocket ss = serverChannel.socket();
            InetSocketAddress address = new InetSocketAddress(port);
            ss.bind(address);

            //设置为非阻塞
            serverChannel.configureBlocking(false);
            //设置选择器
            selector = Selector.open();
            //选择器和通道绑定
            serverChannel.register(selector,SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while(true){
            try {
                int readyNum = selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();

                try{
                    if(key.isAcceptable()){
                        //接收新的连接
                        ServerSocketChannel server = (ServerSocketChannel)key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        //设置为非阻塞客户端
                        client.configureBlocking(false);
                        //注册I/O
                        SelectionKey key2 = client.register(selector,SelectionKey.OP_WRITE);
                        //绑定一个缓冲区
                        ByteBuffer output = ByteBuffer.allocate(4);
                        output.putInt(0);
                        output.flip();
                        key2.attach(output);
                    }else if(key.isWritable()){
                        //拿到通道
                        SocketChannel client = (SocketChannel)key.channel();
                        //拿到缓冲区
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        //重置位置和限度，并重新写入数据
                        if(!output.hasRemaining()){//当前没有元素可读，意味着所有数据可以写入
                            output.rewind();//重置position，使得缓冲区数据可读
                            int value = output.getInt();//读取数据
                            output.clear();//重置position，使得数据从头开始写入，limit为容量
                            output.putInt(value+1);//放入数据
                            output.flip();//重置position，使得数据可读。
                        }
                        //写入数据
                        client.write(output);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    try{
                        key.channel().close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        }
    }
}
