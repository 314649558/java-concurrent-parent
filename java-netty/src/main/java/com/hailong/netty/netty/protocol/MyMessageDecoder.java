package com.hailong.netty.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/15
 * Description:
 *  自定义解码器
 */
@Slf4j(topic = "c.MyMessageDecoder")
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("MyMessageDecoder decode 被调用");

        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        MessageProtocol protocol=new MessageProtocol();
        protocol.setLen(length);
        protocol.setContent(content);
        //传递给下一个handler进行处理
        out.add(protocol);

    }
}
