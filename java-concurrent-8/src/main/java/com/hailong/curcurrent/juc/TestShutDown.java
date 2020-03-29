package com.hailong.curcurrent.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2020/3/21.
 */
@Slf4j(topic = "c.TestShutDown")
public class TestShutDown {
    public static void main(String[] args) {

        ExecutorService executorService=Executors.newFixedThreadPool(2);

        Future<String> future1 = executorService.submit(() -> {
            log.info("begin 1");
            TimeUnit.SECONDS.sleep(1);
            log.info("end 1");
            return "1";
        });

        Future<String> future2 = executorService.submit(() -> {
            log.info("begin 2");
            TimeUnit.SECONDS.sleep(1);
            log.info("end 2");
            return "2";
        });

        Future<String> future3 = executorService.submit(() -> {
            log.info("begin 3");
            TimeUnit.SECONDS.sleep(1);
            log.info("end 3");
            return "3";
        });

        log.info("start shutdown");
        //executorService.shutdown();//提交到线程池所有的任务完成后真正关闭（优雅的关闭） ,它不会阻塞调用此方法的线程
        executorService.shutdownNow();//立即断开
        log.info("call shutdown finish");







    }
}
