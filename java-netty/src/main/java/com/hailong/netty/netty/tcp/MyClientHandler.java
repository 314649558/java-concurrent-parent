package com.hailong.netty.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/13
 * Description:
 */
@Slf4j(topic = "c.MyClientHandler")
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 每一个客户端都会有一个Handler，因此每个不同的客户的这个值不会共享【如果要共享需要使用static】
     */
    private int count;

    /**
     * 循环发送数据给服务端【为了模拟服务端接收数据粘包和拆包的问题】
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("MyClientHandler channelActive is executed");

        //数据刷写到服务端
        for(int i=1;i<=10;i++){
            String msg="Hello Server "+i;
            ctx.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        byte[] buffer=new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        String message=new String(buffer,CharsetUtil.UTF_8);
        log.info("客户端收到消息:{}",message);
        log.info("客户端接收数据次数:{}",++count);

    }
}
