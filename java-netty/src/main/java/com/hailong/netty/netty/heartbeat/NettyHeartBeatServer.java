package com.hailong.netty.netty.heartbeat;

import com.hailong.netty.ConfigConst;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/10
 * Description:
 *     用Netty模拟一个心跳服务端心跳监测程序
 */
@Slf4j(topic = "c.NettyHeartBeatServer")
public class NettyHeartBeatServer {

    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            /**
                             * IdleStateHandler 说明
                             * 1. readerIdleTimeSeconds 读空闲时间  当在指定时间内没有监测到读事件的时候就会触发时间，并传递个下一个handler
                             * 2. writerIdleTimeSeconds 写空闲时间  当在指定时间内没有监测到写事件的时候就会触发时间，并传递个下一个handler
                             * 3. allIdleTimeSeconds 读写空闲时间   当在指定时间内没有监测到读写事件的时候就会触发时间，并传递个下一个handler
                             */
                            pipeline.addLast(new IdleStateHandler(3,5,7));

                            //pipeline中上一个Handler处理完后会传递到下一个Handdler去触发时间
                            pipeline.addLast(new NettyHeartBeatServerHandler());

                        }
                    });

            ChannelFuture channelFuture = bootstrap.bind(ConfigConst.PORT).sync();
            log.info("server is ready...");
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
