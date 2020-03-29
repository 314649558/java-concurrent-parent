package com.hailong.curcurrent.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2020/3/14.
 */
@Slf4j(topic = "c.TestNotifySleep2")
public class TestNotifySleep2 {
    //是否有烟
    public static boolean isHaveSmoke=false;
    //外卖是否到了
    public static boolean isHaveWaiMai=false;
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(()->{
            synchronized (TestNotifySleep2.class) {
                while (!isHaveSmoke) {
                    try {
                        log.info("没有烟...");
                        TestNotifySleep2.class.wait();
                        if(isHaveSmoke) {
                            log.info("烟道咯,可以干活了");
                        }else{
                            log.info("被其他人唤醒了,可是烟还没有到，我得等烟");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"t1");
        Thread t2=new Thread(()->{
            synchronized (TestNotifySleep2.class) {
                while (!isHaveWaiMai) {
                    try {
                        log.info("外卖还没到...");
                        TestNotifySleep2.class.wait();
                        if(isHaveWaiMai) {
                            log.info("吃完饭,可以干活了");
                        }else{
                            log.info("被其他人唤醒了，可是外卖还没到，我得等外卖");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"t2");
        Thread wake1=new Thread(()->{
            synchronized (TestNotifySleep2.class) {
                log.info("烟到了...");
                isHaveSmoke = true;
                //注意这里如果调用的是notify方法可能会出问题，因为notify是随机唤醒waitSet里面的任意一个线程，因此他可能唤醒的不是t1线程
                TestNotifySleep2.class.notifyAll();
            }
        },"wake1");
        Thread wake2=new Thread(()->{
            synchronized (TestNotifySleep2.class) {
                log.info("外卖到了...");
                isHaveWaiMai = true;
                TestNotifySleep2.class.notifyAll();
            }
        },"wake1");


        t1.start();
        t2.start();
        //休息一秒钟 让t1,t2确保已经起来了
        TimeUnit.SECONDS.sleep(1);
        wake1.start();
        wake2.start();
    }

}
