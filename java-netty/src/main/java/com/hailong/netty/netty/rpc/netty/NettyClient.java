package com.hailong.netty.netty.rpc.netty;

import com.hailong.netty.ConfigConst;
import com.hailong.netty.netty.rpc.RPCConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/18
 * Description:
 *   rpc 消费方
 */
public class NettyClient {
    //定义一个线程池
    private static ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static NettyClientHandler client;

    //初始化客户端
    private static void initClient(){
        client=new NettyClientHandler();
        //创建NIOEventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(client);
                    }
                });
        try {
            bootstrap.connect(ConfigConst.ADDR,ConfigConst.PORT).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用代理模式，获取代理对象
     */
    public Object getBean(final Class<?> serviceClass){
        //使用lambda模式来调用代理对象方法
        /*return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass},
                (proxy,method,args)->{
           if(client == null){
               initClient();
           }
           client.setPara(RPCConstant.RPC_PROTOCOL_HEAD+args[0]);
            return executorService.submit(client).get();
        });*/


        //不使用lambda模式来实现【这样代码看起来更直观】
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class<?>[]{serviceClass},new InvocationHandler(){

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(client == null){
                    initClient();
                }
                client.setPara(RPCConstant.RPC_PROTOCOL_HEAD+args[0]);
                return executorService.submit(client).get();
            }
        });


    }
}
