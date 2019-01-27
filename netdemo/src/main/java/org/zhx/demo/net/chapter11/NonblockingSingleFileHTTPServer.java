package org.zhx.demo.net.chapter11;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class NonblockingSingleFileHTTPServer {
    private ByteBuffer contentBuffer;
    private int port = 80;

    public NonblockingSingleFileHTTPServer(ByteBuffer data,String encoding,
                                           String MIMEType,int port){
            this.port = port;
            //HTTP response 头部信息
            String header = "HTTP/1.0 200 OK /r/n"
                    + "Server: NonblockingSingleFileHTTPServer\r\n"
                    + "Content-length: "+data.limit() + "\r\n"
                    + "Content-type: " + MIMEType + " ; charset=" + encoding + "\r\n\r\n";
            //字节格式
            byte[] headerData = header.getBytes(Charset.forName("US-ASCII"));

            //要传输的单文件
            ByteBuffer buffer = ByteBuffer.allocate(data.limit()+headerData.length);

            buffer.put(headerData);
            buffer.put(data);
            buffer.flip();
            this.contentBuffer = buffer;
    }

    public void run(){
        ServerSocketChannel serverChannel = null;
        Selector selector = null;

        try{
            serverChannel = ServerSocketChannel.open();
            ServerSocket ss = serverChannel.socket();
            ss.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);

            selector = Selector.open();
            serverChannel.register(selector,SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try{
                int readyNum = selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
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
                        //HTTP 要求客户端在服务器响应之前发送请求
                        SelectionKey key2 = client.register(selector,SelectionKey.OP_READ);
                    }else if(key.isWritable()){
                        SocketChannel response = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        if(buffer.hasRemaining()){
                            response.write(buffer);
                        }else{
                            response.close();
                        }
                    }else if(key.isReadable()){
                        //不用费力的解析HTTP首部
                        //只需要读取
                        SocketChannel request = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(4096);
                        request.read(buffer);
                        System.out.println("请求内容(不同浏览器头部不一样): "+buffer.toString());
                        key.interestOps(SelectionKey.OP_WRITE);
                        key.attach(contentBuffer.duplicate());
                    }
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
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

    public static void main(String args[]){
        try{
            String filename = "./root/11_index.html";
            //获取要传输文件的类型
            String contentType = URLConnection.getFileNameMap().getContentTypeFor(filename);
            //获取文件
            Path file = FileSystems.getDefault().getPath(filename);
            //获取字节
            byte[] data = Files.readAllBytes(file);
            //封装进入缓冲区
            ByteBuffer input = ByteBuffer.wrap(data);

            int port = 8080;

            String encoding = "UTF-8";

            NonblockingSingleFileHTTPServer server = new NonblockingSingleFileHTTPServer(
                    input,encoding,contentType,port);
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
