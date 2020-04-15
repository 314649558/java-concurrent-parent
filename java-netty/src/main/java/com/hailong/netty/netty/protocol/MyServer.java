package com.hailong.netty.netty.protocol;

import com.hailong.netty.ConfigConst;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/12
 * Description:
 *    TCP 粘包和拆包问题分析案例
 */

@Slf4j(topic = "c.MyServer")
public class MyServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new MyMessageDecoder());
                            pipeline.addLast(new MyMessageEncoder());
                            pipeline.addLast(new MyServerHandler());

                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(ConfigConst.PORT).sync();
            log.info("server is ready ...");
            //对关闭通道进行监听【注，这里是监听关闭通道而不是去关闭通道】
            channelFuture.channel().closeFuture().sync();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭线程组
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
