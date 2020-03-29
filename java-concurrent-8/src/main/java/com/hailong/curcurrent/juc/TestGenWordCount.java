package com.hailong.curcurrent.juc;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2020/3/26.
 * 生成文件
 */
public class TestGenWordCount {

    private static final String ALPHA="abcdefghljklmnopqrstuvwxyz";

    public static void main(String[] args) {
        int length=ALPHA.length();
        int count=200;
        List<String> list=new ArrayList<>(length*count);

        for(int i=0;i<length;i++){
            char ch=ALPHA.charAt(i);
            for(int j=0;j<count;j++){
                list.add(String.valueOf(ch));
            }
        }

        Collections.shuffle(list);

        for(int i=0;i<26;i++){

            try {
                String filename="D:/tmp/"+(i+1)+".txt";
                PrintWriter out=new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(filename))));
                String collect=list.subList(i * count , (i+1) * count).stream().collect(Collectors.joining("\n"));
                out.print(collect);
                out.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
