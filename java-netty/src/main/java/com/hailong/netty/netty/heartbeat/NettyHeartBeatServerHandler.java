package com.hailong.netty.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/10
 * Description:
 *     心跳监测事件发生后，在这里做业务处理
 */
@Slf4j(topic = "c.NettyHeartBeatServerHandler")
public class NettyHeartBeatServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * IdleStateHandler 触发读写事件后会向下传递到这里，触发本方法执行
     * @param ctx
     * @param evt    事件类型
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);

        //如果是IdleStateEvent
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;

            String evtTyp="";

            //READER_IDLE   读空闲事件
            //WRITER_IDLE   写空闲事件
            //ALL_IDLE      读写空闲事件
            IdleState state=event.state();
            switch (state){
                case READER_IDLE :
                    evtTyp="读空闲";
                    break;
                case WRITER_IDLE:
                    evtTyp="写空闲";
                    break;
                case ALL_IDLE:
                    evtTyp="读写空闲";
                    break;

            }
            log.info(evtTyp);


            //如果关闭当前通道，这不会循环触发【实验代码】
            ctx.channel().close();

        }





    }
}
