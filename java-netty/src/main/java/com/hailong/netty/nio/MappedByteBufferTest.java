package com.hailong.netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2020/3/31.
 * MappedByteBuffer  申请的是堆外内存，因此他可以直接在堆外进行修改，避免了数据从堆内存拷贝到操作系统层面的开支
 */
@Slf4j(topic = "c.MappedByteBufferTest")
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception{
        RandomAccessFile randomAccessFile=new RandomAccessFile("D:\\file01.txt","rw");
        FileChannel channel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0,(byte)'F');
        mappedByteBuffer.put(3,(byte)'C');

        randomAccessFile.close();
        log.info("修改成功...");
    }

}
