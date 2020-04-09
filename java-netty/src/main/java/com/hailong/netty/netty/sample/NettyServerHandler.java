package com.hailong.netty.netty.sample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by yuanhailong on 2020/4/5.
 * 说明:
 *  1 自定义一个Handler，需要实现netty提供的相关HandlerAdapter
 */
@Slf4j(topic = "c.NettyServerHandler")
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取实际的数据（可以获取到客户端发送过来的数据）
     *
     * @param ctx 上下文对象，含有通道pipeline和管道channel
     * @param msg  客户端发送过来的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //readClientTest1(ctx, (ByteBuf) msg);
        // 1 用户自定义普通程序 任务提交到taskqueue
        longTaskTest2(ctx);

        //2 用户提交任务到一个定时任务中 ，任务提交到scheduletaskqueue
        ctx.pipeline().channel().eventLoop().schedule(()->{
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端3",CharsetUtil.UTF_8));
        },10,TimeUnit.SECONDS);

        //由于耗时任务都提交到了队列中，因此这个语句会很快得到执行，执行完成后会立即执行channelReadComplete方法
        log.info(" go on ...");
    }

    /**
     *
     * 执行一个耗时很长的任务，通过当前channel反向得到它的NioEventLoop ，然后通过NioEventLoop得到taskQueue，将任务提交大taskQeueu中异步执行
     * @param ctx
     */
    private void longTaskTest2(ChannelHandlerContext ctx) {
        ctx.pipeline().channel().eventLoop().execute(()->{
            try {
                TimeUnit.SECONDS.sleep(10);  //模拟一个耗时很长的任务
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端1", CharsetUtil.UTF_8));//刷写数据到客户端
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //添加第二个任务到taskQueue
        //注：由于放入到taskQueue中还是启动是一个线程执行的，因此这里实际上需要等待第一个任务执行完后在启动（即这里其实需要等待30秒）
        ctx.pipeline().channel().eventLoop().execute(()->{
            try {
                TimeUnit.SECONDS.sleep(20);  //模拟一个耗时很长的任务
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端2",CharsetUtil.UTF_8));//刷写数据到客户端
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * 读取客户端的数据
     * @param ctx
     * @param msg
     */
    private void readClientTest1(ChannelHandlerContext ctx, ByteBuf msg) {
        ByteBuf byteBuf = msg;
        log.info("客户端发送的数据是:{}",byteBuf.toString(CharsetUtil.UTF_8));
        log.info("客户端地址是:{}",ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕后，可以将数据返回
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端",CharsetUtil.UTF_8));
    }

    /**
     * 如果在通信过程中发生异常，需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //ctx.channel().close();
        ctx.close();
    }
}
