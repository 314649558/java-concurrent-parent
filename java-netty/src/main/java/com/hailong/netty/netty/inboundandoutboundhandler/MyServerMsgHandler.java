package com.hailong.netty.netty.inboundandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/12
 * Description:
 *    本handler处理MyServerByteToLongDecoder之后，MyServerByteToLongDecoder将消息解码后传递给本Handler
 *    MyByteToLongDecoder 处理的消息后续会成为一个Long
 */
@Slf4j(topic = "c.MyServerMsgHandler")
public class MyServerMsgHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        log.info("客户端[{}] 发送消息:{}",ctx.channel().remoteAddress(),msg);
        ctx.writeAndFlush(987654321L);//写一个数据给客户端
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
