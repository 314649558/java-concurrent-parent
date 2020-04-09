package com.hailong.netty.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import java.net.URI;
/**
 * Created by Administrator on 2020/4/6.
 */
@Slf4j(topic = "c.MyHttpChannelHandler")
public class MyHttpChannelHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        log.info("MyHttpChannelHandler hashcode : {}",this.hashCode());
        if(msg instanceof HttpRequest){
            log.info("msg 类型:{}",msg.getClass());
            log.info("客户端地址:{}",ctx.channel().remoteAddress());
            //限制资源访问
            HttpRequest httpRequest= (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            /*if("".equals(uri.getPath())){
                log.info("服务器请求已经产生了,不在作出响应");
                return;
            }*/
            ByteBuf content= Unpooled.copiedBuffer("hello i'm server!!!", CharsetUtil.UTF_8);
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");//发送文本数据
            httpRequest.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes()); //设置文件内容大小
            //将构建好的数据返回
            ctx.writeAndFlush(httpResponse);
        }
    }
}
