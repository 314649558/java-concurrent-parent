package com.hailong.curcurrent.base;

/**
 * Created by Administrator on 2020/3/14.
 *
 * Wait 和 Sleep的区别
 * 1 Wait必须拥有锁对象后才能使用，否则会出错
 * 2 调用Wait方法后会释放锁进入WaitSet区域等待Notfiy方法唤醒，进入EntrySet区域
 * 3 sleep方法会一直等待，并且不会释放锁
 * 4 Wait是每个Object方法都有的，而sleep是线程对象的
 */
public class TestNotifySleep1 {


    public static void main(String[] args) {


        //抛出异常，调用Wait时候没有持有锁对象
        new Thread(()->{
            try {
                TestNotifySleep1.class.wait();
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        //不会出现异常，因为持有了锁对象
        new Thread(()->{
            try {
                synchronized (TestNotifySleep1.class) {
                    TestNotifySleep1.class.wait();
                    System.out.println(Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();


    }



}
