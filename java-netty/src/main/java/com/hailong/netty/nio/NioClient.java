package com.hailong.netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Administrator on 2020/3/31.
 */
@Slf4j(topic = "c.NioClient")
public class NioClient {
    public static void main(String[] args) throws Exception{
        //得到一个客户端的网络通道
        SocketChannel socketChannel = SocketChannel.open();

        //设置非阻塞模式
        socketChannel.configureBlocking(false);

        //提供服务器的IP和port
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        //连接服务器
        if(!socketChannel.connect(inetSocketAddress)){
            while(!socketChannel.finishConnect()){
                log.info("由于客户端连接需要时间，客户端不会阻塞，可以干其他事情");
            }
        }

        //连接成功，发送数据[代码能走到这里一定是连接成功了]
        String str="hello hailong , study nio";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        //发送数据（其实就是将byteBuffer的数据写入到Channel中即可）
        socketChannel.write(byteBuffer);

        System.in.read();  //让代码停在这里（仅为测试方便）
    }
}
