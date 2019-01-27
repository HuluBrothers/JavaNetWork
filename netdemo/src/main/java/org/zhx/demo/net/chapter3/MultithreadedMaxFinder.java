package org.zhx.demo.net.chapter3;

import javax.xml.bind.DatatypeConverter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadedMaxFinder {

    public int max(int data[]) throws ExecutionException, InterruptedException {
        if(data.length == 1){
            return data[0];
        }else if(data.length == 0){
            throw new IllegalArgumentException();
        }

        FindMaxTast task1 = new FindMaxTast(data,0,data.length/2);
        FindMaxTast task2 = new FindMaxTast(data,data.length/2,data.length);

        ExecutorService service = Executors.newFixedThreadPool(2);

        Future<Integer> future1 = service.submit(task1);
        Future<Integer> future2 = service.submit(task2);

        service.shutdown();
        int max = Math.max(future1.get(),future2.get());

        return max;
    }

    public static void main(String args[]){
        int data[] = {
                0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25
        };

        MultithreadedMaxFinder finder = new MultithreadedMaxFinder();
        try {
            int max = finder.max(data);
            System.out.println("max=" + max);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
