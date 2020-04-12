package com.hailong.netty.netty.inboundandoutboundhandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/12
 * Description:
 */
@Slf4j(topic = "c.MyClientMsgHandler")
public class MyClientMsgHandler extends SimpleChannelInboundHandler<Long>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        log.info("接收服务端[{}]数据:{}",ctx.channel().remoteAddress(),msg);
    }


    /**
     * 客户端连接后立马发送消息给服务端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("MyClientMsgHandler channelActive 被调用");
        ctx.writeAndFlush(12345678L);//发送一个Long数据给服务端
        //1 16个字节 会导致服务端的业务处理类handler调用多次【下面的数据会导致服务端两次调用】
        //2 由于业务处理类需要的是一个Long，但是传递的不是Long类型会导致数据传递到编码器处理类的时候encode方法不会被调用
        //3 可以查看编码器父类MessageToByteEncoder中的Write方法的实现逻辑
        //ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd", CharsetUtil.UTF_8));
    }
}
