package com.hailong.netty.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.extern.slf4j.Slf4j;


/**
 * Created by Administrator on 2020/4/6.
 */
@Slf4j(topic = "c.MyHttpChannelInitializer")
public class MyHttpChannelInitializer extends ChannelInitializer<SocketChannel> {


    /**
     * 向ch绑定的pipeline中添加Handler
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        log.info("piple hashcode: {}",pipeline.hashCode());

        //添加Http编码-解码的Handler
        pipeline.addLast(new HttpServerCodec());

        //添加自定义的Handler
        pipeline.addLast(new MyHttpChannelHandler());

        log.info("add handler finish");


    }
}
