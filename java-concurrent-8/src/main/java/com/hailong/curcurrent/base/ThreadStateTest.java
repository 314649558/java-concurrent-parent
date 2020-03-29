package com.hailong.curcurrent.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2020/3/9.
 * java 线程六种状态演示
 * NEW 创建了一个线程，但是线程没有启动，既没有调用start方法
 * RUNNABLE 在JAVA的状态设置中 运行状态 （对应操作系统中的RUNNING）
 * TIMED_WAITING 当调用了sleep这种有时间限制的方法时候进入这种状态
 * TERMINATED  线程执行完成后的状态
 * WAITING 当一个线程等待另一个线程执行完成的时候进入（注：不是加锁）
 * BLOCKED 当一个线程需要获得锁而没有获得的时候进入该状态
 */
@Slf4j(topic = "c.ThreadStateTest")
public class ThreadStateTest {

    public static void main(String[] args) throws Exception{

        //仅仅创建状态，不启动NEW
        Thread t1=new Thread(()->{},"t1");

        //一直运行中RUNNABLE
        Thread t2=new Thread(()->{
            while(true){

            }
        },"t2");


        //很快执行完成TERMINATED
        Thread t3=new Thread(()->{
            log.debug("执行任务");
        },"t3");

        //TIMED_WAITING
        Thread t4=new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t4");


        //WAITING
        Thread t5=new Thread(()->{
            synchronized (ThreadStateTest.class) {
                try {
                    t2.join();   //等待t2执行完成，但是t2是个死循环，所以它一直等待  WAITING
                    //t2.join(1000);   //如果加上一个时间，表示最多等待1000毫秒，那么此时线程的状态是TIMED_WAITING
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t5");

        //BLOCKED
        Thread t6=new Thread(()->{
            synchronized (ThreadStateTest.class) {  //想要获得ThreadStateTest锁，但是t5线程获得了锁，而t5又会一直等待下去，所以这里一直获取不到锁，等待锁从而进入阻塞状态
                log.debug("BLOCKED");
            }
        },"t6");


        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();

        TimeUnit.SECONDS.sleep(1);

        log.debug("{}",t1.getState());
        log.debug("{}",t2.getState());
        log.debug("{}",t3.getState());
        log.debug("{}",t4.getState());
        log.debug("{}",t5.getState());
        log.debug("{}",t6.getState());
    }
}
