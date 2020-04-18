/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.hailong.netty.netty.echo;

import ch.qos.logback.core.util.TimeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Handler implementation for the echo server.
 */

@Slf4j(topic = "c.EchoServerHandler")
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {


    //提交handler的任务到一个线程池中进行异步处理
    private static final EventExecutorGroup group=new DefaultEventExecutorGroup(16);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        log.info("EchoServerHandler channelRead thread name:{}",Thread.currentThread().getName());

        //业务任务添加到taskqueue
        //addTaskQueueMethod1(ctx);

        //将任务提交到线程池
        //addTaskQueueMethod2(ctx);


        //method3 普通方式，但是将context调交到不同的线程池在echoserver里面添加
        Thread.sleep(1000);  //模拟长时间的任务
        log.info("group.submit 的 call 的线程是:{}",Thread.currentThread().getName());
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端1", CharsetUtil.UTF_8));


        log.info("go on...");
    }


    /**
     * 任务提交到线程池
     *   他不会和handler共用一个线程，因此它能异步执行（当有多个任务提交handler实时可由多个线程处理）
     * @param ctx
     */
    private void addTaskQueueMethod2(ChannelHandlerContext ctx) {
        group.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                Thread.sleep(1000);  //模拟长时间的任务
                log.info("group.submit 的 call 的线程是:{}",Thread.currentThread().getName());
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端1", CharsetUtil.UTF_8));
                return null;
            }
        });
    }

    /**
     * 1 业务处理添加到队列中执行
     *    缺点：他和主线程在同一个线程中执行，因此如果有多个任务添加到队列中也必须串行执行
     * @param ctx
     */
    private void addTaskQueueMethod1(ChannelHandlerContext ctx) {
        ctx.pipeline().channel().eventLoop().execute(()->{
            try {
                TimeUnit.SECONDS.sleep(5);  //模拟一个耗时很长的任务
                log.info("EchoServerHandler execute thread name:{}",Thread.currentThread().getName());
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端1", CharsetUtil.UTF_8));//刷写数据到客户端
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
