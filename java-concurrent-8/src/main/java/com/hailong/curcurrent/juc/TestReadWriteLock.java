package com.hailong.curcurrent.juc;

import com.hailong.curcurrent.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * Created by Administrator on 2020/3/23.
 */
public class TestReadWriteLock {

    public static void main(String[] args) {
        ReadWriteObject readWriteObject=new ReadWriteObject();
        new Thread(()->{
            readWriteObject.write();
        },"t4").start();

        new Thread(()->{
            readWriteObject.read();
        },"t1").start();

        new Thread(()->{
            readWriteObject.read();
        },"t2").start();

        new Thread(()->{
            readWriteObject.write();
        },"t3").start();

    }

}
/**
 * 读写锁:
 *    1 多个读线程不会阻塞
 *    2 读写，写读，写写线程之间会阻塞
 *    3 读写锁不能升级（既不能重入）   如果一个线程先获得了读锁，不能重入写锁
 *    4 读写锁可以降级（既不能重入）   如果一个线程先获得了写锁，可以重入读锁
 */
@Slf4j(topic = "c.ReadWriteObject")
class ReadWriteObject{

    private ReentrantReadWriteLock rw=new ReentrantReadWriteLock();

    private ReentrantReadWriteLock.ReadLock readLock=rw.readLock();  //读锁

    private ReentrantReadWriteLock.WriteLock writeLock=rw.writeLock();  //写锁


    public Object read(){
        readLock.lock();
        try{
            log.info("read data");
            Sleeper.sleep(1, TimeUnit.SECONDS);  //休息一秒
        }finally {
            readLock.unlock();
        }
        return "read data";
    }
    public void write(){
        writeLock.lock();
        try{
            log.info("write data");
            Sleeper.sleep(1, TimeUnit.SECONDS);  //休息一秒
        }finally {
            writeLock.unlock();
        }
    }
}
