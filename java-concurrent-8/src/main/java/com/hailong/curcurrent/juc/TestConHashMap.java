package com.hailong.curcurrent.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2020/3/28.
 */
@Slf4j(topic = "c.TestConHashMap")
public class TestConHashMap {

    public static void main(String[] args) {
        new Thread(()->{
            ConcurrentHashMap<String,String> concurrentHashMap=new ConcurrentHashMap<>();
            log.info("starting put data");
            concurrentHashMap.put("name","hailong");
            log.info("starting get data");
            concurrentHashMap.get("name");
        },"t1").start();
    }
}
