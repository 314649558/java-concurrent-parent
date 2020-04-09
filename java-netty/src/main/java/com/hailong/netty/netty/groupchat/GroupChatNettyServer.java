package com.hailong.netty.netty.groupchat;

import com.hailong.netty.ConfigConst;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by yuanhailong on 2020/4/9.
 * 聊天程序服务端--> Netty版本
 */
@Slf4j(topic = "c.GroupChatNettyServer")
public class GroupChatNettyServer {

    private int port;

    public GroupChatNettyServer(int port) {
        this.port = port;
    }

    public void run(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ChannelPipeline pipeline = ch.pipeline();
                            //加入字符串编解码器的handler
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            //加入我们自定义的handler
                            pipeline.addLast(new GroupChatNettyServerHandler());
                        }
                    });

            //端口绑定
            ChannelFuture channelFuture = b.bind(port).sync();
            log.info("server is ready ...");
            //监听channel关闭事件
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new GroupChatNettyServer(ConfigConst.PORT).run();
    }

}
