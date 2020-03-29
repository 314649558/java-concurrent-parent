package com.hailong.curcurrent.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2020/3/8.
 */
@Slf4j(topic = "c.TwoParseTermination")
public class TwoParseTermination {
    public static void main(String[] args) throws Exception {
        TwoParseTermination1 test1=new TwoParseTermination1();
        test1.start();
        Thread.sleep(3500);
        test1.stop();
    }
}

@Slf4j(topic = "c.TwoParseTermination1")
class TwoParseTermination1{

    private Thread monitor;



    public void start(){
        monitor=new Thread(()->{
            for(;;){
                Thread current=Thread.currentThread();
                if(current.isInterrupted()){  //如果被其他线程打断
                    log.info("优雅的处理后续工作");
                    break;
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.info("程序监控中...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //如果程序正在睡眠[join,sleep,wait等方法]而被打断则会抛出异常，并且是否被打断将会设置为false
                    current.interrupt();  //重新设置打断标记为true
                }

            }
        });

        monitor.start();


    }


    public void stop(){
        monitor.interrupt();
    }


}
