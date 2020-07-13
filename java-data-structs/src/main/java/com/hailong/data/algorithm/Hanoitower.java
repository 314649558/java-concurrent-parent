package com.hailong.data.algorithm;

/**
 * Created by Administrator on 2020/7/13.
 * 分治算法
 */
public class Hanoitower {
    public static void main(String[] args) {
        hanoiTower(30,'A','B','C');
    }



    public static void hanoiTower(int num,char a,char b,char c){
        if(num==1){
            System.out.println("第1个盘从 "+a+"->"+c);
        }else{

            hanoiTower(num-1,a,c,b);
            System.out.println("第"+num+"个盘从 "+a+"->"+c);
            hanoiTower(num-1,b,a,c);
        }
    }
}
