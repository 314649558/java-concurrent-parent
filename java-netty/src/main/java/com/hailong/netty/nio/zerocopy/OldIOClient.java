package com.hailong.netty.nio.zerocopy;

import com.hailong.netty.ConfigConst;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2020/4/2.
 */
@Slf4j(topic = "c.OldIOClient")
public class OldIOClient {
    public static void main(String[] args) throws Exception{
        Socket socket=new Socket(ConfigConst.ADDR,ConfigConst.PORT);
        //String fileName="D:\\msdia80.dll";
        String fileName="D:\\个人\\安乡县\\安乡县农副产品物流中心项目0126-Model.pdf";
        FileInputStream inputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] bytes=new byte[4096];

        long readCount;
        long total=0L;

        long startTime=System.currentTimeMillis();
        while((readCount=inputStream.read(bytes))>=0){
            total += readCount;
            dataOutputStream.write(bytes);
        }

        log.info("发送总字节数:{}   耗时:{}",total,(System.currentTimeMillis()-startTime));

        dataOutputStream.close();
        socket.close();
        inputStream.close();

    }
}
