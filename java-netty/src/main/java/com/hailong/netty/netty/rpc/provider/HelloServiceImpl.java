package com.hailong.netty.netty.rpc.provider;

import com.hailong.netty.netty.rpc.publicinterface.HelloServices;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/18
 * Description:
 */
@Slf4j(topic = "c.HelloServiceImpl")
public class HelloServiceImpl implements HelloServices {

    //注意：当客户端通过代理对象调用一次，会是一个新的对象，因此需要计数的时候需要保证这个变量是static的来达到共享的目的
    private static int count=0;

    @Override
    public String hello(String mes) {

        log.info("HelloServiceImpl hashcode:{}",this.hashCode());


        log.info("收到消费方信息:{}",mes);
        //根据mes返回不同的消息
        if(mes!=null){
            return "你好客户端，我已经收到你的消息 ["+mes+"] 第" + (++count) +"次收到消息";
        }else{
            return "你好客户端，你发送的消息内容为空";
        }
    }
}
