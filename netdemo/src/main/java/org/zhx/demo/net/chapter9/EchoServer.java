package org.zhx.demo.net.chapter9;

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

public class EchoServer {
    public static int DEFAULT_PORT = 7;

    public static void main(String args[]) {
        System.out.println("Listening for connections on port:" + DEFAULT_PORT);

        //step 1 声明一个通道
        ServerSocketChannel serverChannel = null;
        //setp2 声明一个选择器
        Selector selector = null;

        try {
            //step1 调用静态工厂方法，生成一个通道
            serverChannel = ServerSocketChannel.open();

            //step2 使用socket 方法获取其ServerSocket对等端（peer）对象，并绑定端口
            ServerSocket ss = serverChannel.socket();
            InetSocketAddress address = new InetSocketAddress(DEFAULT_PORT);
            ss.bind(address);
            //step 3 设置成非阻塞模式，允许服务器处理多个并发连接
            serverChannel.configureBlocking(false);

            //step 4 创建一个选择器
            selector = Selector.open();

            //step 5 通道上注册选择器
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {

            //step 6 检查是否有可以操作的数据
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }


            //step 7 返回就绪他通道
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepte connection from " + client);
                        client.configureBlocking(false);
                        SelectionKey clientKey = client.register(selector, SelectionKey.OP_WRITE
                                | SelectionKey.OP_READ);
                        ByteBuffer buffer = ByteBuffer.allocate(100);
                        clientKey.attach(buffer);
                    }
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        client.read(output);
                    }
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        output.flip();
                        client.write(output);
                        output.compact();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        key.channel().close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
