package com.hailong.curcurrent.n6;

import com.hailong.curcurrent.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Administrator on 2020/3/29.
 */
@Slf4j(topic = "c.TestAtmoicIntegerArray")
public class TestAtmoicIntegerArray {

    public static void main(String[] args) {
        //由于普通数组是非线程安全的因此结果会出现错误
        demo(
                () -> new int[10],
                (array)->array.length,
                (array,index) -> array[index]++,
                (array) -> {
                    System.out.println(Arrays.toString(array));
                }
        );

        //结果正确输出 保证了操作的原子性
        demo(
                ()-> new AtomicIntegerArray(10),
                (array) -> array.length(),
                (array,index) -> array.getAndIncrement(index),
                (array) -> System.out.println(array)
        );
    }


    /**
     *
     * @param supplier   内容提供者 无参函数   ()-> 返回结果
     * @param funLength  有一个参数，并且有一个返回类型的函数  (arg) -> 返回结果  两个参数的可以用BIFunction
     * @param biConsumer 有两个参数的消费者，并且无返回结果，对提供的数据进行处理    (arg1,arg2)-> Void
     * @param printConsumer 一个参数的消费者 并且无返回结果，对提供的数据进行处理    (arg1)-> Void
     * @param <T>
     */
    private static <T> void demo(Supplier<T> supplier,
                                 Function<T,Integer> funLength,
                                 BiConsumer<T,Integer> biConsumer,
                                 Consumer<T> printConsumer
                                 ){
        List<Thread> ts=new ArrayList<>();
        T array=supplier.get();
        int length=funLength.apply(array);
        log.info("length {} ",length);

        for(int i=0;i<length;i++){
            ts.add(new Thread(()->{
                for (int j=0;j<10000;j++){
                    biConsumer.accept(array,j % length);
                }
            }));
        }
        ts.forEach(t -> t.start());

        Sleeper.sleep(1, TimeUnit.SECONDS);

        printConsumer.accept(array);
    }

}
