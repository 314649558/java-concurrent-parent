package com.hailong.netty.netty.codec2;

import com.hailong.netty.netty.codec.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by yuanhailong on 2020/4/5.
 * 说明:
 *  1 自定义一个Handler，需要实现netty提供的相关HandlerAdapter
 */
@Slf4j(topic = "c.NettyServerHandler")
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        //根据不同的DataTyp显示不同的信息

        MyDataInfo.MyMessage.DataType dataTyp = msg.getDataTyp();

        if(dataTyp == MyDataInfo.MyMessage.DataType.StudentType){
            MyDataInfo.Student student=msg.getStudent();
            log.info("学生 id={},name={}",student.getId(),student.getName());

        }else if(dataTyp == MyDataInfo.MyMessage.DataType.WorkerType) {
            MyDataInfo.Worker worker=msg.getWorker();
            log.info("工人  name={},age={}",worker.getName(),worker.getAge());
        }else{
            log.info("数据对象类型不正确");
        }

    }
}
