package com.hailong.netty.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by yuanhailong on 2020/4/5.
 * 说明:
 *  1 自定义一个Handler，需要实现netty提供的相关HandlerAdapter
 */
@Slf4j(topic = "c.NettyServerHandler")
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取实际的数据（可以获取到客户端发送过来的数据）
     *
     * @param ctx 上下文对象，含有通道pipeline和管道channel
     * @param msg  客户端发送过来的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //读取客户端数据
        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        log.info("客户端发送的数据:id={}, name={}",student.getId(),student.getName());
    }

    /**
     * 数据读取完毕后，可以将数据返回
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端",CharsetUtil.UTF_8));
    }

    /**
     * 如果在通信过程中发生异常，需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
