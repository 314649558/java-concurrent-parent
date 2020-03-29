package com.hailong.curcurrent.juc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2020/3/26.
 */
public class TestWordCount {
    public static void main(String[] args) {

        demo(
                () -> new ConcurrentHashMap<String,LongAdder>(),
                (map,words) ->{
                    for(String word:words) {

                        //对map加同步锁以后可以保证结果正确输出，但是并发性不高
                        //ConcurrentHashMap   保证单个方法是可靠的 但是方法的组合【如下先调用了get后续有调用了put】使用并非是原子的
                        /*synchronized (map){
                            Integer counter = map.get(word);
                            int newValue = counter == null ? 1 : counter + 1;
                            map.put(word, newValue);
                        }*/

                        //方法2
                        //如果key不存在则添加一个key 并计算得到一个value 然后将key,value放入map中
                        LongAdder longAdder=map.computeIfAbsent(word,(key) -> new LongAdder());
                        longAdder.increment();
                    }
                }
        );

    }


    private static void demo2(){
        Map<String,Integer> collect= IntStream.range(1,27).parallel()
                .mapToObj(idx -> readFromFile(idx))
                .flatMap(list -> list.stream())
                .collect(Collectors.groupingBy(Function.identity(),Collectors.summingInt(w -> 1)));
        System.out.println(collect);
    }


    private static <V> void demo(Supplier<Map<String,V>> supplier, BiConsumer<Map<String,V>,List<String>> consumer){
        Map<String, V> counterMap = supplier.get();

        List<Thread> ts=new ArrayList<>();

        for(int i=1;i<=26;i++){
            int idx=i;
            Thread thread=new Thread(()->{
                List<String> words=readFromFile(idx);
                consumer.accept(counterMap,words);
            });
            ts.add(thread);
        }

        ts.forEach(t -> t.start());
        ts.forEach(t ->{
            try{
                t.join();
            }catch (Exception e){

            }
        });
        System.out.println(counterMap);

    }


    public static List<String> readFromFile(int i){
        ArrayList<String> words=new ArrayList<>();
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("D:/tmp/"+i+".txt")));
            while(true){
                String word=in.readLine();
                if(word==null){
                    break;
                }
                words.add(word);
            }
            return words;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
