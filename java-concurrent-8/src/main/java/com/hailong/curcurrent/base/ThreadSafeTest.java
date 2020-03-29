package com.hailong.curcurrent.base;

import java.util.ArrayList;

/**
 * Created by Administrator on 2020/3/11.
 */
public class ThreadSafeTest {

    static final int THREAD_NUM=2;
    static final int LOOP_NUM=1000;

    public static void main(String[] args) {
        //ThreadUnSafe threadUnSafe=new ThreadUnSafe();
        ThreadSafe threadSafe=new ThreadSafe();
        for (int i=0;i<THREAD_NUM;i++){
            new Thread(()->{
                threadSafe.method1(LOOP_NUM);
            }).start();
        }
    }


}

/**
 * 非线程安全的
 * lst是全局变量，将会放在heap内存中，被多个线程共享
 */
class ThreadUnSafe{
    ArrayList<String> lst=new ArrayList<>();
    public void method1(int loopNum){
        for(int i=0;i<loopNum;i++){
            method2();
            method3();
        }
    }
    public void method2(){
        lst.add("1");
    }
    public void method3(){
        lst.remove(0);
    }
}


/**
 * 线程安全的类
 * lst被放在了局部变量中，每个线程会有自己的栈，lst会在栈内存中，多个线程使用的都是自己栈内存中的对象
 */
class ThreadSafe{

    public void method1(int loopNum){
        ArrayList<String> lst=new ArrayList<>();
        for(int i=0;i<loopNum;i++){
            method2(lst);
            method3(lst);
        }
    }
    public void method2(ArrayList<String> lst){
        lst.add("1");
    }
    public void method3(ArrayList<String> lst){
        lst.remove(0);
    }
}


