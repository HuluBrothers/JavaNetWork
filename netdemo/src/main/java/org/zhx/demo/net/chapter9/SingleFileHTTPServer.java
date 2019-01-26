package org.zhx.demo.net.chapter9;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 单文件的HTTP服务器
 * 无论什么请求，一律应答一个文件
 */
public class SingleFileHTTPServer {

    private static final Logger logger = Logger.getLogger("SingleFileHTTPServer");

    private byte[] content;
    private byte[] header;
    private int port;
    private String encoding;

    public SingleFileHTTPServer(String data, String encoding, String mimeType, int port) throws UnsupportedEncodingException {
        this(data.getBytes(encoding),encoding,mimeType,port);
    }

    public SingleFileHTTPServer(byte[] data, String encoding, String mimeType, int port){
        this.content = data;
        this.port = port;
        this.encoding = encoding;
        String header = "HTTP/1.0 200 OK\r\n"
                + "Server: OneFile 2.0\r\n"
                + "Content-length: " + this.content.length + "\r\n"
                + "Content-type: " + mimeType + "; charset=" + encoding + "\r\n\r\n";
        this.header = header.getBytes(Charset.forName("US-ASCII"));
    }

    public void start(){
        ExecutorService pool = Executors.newFixedThreadPool(100);
        try(ServerSocket server = new ServerSocket(port)){
            logger.info("Accepting connectiongs on port " + server.getLocalPort());
            logger.info("Data to be sent:");
            logger.info(new String(this.content,encoding));

            while(true){
                try{
                    Socket connection = server.accept();
                    pool.submit(new HTTPHandler(connection));
                } catch (IOException ex) {
                    logger.log(Level.WARNING, "Exception accepting connection", ex);
                } catch (RuntimeException ex) {
                    logger.log(Level.SEVERE, "Unexpected error", ex);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class HTTPHandler implements Callable<Void> {
        private final Socket connection;
        public HTTPHandler(Socket conn){
            this.connection = conn;
        }


        @Override
        public Void call() throws Exception {

            try{
                InputStream in = new BufferedInputStream(this.connection.getInputStream());
                OutputStream out = new BufferedOutputStream(this.connection.getOutputStream());

                StringBuilder request = new StringBuilder();
                int c = -1;
                while(true){
                    c = in.read();
                    if(c == '\r' || c == '\n'){
                        break;
                    }
                    request.append((char) c);
                }

                if(request.toString().indexOf("HTTP/") != -1){
                    out.write(header);
                }
                out.write(content);
                out.flush();
                in.close();
                out.close();
            }finally {
                this.connection.close();
            }


            return null;
        }
    }

    public static void main(String args[]){
        try {
            int port = 8080;
            String encoding = "UTF-8";
            String filename = "./root/index.html";
            Path path = Paths.get(filename);
            //Path path = Paths.get("C:","Users","zhanghx","Desktop","body.txt");
            byte[] data = Files.readAllBytes(path);
            //System.out.println(DatatypeConverter.printHexBinary(data));
            String contentType = URLConnection.getFileNameMap().getContentTypeFor(filename);
            //System.out.println(contentType);
            SingleFileHTTPServer server = new SingleFileHTTPServer(data,encoding,contentType,port);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
