package com.hailong.netty.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2020/4/9.
 */
@Slf4j(topic = "c.GroupChatNettyServerHandler")
public class GroupChatNettyServerHandler extends SimpleChannelInboundHandler<String> {


    /**
     * 1 定义一个channel 管理所有的channel[每个客户端连接都会有不同的channel]
     * 2 GlobalEventExecutor.INSTANCE 是一个全局的时间执行器，它是一个单例
     */
    private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    /**
     * 当channelhandler被加入的时候就会被立即调用
     *    如果有一个客户端连接服务端，那么Netty就会创建Handler 然后调用此方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //提示其他客户端有客户上线了【只需要在channel里面写入消息即可】
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress() +" 加入群聊\n");
        //将channel 加入到ChannelGroup
        channelGroup.add(channel);
    }


    /**
     * 客户端断开连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress() +"推出群聊\n");
    }


    /**
     * 客户端被激活【客户端连接建立完成】
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("{} 上线了...\n" ,ctx.channel().remoteAddress());
    }

    /**
     * channel 处于不活跃状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("{} 下线了...\n" ,ctx.channel().remoteAddress());
    }

    /**
     * 发生异常的情况下，关闭channel
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 读取客户端数据，并将数据转发给其他客户端
     * @param ctx
     * @param msg    客户端发送过来的消息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.forEach(ch->{
            if(ch!=channel){
                //发送消息给其他客户端
                ch.writeAndFlush("[客户端]"+ch.remoteAddress()+" 发送了消息 [" + msg +"] \n");
            }else{
                //消息回显给自己
                //TODO 一般情况下不需要回显
                ch.writeAndFlush("[自己]"+ch.remoteAddress()+" [" + msg +"] \n");
            }
        });

    }
}
