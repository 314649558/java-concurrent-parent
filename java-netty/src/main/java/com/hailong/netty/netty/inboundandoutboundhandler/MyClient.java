package com.hailong.netty.netty.inboundandoutboundhandler;

import com.hailong.netty.ConfigConst;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/12
 * Description:
 *     自定义编码器客户端
 */
@Slf4j(topic = "c.MyClient")
public class MyClient {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast(new MyByteToLongDecoder());

                            //发送数据是出栈，Handler会从尾部开始调用
                            pipeline.addLast(new MyLongToByteEncoder());
                            //业务处理handler
                            pipeline.addLast(new MyClientMsgHandler());
                        }
                    });
            log.info("client is ready ...");

            ChannelFuture channelFuture = bootstrap.connect(ConfigConst.ADDR, ConfigConst.PORT).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}
