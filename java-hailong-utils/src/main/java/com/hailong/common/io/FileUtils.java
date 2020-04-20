package com.hailong.common.io;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/19
 * Description:
 *  文件管理工具类
 */
@Slf4j(topic = "c.FileUtils")
public class FileUtils {


    public static String arrToText(int arr[][]){

        StringBuffer text=new StringBuffer();

        for(int i=0;i<arr.length;i++){
            StringBuffer line=new StringBuffer();
            for(int k=0;k<arr[i].length;k++){
                line.append(arr[i][k]);
                if(k<arr[i].length-1){
                    line.append("\t");
                }

            }
            text.append(line);
            if(i<arr.length-1){
                text.append("\n");
            }
        }
        return text.toString();


    }

    public static void writeToFile(String path,String text){
        BufferedWriter out=null;
        try {
            out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path))));
            out.write(text);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out!=null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> readFromFile(String path){
        ArrayList<String> words=new ArrayList<>();
        BufferedReader in=null;
        try{
            in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            while(true){
                String word=in.readLine();
                if(word==null){
                    break;
                }
                words.add(word);
            }
            return words;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
