package com.hailong.netty.netty.inboundandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/12
 * Description:
 */
@Slf4j(topic = "c.MyLongToByteEncoder")
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        log.info("MyLongToByteEncoder encode 被调用");
        log.info("msg={}",msg);
        //消息写出去
        out.writeLong(msg);

    }
}
