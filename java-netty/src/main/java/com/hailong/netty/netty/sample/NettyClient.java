package com.hailong.netty.netty.sample;

import com.hailong.netty.ConfigConst;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2020/4/5.
 */
@Slf4j(topic = "c.NettyClient")
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //客户端只需要一个事件循环组即可
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端启动对象
            Bootstrap bootstrap = new Bootstrap();

            //设置客户端参数，使用链式编程
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            log.info("client is ready ...");

            //启动客户端去链接服务器
            //关于ChannelFuture 涉及到netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect(ConfigConst.ADDR, ConfigConst.PORT).sync();
            //给关闭通道添加监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }


    }
}
