package com.hailong.curcurrent.base;

/**
 * Created by Administrator on 2020/3/7.
 * 调试跟踪线程栈和栈帧
 */
public class TestFrames {
    public static void main(String[] args) {

        new Thread(()->{
            method1(100);
        },"t1").start();

        method1(10);



    }


    public static void method1(int x){
        int y=x+1;
        Object o=method2();
        System.out.println(o);
    }


    public static Object method2(){
        Object object=new Object();
        return object;
    }

}
