package org.zhx.demo.net.chapter3;

import java.io.*;
import java.util.Date;

public class LogFile {

    private Writer out;

    public LogFile(File f) throws IOException {
        FileWriter fw = new FileWriter(f);
        out = new BufferedWriter(fw);
    }

    public synchronized void writeEntry(String message) throws IOException {
        Date d = new Date();
        out.write(d.toString());
        out.write("\t");
        out.write(message);
        out.write("\r\n");
    }

    public void close() throws IOException {
        out.flush();
        out.close();
    }
}
