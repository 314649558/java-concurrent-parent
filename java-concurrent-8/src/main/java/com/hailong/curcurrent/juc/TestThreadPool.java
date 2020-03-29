package com.hailong.curcurrent.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yuanhailong on 2020/3/19.
 * 测试主类
 */
@Slf4j(topic = "c.TestThreadPool")
public class TestThreadPool {
    public static void main(String[] args) {
        ThreadPool threadPool=new ThreadPool(2,1000,TimeUnit.MILLISECONDS,10,(queue,task)->{
            queue.put(task);
        });
        for (int i=0;i<15;i++){
            int j=i;
            threadPool.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(10000);   //模拟每个线程正在执行任务，并且是运行时间很长的任务
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("{}",j);
            });
        }

    }
}

/**
 * 拒绝策略 【策略模式】
 * @param <T>
 */
@FunctionalInterface
interface RejectPolicy<T>{
    void reject(BlockQueue<T> queue,T task);
}

/**
 * 自定义线程池
 */
@Slf4j(topic = "c.ThreadPool")
class ThreadPool{
    private int coreSize;                     //核心线程池大小
    private BlockQueue<Runnable> taskQueue;   //当工作线程容量满了的时候任务放入阻塞队列
    private int timeout;                      //超时时间
    private TimeUnit timeUnit;                //时间单位
    private int queueCapacity;               //队列容量
    private RejectPolicy<Runnable> rejectPolicy;       //拒绝策略
    private HashSet<Worker> workers=new HashSet<>();   //存放工作者线程

    public ThreadPool(int coreSize, int timeout, TimeUnit timeUnit, int queueCapacity,RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.queueCapacity = queueCapacity;
        taskQueue=new BlockQueue<>(this.queueCapacity);
        this.rejectPolicy=rejectPolicy;
    }



    public void execute(Runnable task){
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                log.info("新增worker {},{}", worker, task);
                workers.add(worker);
                worker.start();
            } else {
                /*log.debug("加入任务队列 {}", task);
                taskQueue.put(task);*/
                // 1) 死等
                // 2) 带超时等待
                // 3) 让调用者放弃任务执行
                // 4) 让调用者抛出异常
                // 5) 让调用者自己执行任务
                taskQueue.tryPut(rejectPolicy,task);
            }
        }
    }


    /**
     * 工作者线程
     */
    class Worker extends Thread{
        private Runnable task;
        public Worker(Runnable task) {
            this.task = task;
        }
        @Override
        public void run() {
            while(task!=null || (task=taskQueue.poll(timeout,timeUnit))!=null){
                try{
                    log.info("正在执行...{}",task);
                    task.run();
                }catch (Exception e){

                }finally {
                    task=null;
                }
            }
            synchronized (this){
                log.info("worker 被移除...{}",this);
                workers.remove(this);
            }
        }
    }
}

/**
 * 自定义阻塞队列
 */
@Slf4j(topic = "c.BlockQueue")
class BlockQueue<T>{
    //阻塞队列容量
    private int capacity;
    private Deque<T> deque=new ArrayDeque<>();

    private ReentrantLock lock=new ReentrantLock();
    private Condition emptyWaitSet=lock.newCondition();    //等待条件
    private Condition fullWaitSet=lock.newCondition();
    public BlockQueue(int capacity) {
        this.capacity = capacity;
    }
    /**
     * 从队列中获取数据，带超时时间
     * @param timeout
     * @param timeUnit
     * @return
     */
    public T poll(long timeout, TimeUnit timeUnit){
        lock.lock();
        try{
            long nanos=timeUnit.toNanos(timeout);
            while(deque.isEmpty()){
                try {
                    if(nanos<=0){
                        return null;
                    }
                    nanos=emptyWaitSet.awaitNanos(nanos); //返回剩余需要等待的时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            T t=deque.removeFirst();
            log.info("poll 返回 {}",t);
            fullWaitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }

    /**
     * 从队列中获取数据，不带超时时间，如果队列中一直没有数据，会死等[一直等]下去
     * @return
     */
    public T take(){
        lock.lock();
        try{
            while(deque.isEmpty()){
                try {
                    emptyWaitSet.await();   //队列数据为空，需要等待其他线程向队列中存放数据
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t=deque.removeFirst();
            log.info("take 返回 {}",t);
            fullWaitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }

    /**
     * 存放数据，如果队列满了会一直死等下去
     * @param t
     */
    public void put(T t){
        lock.lock();
        try{
            while(deque.size()==capacity){
                try {
                    log.info("put 等待加入 {}",t);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("put {}",t);
            deque.addLast(t);
            emptyWaitSet.signal();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 存放数据，如果超过了一定时间则进入下一次循环
     * @param t
     */
    public boolean offer(T t,long timeout,TimeUnit timeUnit){
        lock.lock();
        try{
            long nanos=timeUnit.toNanos(timeout);
            while(deque.size()==capacity){
                try {
                    if(nanos<=0){
                        return false;
                    }
                    log.info("offer 等待加入 {}",t);
                    nanos=fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            deque.addLast(t);
            log.info("offer {}",t);
            emptyWaitSet.signal();
            return true;
        }finally {
            lock.unlock();
        }
    }


    /**
     * 队列满了后怎么做，让用户自己选择
     * @param rejectPolicy
     * @param t
     */
    public void tryPut(RejectPolicy<T> rejectPolicy,T t){
        lock.lock();
        try{
            if(deque.size()==capacity){
                rejectPolicy.reject(this,t);   //拒绝策略
            }else {
                log.info("put {}", t);
                deque.addLast(t);
                emptyWaitSet.signal();
            }
        }finally {
            lock.unlock();
        }
    }
}
