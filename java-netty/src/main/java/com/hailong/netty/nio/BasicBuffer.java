package com.hailong.netty.nio;

import java.nio.IntBuffer;

/**
 * Created by Administrator on 2020/3/30.
 */
public class BasicBuffer {

    public static void main(String[] args) {

        //创建一个容量为5的IntBuffer
        IntBuffer intBuffer=IntBuffer.allocate(5);

        //存入数据到buffer
        for(int i=0; i< intBuffer.capacity();i++){
            intBuffer.put((i+1)*2);
        }


        //从Buffer读取数据
        intBuffer.flip();   //将limit设置为position  将position设置为0


        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }




    }

}
