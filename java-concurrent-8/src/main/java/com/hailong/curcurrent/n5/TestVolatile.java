package com.hailong.curcurrent.n5;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2020/3/15.
 */
@Slf4j(topic = "c.TestVolatile")
public class TestVolatile {

    volatile static boolean run=true;  //加上volatile后可以保证可见性，意思就是其他线程需要从主存中获取消息而不会从工作内存中获取消息，对性能有一定影响

    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{
            while (run){
                //System.out.println("-->");  //如果加上这一行代码就能够停止运行（既可以保证可见性），原因是println方法加了同步锁，一旦加上同步锁就会从JMM的主存中获取数据
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);
        log.info("停止");
        run=false;

    }

}
