package com.hailong.netty.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2020/4/5.
 */
@Slf4j(topic = "c.NettyClientHandler")
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道准备就绪以后出发，即连接成功后出发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(1).setName("hailong").build();
        ctx.writeAndFlush(student);
    }

    /**
     * 接收服务端返回的消息
      * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        log.info("服务器返回给客户端消息:{}",buf.toString(CharsetUtil.UTF_8));
        log.info("服务器地址是:{}",ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
