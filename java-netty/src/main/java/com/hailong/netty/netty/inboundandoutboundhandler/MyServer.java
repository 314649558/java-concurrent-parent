package com.hailong.netty.netty.inboundandoutboundhandler;

import com.hailong.netty.ConfigConst;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/12
 * Description:
 *    自定义解码器接收客户端Long类型的数据
 *
 *    注：
 *    1 解码是入栈，编码是出栈
 *    2 如果一个是服务器发送数据给客户端，那么服务器就是出栈【出栈用编码器】，反之就是入栈【入栈用解码器】
 *    3 服务器或客户端不会如果只有读或者写只会调用编码器和解码器中的一个
 *    4 服务器或客户端会根据不同的事件（读，写事件）自动选择编码器或解码器
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

                            pipeline.addLast(new MyLongToByteEncoder());//编码器

                            //pipeline.addLast(new MyByteToLongDecoder());//自定义解码器
                            pipeline.addLast(new MyByteToLongDecoder2());//自定义解码器
                            //经过MyServerByteToLongDecoder解码后 消息会变成一个Long
                            //因此，这里可以直接接收Long类型的数据
                            pipeline.addLast(new MyServerMsgHandler());
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
