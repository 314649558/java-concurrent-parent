package com.hailong.netty.nio.zerocopy;

import com.hailong.netty.ConfigConst;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by Administrator on 2020/4/2.
 */
public class NewIOServer {
    public static void main(String[] args) throws Exception {
        InetSocketAddress address=new InetSocketAddress(ConfigConst.PORT);

        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        ServerSocket socket = serverSocketChannel.socket();
        socket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();

            int readCount=0;
            while (-1!=readCount){
                try{
                    readCount = socketChannel.read(byteBuffer);

                }catch (Exception e){
                    //e.printStackTrace();
                    break;
                }
                byteBuffer.rewind();

            }

        }
    }
}
