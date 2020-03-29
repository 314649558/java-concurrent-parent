package com.hailong.curcurrent.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2020/3/15.
 * ReentrantLock 和 synchronized 都是可重入锁，不过ReentrantLock多了一下特性
 * 1 ReentrantLock 支持公平锁
 * 2 可重入 （synchronized也支持）
 * 3 锁超时
 * 4 多条件变量，既有多个WaitSet区域，而synchronized只有一个WaitSet区域
 */
@Slf4j(topic = "c.TestReentrantLockCondition")
public class TestReentrantLockCondition {


    static Boolean isHaveSmoke=false;
    static Boolean isHaveTakeout=false;
    static ReentrantLock lock=new ReentrantLock();
    static Condition condition1=lock.newCondition();
    static Condition condition2=lock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        Thread t1=new Thread(()->{
            lock.lock();
            try{
                log.info("烟没到，我要等烟");
                while(!isHaveSmoke){
                    log.info("烟怎么还没到，继续等");
                    try {
                        condition1.await();  //等待
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("烟到了，开始干活咯");
            }finally {
                lock.unlock();
            }

        },"小王");


        Thread t2=new Thread(()->{
            lock.lock();
            try{
                log.info("外卖没到，我要等外卖");
                while(!isHaveTakeout){
                    log.info("外卖怎么还没到，继续等");
                    try {
                        condition2.await();  //等待
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("外卖到了，开始干活咯");
            }finally {
                lock.unlock();
            }
        },"小菲");


        t1.start();
        t2.start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            lock.lock();
            try {
                log.info("烟送到了");
                isHaveSmoke=true;
                //它只会唤醒在condition1的WaitSet中的线程
                condition1.signal();  //这个跟Object的Notfiy一样，只唤醒WaitSet一个阻塞线程
            }finally {
                lock.unlock();
            }
        },"smoke").start();


        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            lock.lock();
            try {
                log.info("外卖送到了");
                isHaveTakeout=true;
                //它只会唤醒在condition2的WaitSet中的线程
                condition2.signal();
            }finally {
                lock.unlock();
            }
        },"takeout").start();


    }


}
