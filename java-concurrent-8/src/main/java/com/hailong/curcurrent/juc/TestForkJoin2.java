package com.hailong.curcurrent.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by Administrator on 2020/3/22.
 * ForkJoin采用分而治之的思想，如果要提高性能对于如何进行划分非常重要
 */
public class TestForkJoin2 {
    public static void main(String[] args) {
        ForkJoinPool pool=new ForkJoinPool(2);
        System.out.println(pool.invoke(new MyTask(5)));

    }
}

@Slf4j(topic = "c.MyTask")
class MyTask extends RecursiveTask<Integer>{

    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {

        if(n==1){
            log.info("join() {}",n);
            return 1;
        }

        MyTask t1=new MyTask(n-1);
        t1.fork();//启动一个进行计算
        log.info("fork() {} + {}",n,t1);
        int result=n+t1.join();//获得结果
        log.info("join() {} + {}",n,t1);
        return result;
    }

    @Override
    public String toString() {
        return "{" + n + "}";
    }
}
