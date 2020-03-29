package com.hailong.curcurrent.n6;

import com.hailong.curcurrent.util.Sleeper;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2020/3/29.
 */
@Slf4j(topic = "c.TestCustoAtomicInteger")
public class TestCustoAtomicInteger {

    public static void main(String[] args) {
        MyAtomicInteger atomicInteger=new MyAtomicInteger(1);

        for(int i=0;i<10;i++){
            new Thread(()->{
                atomicInteger.decrment(1);
                log.info("{}",atomicInteger.getValue());
            },"decrment_"+(i+1)).start();
        }


        for(int i=0;i<10;i++){
            new Thread(()->{
                atomicInteger.incrment(1);
                log.info("{}",atomicInteger.getValue());
            },"incrment_"+(i+1)).start();
        }


        Sleeper.sleep(2, TimeUnit.SECONDS);

        log.info("final result {}",atomicInteger.getValue());

    }

}


class MyAtomicInteger{
    private static final Unsafe UNSAFE;
    private volatile int value;
    private static final long valueOffset;

    public MyAtomicInteger(int value) {
        this.value = value;
    }
    static {
        UNSAFE=UnsafeAccessor.getUnsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw  new RuntimeException(e);
        }
    }

    public int getValue() {
        return value;
    }

    /**
     * 原子操作，如果多个线程操作在调用compareAndSwapInt可能会失败从而导致下一次循环调用直到成功
     * @param amount
     */
    public void decrment(int amount){
        while (true){
            int prev=this.value;
            int next=prev-amount;
            if(UNSAFE.compareAndSwapInt(this,valueOffset,prev,next)){
                break;
            }
        }
    }

    public void incrment(int amount){
        while (true){
            int prev=this.value;
            int next=prev+amount;
            if(UNSAFE.compareAndSwapInt(this,valueOffset,prev,next)){
                break;
            }
        }
    }
}


