package com.hailong.curcurrent.juc;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import static com.hailong.curcurrent.util.Sleeper.sleep;
/**
 * Created by Administrator on 2020/3/24.
 */
public class TestStampedLock {
    public static void main(String[] args) {
        StampedLockContainer container=new StampedLockContainer("hailong");
        new Thread(()->{
            container.read();
        },"t1").start();
        sleep(500,TimeUnit.MILLISECONDS);
        new Thread(()->{
            container.write("yuanhailong");
        },"t2").start();
    }
}
/**
 * StampedLock 主要通过一个戳来判断对象持有锁是否发生了变化，读锁和读锁间产生相同的戳
 * 流程如下
 *        1 第一个读线程尝试获得乐观读锁（可以理解为没有枷锁）
 *        2 第二个读线程尝试获得乐观读锁，并对比戳是否发生了变化
 *                2.1 如果没有发生变化，戳会相同，直接返回数据
 *                2.2 如果发生了变化[其他线程尝试获得写锁]，乐观读锁尝试获得读锁（和写锁互斥，如果此时其他线程获得了写锁，并且正在执行，在获得读锁时会发生阻塞）
 *        3 开另一个线程尝试获得写说，写锁会导致戳发生变化
 */
@Slf4j(topic = "c.StampedLockContainer")
class StampedLockContainer{
    private String data;
    public StampedLockContainer(String data) {
        this.data = data;
    }
    StampedLock stampedLock=new StampedLock();
    public String read(){
        //获得乐观锁
        long stamp = stampedLock.tryOptimisticRead();
        log.info("optimistic read locking... {}",stamp);
        sleep(1, TimeUnit.SECONDS);
        if(stampedLock.validate(stamp)){
            log.info("read finish...");
            return data;
        }
        //锁升级  乐观读锁【就是没有加锁】->读锁【可以保证和其他写锁是互斥的】
        log.info("updateing to read locking... {}",stamp);
        try{
            stamp=stampedLock.readLock();
            log.info("read lock {}",stamp);
            sleep(1,TimeUnit.SECONDS);
            log.info("read finish... {}",stamp);
            return data;
        }finally {
            log.info("read unlock {}",stamp);
            stampedLock.unlock(stamp);
        }
    }
    public void write(String data){
        long stamp=stampedLock.writeLock();
        log.info("write log {}",stamp);
        try{
            sleep(2,TimeUnit.SECONDS);
            this.data=data;
        }finally {
            log.info("write unlock {}",stamp);
            stampedLock.unlock(stamp);
        }
    }
}
