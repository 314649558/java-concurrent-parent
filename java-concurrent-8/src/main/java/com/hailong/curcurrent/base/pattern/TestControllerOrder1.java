package com.hailong.curcurrent.base.pattern;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2020/3/15.
 * 控制不同线程之间输出的顺序
 *
 *
 */
public class TestControllerOrder1 {
    public static void main(String[] args) throws Exception{
        WaitNotify wn=new WaitNotify(1,5);

        new Thread(()->{
            wn.print("b",2,3);
        }).start();

        new Thread(()->{
            wn.print("c",3,1);
        }).start();

        new Thread(()->{
            wn.print("a",1,2);
        }).start();
    }
}

class WaitNotify {

    private int flag;
    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    public void print(String str, int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}


