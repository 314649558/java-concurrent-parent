package com.hailong.netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2020/3/30.
 */
@Slf4j(topic = "c.NioFileChannel02")
public class NioFileChannel02 {
    public static void main(String[] args) throws Exception {


        File file=new File("D://file01.txt");

        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer byteBuffer=ByteBuffer.allocate((int)file.length());

        log.info("read begin :  position {}     limit {}",byteBuffer.position(),byteBuffer.limit());
        //读取数据，将channel的数据写入到buffer
        channel.read(byteBuffer);
        log.info("read after :  position {}     limit {}",byteBuffer.position(),byteBuffer.limit());

        log.info("-----------------------读取方式一 直接获得源码中的hb数据---------------");
        String str=new String(byteBuffer.array());

        log.info(str);




        log.info("-----------------------读取方式二 一个字节的获取---------------");
        //必须调用Flip  否则读取不到任何数据  因为此时position=limit
        byteBuffer.flip();
        log.info("flip after :  position {}     limit {}",byteBuffer.position(),byteBuffer.limit());
        byte[] bytes=new byte[byteBuffer.limit()];
        while (byteBuffer.hasRemaining()){
            int pos=byteBuffer.position();
            bytes[pos]=byteBuffer.get();  //每get一次Position就会加1
        }

        log.info(new String(bytes));


        fileInputStream.close();



    }
}
