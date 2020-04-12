package com.hailong.netty.netty.codec;

import com.hailong.netty.ConfigConst;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2020/4/5.
 */
@Slf4j(topic = "c.NettyServer")
public class NettyServer {

    public static void main(String[] args) {

        /**
         * 创建BossGroup 和 WorkerGroup
         * 说明：
         *   1 创建两个线程组BossGroup 和 WorkerGroup
         *   2 BossGroup只处理连接(accept)请求,WorkerGroup处理客户端实际的业务请求（例如，读写请求）
         *   3 BossGroup 和 WorkerGroup底层都是一个无限循环
         *   4 默认情况下开启的线程数为CPU内核*2
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);  //BossGroup一般不需要那么多线程，因为他只处理连接请求
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            //创建服务器启动对象，并配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //使用链式编程的方式配置参数
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class) //设置服务器通道类型
                    .option(ChannelOption.SO_BACKLOG, 128)  //设置队列等待的线程数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {  //创建一个通道初始化对象
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {


                            socketChannel.pipeline().addLast(new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));

                            //给pipeline设置处理器
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            //绑定端口并同步监听
            //这里会去启动服务器
            ChannelFuture channelFuture = bootstrap.bind(ConfigConst.PORT).sync();

            //添加关心的事件
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(channelFuture.isSuccess()){
                        log.info("监听端口 {} 成功",ConfigConst.PORT);
                    }else{
                        log.info("监听端口 {} 失败",ConfigConst.PORT);
                    }
                }
            });

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
