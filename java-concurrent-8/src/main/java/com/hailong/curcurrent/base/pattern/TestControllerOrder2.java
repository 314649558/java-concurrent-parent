package com.hailong.curcurrent.base.pattern;

/**
 * Created by Administrator on 2020/3/15.
 * 控制不同线程之间输出的顺序
 *
 *
 */
public class TestControllerOrder2 {
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


