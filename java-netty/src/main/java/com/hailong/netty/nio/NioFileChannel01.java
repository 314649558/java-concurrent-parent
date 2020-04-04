package com.hailong.netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2020/3/30.
 */
@Slf4j(topic = "c.NioFileChannel01")
public class NioFileChannel01 {

    public static void main(String[] args) throws IOException {
        String str="hello,hailong";
        FileOutputStream fileOutputStream = new FileOutputStream("D://file01.txt");
        //获取FileChannel
        FileChannel channel = fileOutputStream.getChannel();
        //将数据写入到Buffer中
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        log.info("flip begin :  position {}     limit {}",byteBuffer.position(),byteBuffer.limit());
        //写读转换    充值position,limit属性 便于后续将数据写入到channel中
        byteBuffer.flip();
        log.info("flip after :  position {}     limit {}",byteBuffer.position(),byteBuffer.limit());
        //将byteBuffer数据写入到Channel
        channel.write(byteBuffer);

        log.info("write after :  position {}     limit {}",byteBuffer.position(),byteBuffer.limit());
        //关闭IO
        fileOutputStream.close();
        log.info("write data finish ...");
    }

}
