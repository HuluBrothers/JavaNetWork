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

public class ChargenServer {
    public static int DEFAULT_PORT = 19;

    public static void main(String args[]){
        int port = DEFAULT_PORT;
        System.out.println("listening for connections on port=" + port);

        //准备发送的数据
        byte[] rotation = new byte[95 * 2];
        for(byte i=' ';i<='~';i++){
            rotation[i - ' '] = i;
            rotation[ i + 95 - ' '] = i;
        }

        //构建服务通道
        ServerSocketChannel serverChannel;

        //构建选择器
        Selector selector;

        try{
            //绑定端口
            serverChannel = ServerSocketChannel.open();
            ServerSocket ss = serverChannel.socket();
            InetSocketAddress address = new InetSocketAddress(port);
            ss.bind(address);

            //设置接收连接方式为非阻塞
            serverChannel.configureBlocking(false);
            //构建选择器，注册服务
            selector = Selector.open();
            serverChannel.register(selector,SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while(true){
            try{
                //选择一组键，其相应的通道已为 I/O 操作准备就绪。返回准备就绪的个数
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //返回此选择器的已选择键集。
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                try{
                    if(key.isAcceptable()){
                        //当前的通道一定是服务通道，接收新的连接通道
                        ServerSocketChannel server = (ServerSocketChannel)key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);

                        //设置新的通道为非阻塞模式
                        client.configureBlocking(false);
                        //新的的通道注册到选择上去
                        SelectionKey key2 = client.register(selector,SelectionKey.OP_WRITE);
                        //设置缓冲区绑定
                        ByteBuffer buffer = ByteBuffer.allocate(74);
                        buffer.put(rotation,0,72);
                        buffer.put((byte) '\r');
                        buffer.put((byte)'\n');
                        buffer.flip();
                        key2.attach(buffer);
                    }else if(key.isWritable()){
                        //拿到可以写入数据的通道
                        SocketChannel client = (SocketChannel) key.channel();
                        //拿到绑定在其上的缓冲区
                        ByteBuffer buffer = (ByteBuffer)key.attachment();
                        if(!buffer.hasRemaining()){//告知在当前位置和限制之间是否有元素
                            //用下一行重新填充缓冲区
                            buffer.rewind();
                            //得到上一次的首个字符
                            int first = buffer.get();
                            //准备改变缓冲区中的数据
                            buffer.rewind();
                            //寻找rotation中新的首个字符位置
                            int position = first - ' ' + 1;
                            //将数据从rotaion复制到缓冲区
                            buffer.put(rotation,position,72);
                            //在缓冲区末尾存储一个行分隔符
                            buffer.put((byte) '\r');
                            buffer.put((byte) '\n');
                            buffer.flip();
                        }
                        client.write(buffer);
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
