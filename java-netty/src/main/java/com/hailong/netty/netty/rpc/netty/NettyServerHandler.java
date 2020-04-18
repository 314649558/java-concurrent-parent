package com.hailong.netty.netty.rpc.netty;

import com.hailong.netty.netty.rpc.RPCConstant;
import com.hailong.netty.netty.rpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/18
 * Description:
 */
@Slf4j(topic = "c.NettyServerHandler")
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg1) throws Exception {
        String msg=msg1.toString();

        log.info("服务端读取到客户端消息:{}",msg);

        if(msg.startsWith(RPCConstant.RPC_PROTOCOL_HEAD)){
            //获取消息的内容交给RPC服务端处理
            String s = msg.replace(RPCConstant.RPC_PROTOCOL_HEAD, "");

            //得到的result是需要服务端返回给客户端的消息
            String result = new HelloServiceImpl().hello(s);

            //消息会写到消费方
            ctx.writeAndFlush(result);

        }else{
            log.info("消息不符合协议:{}",msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
