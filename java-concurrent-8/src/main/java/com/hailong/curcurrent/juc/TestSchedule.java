package com.hailong.curcurrent.juc;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2020/3/22.
 */
@Slf4j(topic = "c.TestSchedule")
public class TestSchedule {
    public static void main(String[] args) {

        LocalDateTime now=LocalDateTime.now();//当前时间
        log.info("{}",now);

        //获取本周四 18点整的时间
        LocalDateTime time=now.withHour(18).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);


        if(now.compareTo(time)>0){
            time=time.plusWeeks(1);
        }

        log.debug("{}",time);


        //首次启动时候的应该延时的时间
        long initDelay= Duration.between(now,time).toMillis();
        long period=7*24*60*60*1000;
        ScheduledExecutorService pool= Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(()->{
            log.info("running...");
        },initDelay,period, TimeUnit.MILLISECONDS);

    }
}
