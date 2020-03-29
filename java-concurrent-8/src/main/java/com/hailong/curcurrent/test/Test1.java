package com.hailong.curcurrent.test;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2020/3/7.
 */
@Slf4j(topic = "c.Test1")
public class Test1 {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug("running");
            }
        }).start();
        log.debug("running");
    }
}
