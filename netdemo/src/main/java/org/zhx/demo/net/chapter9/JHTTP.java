package org.zhx.demo.net.chapter9;


import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * 概述： 模拟一个HTTP服务器
 * 功能： 可以提供一个完整的文档树，包括图像，applet，HTML文件,文本文件等
 */
public class JHTTP {

    private static final Logger logger = Logger.getLogger(JHTTP.class.getCanonicalName());

    private static final int NUM_THREADS = 100;
    private static final String INDEX_FILE = "index.html";

    private final File rootDirectory;
    private final int port;

    public JHTTP(File rootDirectory, int port){
        this.port = port;
        this.rootDirectory = rootDirectory;
    }

    public void start(){
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        try(ServerSocket server = new ServerSocket(port)){
            logger.info("Accepting connections on port " + server.getLocalPort());
            logger.info("Document Root: " + rootDirectory);

            while(true){
                Socket request = server.accept();
                Runnable task = new RequestProcessor(this.rootDirectory,INDEX_FILE,request);
                pool.submit(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    public static void main(String args[]){
        File docroot = new File("root/");
        System.out.println(docroot.getAbsoluteFile());
        int port = 8080;
        JHTTP webserver = new JHTTP(docroot,port);
        webserver.start();
    }


}
