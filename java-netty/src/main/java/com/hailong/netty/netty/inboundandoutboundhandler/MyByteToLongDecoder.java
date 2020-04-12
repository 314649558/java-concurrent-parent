package com.hailong.netty.netty.inboundandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/12
 * Description:
 *    将byte转换为long
 */
@Slf4j(topic = "c.MyByteToLongDecoder")
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     *
     * @param ctx
     * @param in   入栈的消息【从客户端发过来的字节】
     * @param out  将数据写入到本list后，它自己会自动将消息传递给下一个handler进行处理
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        log.info("MyByteToLongDecoder decode 被调用");
        //long占用8个字节
        //必须大于8个字节的时候才读取，否则解码会出错
        if(in.readableBytes()>=8){
            out.add(in.readLong());  //readLong会将readerIndex向后移动8个字节
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
