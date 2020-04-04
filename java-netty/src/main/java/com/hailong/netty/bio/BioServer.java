package com.hailong.netty.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2020/3/29.
 * BIO模式：一个客户端就会采用一个线程进行连接，这会导致大量的资源浪费【因为一个线程不可能时时刻刻都在工作】
 */
@Slf4j(topic = "c.BioServer")
public class BioServer {

    private final static Integer PORT=6666;

    public static void main(String[] args) throws Exception{
        //线程池
        ExecutorService threadPool= Executors.newCachedThreadPool();

        ServerSocket serverSocket=new ServerSocket(6666);
        log.info("服务器启动了");
        //用一个死循环去监听客户端的链接
        while (true){
            log.info("主线程   线程名称 {} ",Thread.currentThread().getName());
            //监听客户端连接 ，这里会阻塞
            Socket socket = serverSocket.accept();
            log.info("连接到一个客户端咯");

            threadPool.execute(()->{
                handler(socket);
            });
        }
    }

    private static void handler(Socket socket) {
        byte[] bytes = new byte[1024];

        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            while (true){
                log.info("线程名称 {} ",Thread.currentThread().getName());
                //监听客户端发送的数据，如果没有发送数据会阻塞在这里
                int read=inputStream.read(bytes);
                log.info("{} 读取到数据咯",Thread.currentThread().getName());
                if(read>=0){
                    log.info("{}",new String(bytes,0,read));
                }else{
                    break;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(inputStream!=null){
                    inputStream.close();
                }
                socket.close();
                log.info("{} 关闭连接",Thread.currentThread().getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
