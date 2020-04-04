package com.hailong.netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2020/3/31.
 */
@Slf4j(topic = "c.NioServer")
public class NioServer {
    public static void main(String[] args) throws Exception{
        //创建SereverSocketChannel
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        //得到一个Selector(多路复用器)
        Selector selector=Selector.open();

        //绑定到一端口6666 服务器监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);

        //把serverSocketChannel 注册到Selector上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//关注客户端连接事件

        //循环等待客户端连接
        while (true){
            //==0表示一秒后还没有获取到连接
            if(selector.select(1000)==0){
                log.info("服务器等待了1秒钟,无法获得客户端连接");
                continue;
            }
            //1 如果返回>0 表示得到了关注的事件
            //获取SelectionKey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                //得到SelectionKey
                SelectionKey selectionKey = keyIterator.next();

                //根据key做的不同事件类型做相应的处理
                //如果是OP_ACCEPT表示有新的客户端连接事件
                if(selectionKey.isAcceptable()){
                    //注意这其实是SocketChannel的Accept方法,它是阻塞的
                    //但是由于我们知道了这是一个客户端连接，因此他会很快的执行这段代码
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    log.info("客户端连接成功，生成了一个socketChannel：{} ",socketChannel.hashCode());

                    //设置为非阻塞的SockeChannel
                    socketChannel.configureBlocking(false);

                    //将socketChannel同样也要祖册到Selector上
                    //并将这个socketChannel和Buffer连接起来
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }else if (selectionKey.isReadable()){  //发生了OP_READ事件
                    //通过key反向获取对应的Channel
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    //获得Channel上的关联的Buffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    //将Channel上的数据写入到关联的Buffer上面
                    socketChannel.read(byteBuffer);
                    //读写反转
                    byteBuffer.flip();
                    log.info("服务端接收到数据  {}",new String(byteBuffer.array()));
                }
            }
            //手动移除SelectionKey
            keyIterator.remove();
        }


    }
}
