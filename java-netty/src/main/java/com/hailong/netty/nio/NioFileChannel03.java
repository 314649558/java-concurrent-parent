package com.hailong.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2020/3/30.
 *
 * 从一个文件读 然后将数据写入到另一个文件 要求用同一个Buffer
 */
public class NioFileChannel03 {

    public static void main(String[] args) throws Exception{

        FileInputStream inputStream=new FileInputStream("D:\\file01.txt");
        FileOutputStream outputStream=new FileOutputStream("D:\\file02.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        //test1(inputChannel, outputChannel);
        test2(inputChannel, outputChannel);


        //关闭
        inputStream.close();
        outputStream.close();
        inputChannel.close();
        outputChannel.close();
    }

    /**
     * 实现方法1   将channel的数据首先写入到bytebuffer,然后将bytebuffer的数据写入到另外一个channel
     * @param inputChannel
     * @param outputChannel
     * @throws IOException
     */
    private static void test1(FileChannel inputChannel, FileChannel outputChannel) throws IOException {
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        while(true){
            byteBuffer.clear();
            //将Channel中的数据读到byteBuffer中
            int read = inputChannel.read(byteBuffer);
            if(read==-1){
                break;
            }

            byteBuffer.flip();
            outputChannel.write(byteBuffer);
        }
    }

    /**
     * 直接将channel的数据写到另一个channel
     * @param srcChannel
     * @param destChannel
     * @throws IOException
     */
    private static void test2(FileChannel srcChannel, FileChannel destChannel) throws IOException {

        //destChannel.transferFrom(srcChannel,0,srcChannel.size());

        srcChannel.transferTo(0,srcChannel.size(),destChannel);

    }
}
