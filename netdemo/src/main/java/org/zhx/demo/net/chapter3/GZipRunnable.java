package org.zhx.demo.net.chapter3;

import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * 线程池的简单使用
 * 线程任务
 */
public class GZipRunnable implements Runnable{

    private File input;

    public GZipRunnable(File f){
        this.input = f;
    }
    @Override
    public void run() {
        if(!input.getName().endsWith(".gz")){
            File outputParent = new File(input.getParent()+"/gz");
            if(!outputParent.exists()){
                outputParent.mkdir();
            }

            File output= new File(outputParent.getAbsoluteFile(),input.getName()+".gz");

            try(
                    InputStream in = new BufferedInputStream(new FileInputStream(input));
                    OutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(output)));
            ){
                int b;
                while( ( b = in.read()) != -1){
                    out.write(b);
                }
                out.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            input.delete();
        }
    }
}
