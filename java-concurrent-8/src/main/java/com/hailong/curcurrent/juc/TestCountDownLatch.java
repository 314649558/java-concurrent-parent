package com.hailong.curcurrent.juc;

import com.hailong.curcurrent.util.Sleeper;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2020/3/25.
 */
public class TestCountDownLatch {
    private final static Integer NUM=10;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM);
        String[] all=new String[NUM];
        Random r=new Random();
        for(int k=0;k<NUM;k++) {
            int n=k;
            executorService.submit(() -> {
                for (int i = 0; i <= 100; i++) {
                    Sleeper.sleep(r.nextInt(100), TimeUnit.MILLISECONDS);
                    all[n] = i + "%";
                    System.out.print( Arrays.toString(all));
                }
            });
        }
        executorService.shutdown();
    }
}
