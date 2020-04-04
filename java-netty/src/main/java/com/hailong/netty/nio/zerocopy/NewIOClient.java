package com.hailong.netty.nio.zerocopy;

import com.hailong.netty.ConfigConst;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by Administrator on 2020/4/2.
 */
public class NewIOClient {

    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel=SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(ConfigConst.ADDR,ConfigConst.PORT));
        String fileName="D:\\个人\\安乡县\\安乡县农副产品物流中心项目0126-Model.pdf";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime=System.currentTimeMillis();

        //在Linux下 一个transforTo就可可以完成传输
        //在Window下 一次调用transforTo只能发送8m,就需要分段发送，而且需要注意发送时的传输位置
        //transfor底层就使用到了零拷贝
        long transCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的总字节数:"+transCount+"   耗时:"+(System.currentTimeMillis()-startTime));
        fileChannel.close();

    }

}
