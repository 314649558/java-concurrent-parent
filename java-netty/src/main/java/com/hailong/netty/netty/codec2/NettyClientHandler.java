package com.hailong.netty.netty.codec2;

import com.hailong.netty.netty.codec.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * Created by Administrator on 2020/4/5.
 */
@Slf4j(topic = "c.NettyClientHandler")
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道准备就绪以后出发，即连接成功后出发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //随机发送
        int randam = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage=null;
        //如果randam==0 发送student对象否则发送Worker对象
        if(randam==0){
            myMessage= MyDataInfo.MyMessage.newBuilder().setDataTyp(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(1).setName("刘瑶").build()).build();
        }else{
            myMessage= MyDataInfo.MyMessage.newBuilder().setDataTyp(MyDataInfo.MyMessage.DataType.WorkerType)
                .setWorker(MyDataInfo.Worker.newBuilder().setName("袁海龙").setAge(32).build()).build();
        }
        //数据发送出去
        ctx.writeAndFlush(myMessage);
    }
    /**
     * 接收服务端返回的消息
      * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        log.info("服务器返回给客户端消息:{}",buf.toString(CharsetUtil.UTF_8));
        log.info("服务器地址是:{}",ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
