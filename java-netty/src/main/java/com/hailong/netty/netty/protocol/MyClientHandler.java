package com.hailong.netty.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/13
 * Description:
 */
@Slf4j(topic = "c.MyClientHandler")
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

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
        for(int i=1;i<=5;i++){
            String message="发送第["+i+"]条消息";
            byte[] content=message.getBytes(CharsetUtil.UTF_8);

            //构建协议对象
            //自定义协议对象
            MessageProtocol messageProtocol=new MessageProtocol();
            messageProtocol.setLen(content.length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }
    /**
     * 读取服务器回送的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len=msg.getLen();
        byte[] content = msg.getContent();

        log.info("length={}",len);
        log.info("content={}",new String(content,CharsetUtil.UTF_8));

        log.info("客户端收到消息数量={}",++count);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
