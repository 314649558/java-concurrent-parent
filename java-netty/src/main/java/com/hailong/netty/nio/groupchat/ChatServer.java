package com.hailong.netty.nio.groupchat;

import com.hailong.netty.ConfigConst;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Created by Administrator on 2020/4/2.
 * 聊天室模拟小程序服务端
 */
@Slf4j(topic = "c.ChatServer")
public class ChatServer {

    private ServerSocketChannel listenChannel;
    private Selector selector;  //多路复用器

    public ChatServer(){
        try {
            listenChannel =ServerSocketChannel.open();
            selector=Selector.open();
            listenChannel.socket().bind(new InetSocketAddress(ConfigConst.PORT));  //端口绑定且启动服务器
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("服务器初始化完成...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void listen(){
        try {
            //这个循环等待的主线程可以理解为Reactor【反应器，响应器模式】
            while(true) {
                //获取事件（如果2秒钟还没有获取到事件则处理其他事情（即else部分代码））
                int count = selector.select(2000);
                if(count>0){
                    //通过selector的selectedKeys选择已经注册在其上面的channel并且是正在发生事件的channel
                    //通过SelectionKey可以得到channel
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        SelectionKey key = iterator.next();

                        //如果获得了一个客户端连接事件
                        if(key.isAcceptable()){
                            SocketChannel socketChannel = listenChannel.accept();  //通过ServerSocketChannel获得客户端连接
                            socketChannel.configureBlocking(false);  //设置非阻塞模式
                            socketChannel.register(selector,SelectionKey.OP_READ);   //监听这个客户端SocketChannel的OP_READ事件
                            System.out.println(socketChannel.getRemoteAddress() + "  上线");
                        }else if (key.isReadable()){
                            //读取客户端发送的数据 可以理解为handler
                            readData(key);
                        }
                        //移除SelectionKey
                        iterator.remove();
                    }
                }else{
                    //TODO 这里可以处理其他工作
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readData(SelectionKey key){
        //获取到Key相关的Channel
        SocketChannel channel = null;
        try{
            channel=(SocketChannel) key.channel();  //通过key得到channel
            ByteBuffer buffer=ByteBuffer.allocate(1024);
            //将channel【客户端发送的数据】的数据读到buffer中
            int count=channel.read(buffer);

            //表示读取到了客户端数据
            if(count>0){
                String msg=new String(buffer.array());
                System.out.println("from 客户端:" +msg);
                //向其他客户端发送消息（排除自己）
                sendInfoToOtherChannel(msg,channel);
            }
        }catch (IOException e){
            //当某个客户端关闭连接【可认为是下线】的时候会进入到这个异常
            try {
                System.out.println(channel.getRemoteAddress() +" 离线了");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 通过服务器将消息转发给其他客户端
     * @param msg
     * @param self
     * @throws IOException
     */
    private void sendInfoToOtherChannel(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中...");

        //selector.keys()表示所有注册到了selector上的Channel


        //selector.keys()表示得到注册在selector上的所有的channel
        for(SelectionKey selectionKey:selector.keys()){
            Channel targetChannel= selectionKey.channel();
            //targetChannel instanceof SocketChannel  会排掉注册在Selector上的ServerSocketChannel
            //消息不用转发给自己
            if(targetChannel instanceof SocketChannel && targetChannel!=self){
                SocketChannel dest=(SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将Buffer的数据写入通道中
                dest.write(buffer);
            }

        }
    }
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.listen();
    }

}
