package com.hailong.curcurrent.base.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2020/3/14.
 * 保护性暂停 设计模式
 *
 * Thred.join实现原理类似
 *
 */
@Slf4j(topic = "c.TestGuardObject")
public class TestGuardObject {
    public static void main(String[] args) throws Exception {
        test1();
    }
    public static void test1() throws InterruptedException {
        final GuardObject guardObject=new GuardObject();

        new Thread(()->{
            log.info("获取数据");
            guardObject.get(200000);
            log.info("获取完成");
        },"t1").start();


        TimeUnit.SECONDS.sleep(3);
        new Thread(()->{
            log.info("产生数据");
            guardObject.complete(new Object());
            log.info("产生完成");
        },"t2").start();
    }
}
class GuardObject{

    private Object respose;

    public synchronized Object get(long timeout){
        long begin=System.currentTimeMillis();

        long parseTime=0;

        //如果没有获得结果就继续等待
        while (this.respose==null){
            long waitTime=timeout-parseTime;
            if(waitTime<=0){
                break;
            }

            try {
                this.wait(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            parseTime=System.currentTimeMillis()-begin;
        }
        return respose;
    }

    public synchronized  void complete(Object respose){
            this.respose = respose;
            this.notify();
    }


}
