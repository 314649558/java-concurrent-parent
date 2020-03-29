package com.hailong.curcurrent.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2020/3/23.
 */
public class Sleeper {
    public static void sleep(long time, TimeUnit timeUnit){
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
