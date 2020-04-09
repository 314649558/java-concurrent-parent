package com.hailong.netty.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2020/4/9.
 */
@Slf4j(topic = "c.NettyByteBuf")
public class NettyByteBuf {
    public static void main(String[] args) {


        /**
         * 1 在netty的ByteBuf中不需要使用flip进行读写转换
         * 2 在netty的ByteBuf底层维护了readerindex 和writerindex
         * 3 通过readerindex,writerindex,capacity将底层的buffer分成三个区域
         *   3.1 0 - readerindex 表示已经读取的数据
         *   3.2 readerindex - writerindex 表示可读取的数据
         *   3.3 writerindex - capacity 表示可写数据
         */
        ByteBuf buffer = Unpooled.buffer(10);
        log.info("buffer capacity : {}",buffer.capacity());

        for(int i=0;i<buffer.capacity();i++){
            buffer.writeByte(i);
        }

        //数据读取
        //buffer.getByte(index);//这个方法不会导致readIndex下标的移动

        for(int i=0;i<buffer.capacity();i++){
            log.info("{}",buffer.readByte());  //会导致下表的移动
        }


        for(int i=0;i<buffer.capacity();i++){
            log.info("{}",buffer.getByte(i));  //如果这个时候采用readByte()方法读取则会抛出索引越界的异常
        }

    }
}
