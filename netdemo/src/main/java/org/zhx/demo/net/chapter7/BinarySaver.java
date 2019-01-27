package org.zhx.demo.net.chapter7;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * 这是http 返回的格式问题
 */
public class BinarySaver {

    public static void main(String args[]) {

        String urlStr = "http://www.baidu.com/";
        try {
            URL url = new URL(urlStr);
            saveBinaryFile(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void saveBinaryFile(URL url) {
        InputStream in = null;
        BufferedInputStream bin = null;
        try {
            URLConnection uc = url.openConnection();

            in = uc.getInputStream();
            bin = new BufferedInputStream(in);

            String contentType = uc.getContentType();
            int contentLength = uc.getContentLength();
            if (contentType.startsWith("text/") || contentLength != -1) {
                throw new IOException("This is not a binary file.");
            }

            in = uc.getInputStream();
            bin = new BufferedInputStream(in);

            byte[] data = new byte[contentLength];
            int offset = 0;
            while (offset < contentLength) {
                int bytesRead = bin.read(data, offset, contentLength - offset);
                if (bytesRead == -1)
                    break;
                offset += bytesRead;
            }
            String filename = url.getFile();
            filename = filename.substring(filename.lastIndexOf('/') + 1);
            FileOutputStream fout = new FileOutputStream(filename);
            fout.write(data);
            fout.flush();
            fout.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (bin != null) {
                    bin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
