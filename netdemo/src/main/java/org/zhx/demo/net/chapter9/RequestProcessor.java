package org.zhx.demo.net.chapter9;

import java.io.*;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Date;
import java.util.logging.Logger;

public class RequestProcessor implements Runnable{

    private final static Logger logger = Logger.getLogger(RequestProcessor.class.getCanonicalName());

    private File rootDirectory;
    private String indexFileName = "index.html";
    private Socket connection;

    public RequestProcessor(File rootDirectory, String indexFileName, Socket connection) {
        if(rootDirectory.isFile()){
            throw new IllegalArgumentException("rootDirectory must be a directory, not a file");
        }
        try{
            this.rootDirectory = rootDirectory.getCanonicalFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(indexFileName != null){
            this.indexFileName = indexFileName;
        }

        this.connection = connection;
    }
    @Override
    public void run() {
        //安全检查
        String root = rootDirectory.getPath();
        Reader in = null;
        Writer out = null;

        try{
            //拿到输入输出流
            OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
            in = new InputStreamReader(new BufferedInputStream(connection.getInputStream()));
            out = new OutputStreamWriter(raw);

            //拿到第一行请求信息
            StringBuilder request = new StringBuilder();
            while(true){
                int c = in.read();
                if(c == '\r' || c == '\n'){
                    break;
                }
                request.append((char)c);
            }

            String get = request.toString();
            logger.info(connection.getRemoteSocketAddress() + " " + get);

            //获得HTTP版本 和方法
            String tokers[] = get.split("\\s+");
            String method = tokers[0];
            String version = "";

            if(method.equals("GET")){
                String fileName = tokers[1];
                //访问目录下默认文件
                if(fileName.endsWith("/")){
                    fileName += indexFileName;
                }
                String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
                if(tokers.length > 2){
                    version = tokers[2];
                }

                File theFile = new File(rootDirectory,fileName.substring(1,fileName.length()));
                if(theFile.canRead() && theFile.getCanonicalPath().startsWith(root)){
                    byte[] data = Files.readAllBytes(theFile.toPath());
                    if(version.startsWith("HTTP/")){
                        sendHeader(out,"HTTP/1.0 200 OK",contentType,data.length);
                    }

                    //发送文件，这可能是一个图像或其他二进制数据
                    //所以要用底层输出流而不是Writer
                    raw.write(data);
                    raw.flush();
                }else{
                    String body = new StringBuilder("<HTML>\r\n")
                            .append("<HEAD><TITLE>File Not Found</TITLE>\r\n")
                            .append("</HEAD>\r\n")
                            .append("<BODY>")
                            .append("<H1>HTTP Error 404: File Not Found</H1>\r\n")
                            .append("</BODY></HTML>\r\n").toString();
                    if (version.startsWith("HTTP/")) { // send a MIME header
                        sendHeader(out, "HTTP/1.0 404 File Not Found",
                                "text/html; charset=utf-8", body.length());
                    }
                    out.write(body);
                    out.flush();
                }
            }else{//无法找到文件
                String body = new StringBuilder("<HTML>\r\n")
                        .append("<HEAD><TITLE>File Not Found</TITLE>\r\n")
                        .append("</HEAD>\r\n")
                        .append("<BODY>")
                        .append("<H1>HTTP Error 404: File Not Found</H1>\r\n")
                        .append("</BODY></HTML>\r\n").toString();
                if (version.startsWith("HTTP/")) { // send a MIME header
                    sendHeader(out, "HTTP/1.0 404 File Not GET REQUEST",
                            "text/html; charset=utf-8", body.length());
                }
                out.write(body);
                out.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
            //这里发送404 页面
        }finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendHeader(Writer out, String responseCode,
                            String contentType, int length)
            throws IOException {
        out.write(responseCode + "\r\n");
        Date now = new Date();
        out.write("Date: " + now + "\r\n");
        out.write("Server: JHTTP 2.0\r\n");
        out.write("Content-length: " + length + "\r\n");
        out.write("Content-type: " + contentType + "\r\n\r\n");
        out.flush();
    }
}
