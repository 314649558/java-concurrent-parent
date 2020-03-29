package com.hailong.concurrent.sample;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2020/3/7.
 */
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
public class MyBenchmark2 {
    static int i=0;
    @Benchmark
    public void a() throws Exception{
        i++;
    }


    /**
     * 在java中会有一个即时编译器(JIT)会对没有用的代码进行消除默认情况下JIT优化编译是开启的
     * -XX:-EliminateLocks   关闭JIT优化
     * @throws Exception
     */
    @Benchmark
    public void b() throws Exception{
        Object lock=new Object();
        synchronized (lock){ //这个lock对象是一个局部变量，因此他的锁线程改变或占用，如果没有JIT优化会导致每次都要加锁从而影响性能
            i++;
        }
    }

}
