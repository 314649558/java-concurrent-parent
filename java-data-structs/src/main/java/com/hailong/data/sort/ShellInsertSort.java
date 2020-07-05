package com.hailong.data.sort;

import java.util.Arrays;

/**
 * Created by Administrator on 2020/5/10.
 * 希尔排序算法->插入算法的一种优化
 */
public class ShellInsertSort {
    public static void main(String[] args) {
        int[] arr={8,9,1,7,2,3,5,4,6,0,-10};
        //shellSortProcess(arr);
        //shellSort(arr);
        shellSort2(arr);
    }

    /**
     * 希尔排序-[交换法]
     * @param arr
     */
    public static void shellSort(int[] arr){
        int temp=0;
        int count=0;
        for(int gap=arr.length/2;gap>0;gap /=2){
            for(int i=gap;i<arr.length;i++){

                for(int j=i-gap;j>=0;j-=gap){
                    //说明需要交互
                    if(arr[j]>arr[j+gap]){
                        temp=arr[j];
                        arr[j]=arr[j+gap];
                        arr[j+gap]=temp;
                    }
                }

            }
            System.out.println("希尔排序第"+(++count)+"轮");
            System.out.println(Arrays.toString(arr));
        }
    }



    public static void shellSortProcess(int[] arr){
        int temp=0;

        //希尔排序第1轮
        //因为第1轮排序，将10个数据分成5组
        for(int i=5;i<arr.length;i++){

            for(int j=i-5;j>=0;j-=5){
                //说明需要交互
                if(arr[j]>arr[j+5]){
                    temp=arr[j];
                    arr[j]=arr[j+5];
                    arr[j+5]=temp;
                }
            }

        }
        System.out.println("希尔排序第1轮");
        System.out.println(Arrays.toString(arr));


        //希尔排序第2轮
        //因为第1轮排序，将10个数据分成5/2=2组
        for(int i=5;i<arr.length;i++){

            for(int j=i-2;j>=0;j-=2){
                //说明需要交互
                if(arr[j]>arr[j+2]){
                    temp=arr[j];
                    arr[j]=arr[j+2];
                    arr[j+2]=temp;
                }
            }

        }
        System.out.println("希尔排序第2轮");
        System.out.println(Arrays.toString(arr));


        //希尔排序第3轮
        //因为第1轮排序，将10个数据分成5/2/2=1组
        for(int i=5;i<arr.length;i++){

            for(int j=i-1;j>=0;j-=1){
                //说明需要交互
                if(arr[j]>arr[j+1]){
                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }

        }
        System.out.println("希尔排序第1轮");
        System.out.println(Arrays.toString(arr));


    }


    /**
     * 对交换式的方法进行改进->位移法
     * @param arr
     */
    public static void shellSort2(int[] arr){

        for(int gap=arr.length/2;gap>0;gap /=2){
            //从第gap个元素开始，逐个对其所在的组进行直接插入排序
            for (int i=gap;i<arr.length;i++){
                int j=i;
                int temp=arr[j];  //待插入的数据
                if(arr[j]<arr[j-gap]){
                    while(j-gap>=0 && temp<arr[j-gap]){
                        //移动
                        arr[j]=arr[j-gap];
                        j-=gap;
                    }
                    //当退出循环后，就给temp找到了插入的位置
                    arr[j]=temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }


}
