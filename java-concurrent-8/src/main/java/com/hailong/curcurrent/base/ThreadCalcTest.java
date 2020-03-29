package com.hailong.curcurrent.base;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2020/3/11.
 *
 * 利用同步锁保证数据计算的正确性，如果不加锁很有可能出现错误的结果
 *
 */
@Slf4j(topic = "c.ThreadCalcTest")
public class ThreadCalcTest {
    static int index=0;
    static Object lock=new Object();
    public static void main(String[] args) throws InterruptedException{



        Thread t1=new Thread(()->{
            for(int i=0;i<5000;i++){
                synchronized (lock) {
                    index++;
                }
            }
        });

        Thread t2=new Thread(()->{
            for(int i=0;i<5000;i++){
                synchronized (lock) {
                    index--;
                }
            }
        });


        t1.start();
        t2.start();
        t1.join();   //等待线程t1执行完成
        t2.join();
        log.info("----> " +index);


    }

}
