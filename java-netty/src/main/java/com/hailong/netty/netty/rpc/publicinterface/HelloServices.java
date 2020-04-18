package com.hailong.netty.netty.rpc.publicinterface;

/**
 * Created by Administrator on 2020/4/18.
 * 自定义dubbo rpc
 *   这个接口是消费者和提供方都需要使用的接口
 */
public interface HelloServices {

    String hello(String mes);

}
