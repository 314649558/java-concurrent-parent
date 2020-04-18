package com.hailong.netty.netty.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/18
 * Description:
 * 本类中方法调用顺序
 * 1 setPara
 * 2 channelActive[只会被调用一次]
 * 3 call【第一次调用后调用wait方法等待channelRead唤醒】
 * 4 channelRead 数据处理完成后调用notify方法唤醒call
 * 5 call  被唤醒后走后续流程
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {


    //
    //1


    private ChannelHandlerContext context;
    private String result;
    private String para;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context=ctx;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //注意这里 凡是用到notify或者wait方法的地方都必须加锁，否则会报错
        //只有持有了锁之后才能进行notify或wait
        synchronized (this) {
            result = msg.toString();
            notify();//唤醒call中的wait
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 该方法被消费方调用
     * @return
     * @throws Exception
     */
    @Override
    public Object call() throws Exception {
        synchronized (this) {
            context.writeAndFlush(para);  //消息发送给服务端
            wait();//等待服务端返回结果
            return result;  //服务方返回的结果
        }
    }
    void setPara(String para){
        this.para=para;
    }
}
