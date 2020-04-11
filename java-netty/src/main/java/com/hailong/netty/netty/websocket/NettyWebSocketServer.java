package com.hailong.netty.netty.websocket;

import com.hailong.netty.ConfigConst;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/11
 * Description:
 *     利用Netty实现WebSocket全双工通信-服务端
 *     浏览器发送消息给服务端
 */
@Slf4j(topic = "c.NettyWebSocketServer")
public class NettyWebSocketServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            //用于webSocket也是基于http的因此我们加上http的编解码器
                            pipeline.addLast(new HttpServerCodec());
                            //以块的方式写
                            pipeline.addLast(new ChunkedWriteHandler());

                            /**
                             * 1. http在数据传输过程中是分段的，HttpObjectAggregator可以将多个段整合
                             * 2. 这就是为什么，当浏览器发生大量数据时，会发出多个http请求的原因
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));

                            /**
                             * 1. 对应websocket ,它的数据是以帧(frame)发送的
                             * 2. 可以看到WebSocketFrame下，有多个子类
                             * 3. 浏览器请求时: ws://hostname:port/xxx
                             * 4. WebSocketServerProtocolHandler的核心功能是将http协议升级为websocket协议并保持长连接
                             * 5. 通过一个http 101的状态码升级为websocket升级
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/chat"));
                            pipeline.addLast(new NettyWebSocketServerHandler());
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
