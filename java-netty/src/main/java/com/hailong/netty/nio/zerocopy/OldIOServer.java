package com.hailong.netty.nio.zerocopy;

import com.hailong.netty.ConfigConst;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2020/4/2.
 */
@Slf4j(topic = "c.OldIOServer")
public class OldIOServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket=new ServerSocket(ConfigConst.PORT);
        while (true){

            log.info("等待客户端连接");
            Socket socket=serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            byte[] bytes=new byte[4096];

            int total=0;
            while(true){
                int readCount=dataInputStream.read(bytes,0,bytes.length);
                if(-1==readCount){
                    break;
                }
                total+=readCount;
            }

            log.info("服务器接收到字节数:{}",total);

        }
    }
}
