package com.hailong.netty.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/11
 * Description:
 *    TextWebSocketFrame表示一个文本帧（frame）
 */
@Slf4j(topic = "c.NettyWebSocketServerHandler")
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("服务器端收到消息:{}",msg.text());

        //消息回显到客户端
        //这里一定要用TextWebSocketFrame封装会送消息的内容
        String retMsg="[server] "+ LocalDateTime.now()+" "+msg.text();
        ctx.writeAndFlush(new TextWebSocketFrame(retMsg));
    }

    /**
     * 客户端连接后，就会触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerAdded 被调用 {}",ctx.channel().id().asLongText());
        log.info("handlerAdded 被调用 {}",ctx.channel().id().asShortText());
    }

    /**
     * 客户端断开连接的时候会被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerRemoved 被调用 {}",ctx.channel().id().asLongText());
        log.info("handlerRemoved 被调用 {}",ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("异常发生:{}",cause.getMessage());
        ctx.close();
    }
}
