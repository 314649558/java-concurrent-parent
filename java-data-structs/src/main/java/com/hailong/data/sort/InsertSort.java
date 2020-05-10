package com.hailong.data.sort;

import java.util.Arrays;

/**
 * Created by Administrator on 2020/5/10.
 * 插入排序主要思想
 *    选择其中的一个数据作为其中一个有序的数组，另外的数据作为无序的数组，将这个有序的数和无序的数中的所有数据进行对比
 */
public class InsertSort {
    public static void main(String[] args) {

        int[] arr={101,34,119,1,-1,43,10,-3,187};

        //insertSortProcess(arr);
        insertSort(arr);
    }

    private static void insertSort(int[] arr){


        for(int i=1;i<arr.length;i++) {

            //第一轮排序
            int insertVal = arr[i];
            int insertIndex = i - 1;

            /**
             * 给insertVal找插入位置
             * 说明：
             * 1. insertIndex >0 保证在给insertVal找插入位置时不会越界
             * 2. insertVal < arr[insertIndex] 待插入的数据，还没有找到插入的位置
             */
            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }

            //当退出循环时，说明插入位置找到
            arr[insertIndex + 1] = insertVal;
            System.out.println("第"+i+"轮插入");
            System.out.println(Arrays.toString(arr));
        }
    }



    /**
     * 插入排序推演过程
     * @param arr
     */
    private static void insertSortProcess(int[] arr){

        //第一轮排序
        int insertVal=arr[1];
        int insertIndex=1-1;

        /**
         * 给insertVal找插入位置
         * 说明：
         * 1. insertIndex >0 保证在给insertVal找插入位置时不会越界
         * 2. insertVal < arr[insertIndex] 待插入的数据，还没有找到插入的位置
         */
        while(insertIndex>=0 && insertVal<arr[insertIndex]){
            arr[insertIndex+1]=arr[insertIndex];
            insertIndex--;
        }

        //当退出循环时，说明插入位置找到
        arr[insertIndex+1]=insertVal;
        System.out.println("第1轮插入");
        System.out.println(Arrays.toString(arr));


        //第二轮排序
        insertVal=arr[2];
        insertIndex=2-1;

        while(insertIndex>=0 && insertVal<arr[insertIndex]){
            arr[insertIndex+1]=arr[insertIndex];
            insertIndex--;
        }

        //当退出循环时，说明插入位置找到
        arr[insertIndex+1]=insertVal;
        System.out.println("第2轮插入");
        System.out.println(Arrays.toString(arr));


        //第三轮排序
        insertVal=arr[3];
        insertIndex=3-1;

        while(insertIndex>=0 && insertVal<arr[insertIndex]){
            arr[insertIndex+1]=arr[insertIndex];
            insertIndex--;
        }

        //当退出循环时，说明插入位置找到
        arr[insertIndex+1]=insertVal;
        System.out.println("第3轮插入");
        System.out.println(Arrays.toString(arr));


    }
}
