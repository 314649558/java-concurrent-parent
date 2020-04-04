package com.hailong.netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Created by Administrator on 2020/3/31.
 *
 * Scattering 将数据写入到多个buffer中，可以采用Buffer数组，根据Buffer大小一次写入【分散】
 *
 * Gathering 从Buffer中读取数据，可采用Buffer数组依次读取。
 */
@Slf4j(topic = "c.ScatteringAndGatheringTest")
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception {

        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress=new InetSocketAddress(6666);

        //绑定端口到socket 并启动服务器
        serverSocketChannel.socket().bind(inetSocketAddress);
        log.info("服务器已经启动 服务监听端口为:{}",6666);

        //创建ByteBuffer数组，稍后将数据写入到数组中
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0]=ByteBuffer.allocate(5);  //为了测试方便，ByteBuffer容量都给小一点
        byteBuffers[1]=ByteBuffer.allocate(3);
        int messageLength=8;
        //SocketChannel  等待客户端连接（可用telnet测试）
        SocketChannel socketChannel = serverSocketChannel.accept();


        while (true){
            long byteRead = 0;

            while (byteRead<messageLength){
                long l = socketChannel.read(byteBuffers);
                byteRead +=1;  //累计读取字节
                log.info(" byteRead={}",byteRead);

                Arrays.asList(byteBuffers).stream()
                        .map(buffer-> "position="+buffer.position()+", limit="+buffer.limit())
                        .forEach((message) -> log.info(message));
            }

            //将所有的Buffer进行反转 为了以后的操作方便
            //Arrays.asList(byteBuffers).stream().map(buffer->buffer.flip());
            Arrays.asList(byteBuffers).forEach(buffer->buffer.flip());

            //将数据显示到客户端
            long byteWrite=0;
            while(byteWrite<messageLength){
                //将byteBuffers的数据写入到socketChannel【实际上这里就是回写道客户端】
                socketChannel.write(byteBuffers);
                byteWrite +=1;
            }
            //将所有的Buffer 进行复位
            Arrays.asList(byteBuffers).forEach(buffer->buffer.clear());


            log.info("byteRead={},   byteWrite={}, messageLength={}",byteRead,byteWrite,messageLength);

        }

    }
}
