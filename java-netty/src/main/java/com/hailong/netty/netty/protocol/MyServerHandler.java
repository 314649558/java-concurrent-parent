package com.hailong.netty.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.UUID;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/13
 * Description:
 */
@Slf4j(topic = "c.MyServerHandler")
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    /**
     * 读取客户端发送的数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {


        int len=msg.getLen();
        byte[] content = msg.getContent();
        log.info("length={}",len);
        log.info("content={}",new String(content, CharsetUtil.UTF_8));
        log.info("服务器收到消息数量={}",++count);
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /**
     * 数据读取完成后会送数据给客户端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        for (int i=1;i<=5;i++) {
            String uuid = UUID.randomUUID().toString() + "        ";
            log.info("服务端回送消息UUID : {}", uuid);
            byte[] content = uuid.getBytes(CharsetUtil.UTF_8);
            MessageProtocol protocol = new MessageProtocol();
            protocol.setLen(content.length);
            protocol.setContent(content);
            ctx.writeAndFlush(protocol);
        }

    }
}
