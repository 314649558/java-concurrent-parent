package com.hailong.netty.netty.groupchat;

import com.hailong.netty.ConfigConst;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * Created by yuanhailong on 2020/4/9.
 *  * 聊天程序客户端--> Netty版本
 */
@Slf4j(topic = "c.GroupChatNettyClient")
public class GroupChatNettyClient {
    private String host;
    private int port;
    public GroupChatNettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run(){
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new GroupChatNettyClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            log.info("--------------{}------------------",channelFuture.channel().localAddress());
            //channelFuture.channel().closeFuture().sync();
            //控制台输入消息
            Scanner scanner=new Scanner(System.in);

            while (scanner.hasNextLine()){
                String msg = scanner.nextLine();
                channelFuture.channel().writeAndFlush(msg);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        new GroupChatNettyClient(ConfigConst.ADDR,ConfigConst.PORT).run();
    }


}
