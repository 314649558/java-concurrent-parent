package com.hailong.curcurrent.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by Administrator on 2020/3/22.
 */
@Slf4j(topic = "c.TestCustomAQS")
public class TestCustomAQS {
    public static void main(String[] args) {
        MyLock lock=new MyLock();

        new Thread(()->{

            lock.lock();
            log.info("locking1 ...");
            //lock.lock();  //如果加了两次锁则会阻塞，因为是非重入锁
            try{
                log.info("locking ...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                lock.unlock();
                log.info("unlocking ...");
            }
        },"t1").start();

        new Thread(()->{
            lock.lock();
            try{
                log.info("locking ...");
            }finally {
                lock.unlock();
                log.info("unlocking ...");
            }
        },"t2").start();
    }

}


/**
 * 自定义锁[独占锁]
 */
class MyLock implements Lock{

    class MySync extends AbstractQueuedSynchronizer{
        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0,1)){
                //加锁，将当前线程设置为Owner
                setExclusiveOwnerThread(Thread.currentThread());

                return true;
            }
            //加锁失败
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * 是否独占锁（即不可重入锁）
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState()==1;
        }

        public Condition newCondition(){
            return  new ConditionObject();
        }
    }

    private MySync sync=new MySync();


    /**
     * 加锁，不成功会进入阻塞队列
     */
    @Override
    public void lock() {
        sync.acquire(1);
    }

    /**
     * 加锁，但是可被其他线程打断
     * @throws InterruptedException
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    /**
     * 方法里面会调用tryRelease方法，并唤醒其他线程
     */
    @Override
    public void unlock() {
        sync.release(0);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
