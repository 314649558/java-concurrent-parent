package com.hailong.netty.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/13
 * Description:
 */
@Slf4j(topic = "c.MyServerHandler")
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    /**
     * 读取客户端发送的数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        /**
         * 1 读取客户端消息
         * 2 由于服务端每次读取客户端消息的字节数是不固定【在没有任何编码器的情况下】的，因此会发生粘包和拆包的问题
         */
        byte[] buffer=new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        String message=new String(buffer, CharsetUtil.UTF_8);
        log.info("服务端接收到消息:{}",message);
        log.info("服务器调用次数:{}",++count);

    }

    /**
     * 数据读取完成后会送数据给客户端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        String uuid = UUID.randomUUID().toString()+"        ";
        log.info("服务端回送消息UUID : {}",uuid);
        ByteBuf byteBuf = Unpooled.copiedBuffer(uuid, CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBuf);

    }
}
