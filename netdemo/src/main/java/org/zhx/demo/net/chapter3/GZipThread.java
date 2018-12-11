package org.zhx.demo.net.chapter3;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GZipThread {

    public final static int THREAD_COUNT = 4;

    public static void main(String args[]){
        String filenames[] = {
                "./src/main/java/org/zhx/source/net",
        };

        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);

        for(String file : filenames){
            File f = new File(file);

            if(f.exists()){
                if(f.isDirectory()){
                    File[] files = f.listFiles();
                    for(int i=0;i<files.length;i++){
                        if(!files[i].isDirectory()){
                            Runnable task = new GZipRunnable(files[i]);
                            pool.submit(task);
                        }
                    }
                }else{
                    Runnable task = new GZipRunnable(f);
                    pool.submit(task);
                }
            }
        }

        pool.shutdown();
    }
}
