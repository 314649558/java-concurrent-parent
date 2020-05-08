package com.hailong.data.sort;

import java.util.Arrays;

/**
 * Created by Administrator on 2020/5/8.
 * 冒泡排序:
 *    1） 一共进行数组大小-1趟排序
 *    2） 每一趟排序的次数逐渐减少
 *    3） 如果我们发现在某一趟排序中，没有发生一次交换，可以提前结束（优化）
 */
public class BubbleSort {

    public static void main(String[] args) {
        //int[] arr={3,9,-1,10,-2};
        int[] arr={3,9,-1,10,20};
        int temp=0;
        bubbleSort(arr);
        //bubleSortProcess(arr);

    }

    /**
     * 冒泡排序算法
     *   其实就是将演变过程用一个循环包起来
     * @param arr
     */
    private static void bubbleSort(int[] arr) {
        int temp;//冒泡排序算法时间复杂度O(n^2)

        boolean flag=false;  //标识符，用来表示是否进行过交换，可用这个标识符进行优化

        for(int i=0;i<=arr.length-1;i++) {
            for (int j = 0; j < arr.length - 1-i; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag=true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            System.out.println("第["+(i+1)+"]趟排序");
            System.out.println(Arrays.toString(arr));


            //表示在某趟中一次交换都没有发生过，则可以提前优化
            if(!flag){
                break;
            }else{
                flag=false;//重置flag=flase
            }

        }
    }

    /**
     * 冒泡排序演变过程
     */
    private static void bubleSortProcess(int[] arr){
        int temp=0;
        //第一趟排序，将最大的数据放在最后一位
        for (int j = 0; j < arr.length - 1-0; j++) {
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        System.out.println("第1趟排序");
        System.out.println(Arrays.toString(arr));


        //第二趟排序，将第二大的数排在倒数第二位
        for (int j = 0; j < arr.length - 1-1; j++) {
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        System.out.println("第2趟排序");
        System.out.println(Arrays.toString(arr));

        //第三趟排序，将第三大的数排在倒数第三位
        for (int j = 0; j < arr.length - 1-1; j++) {
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        System.out.println("第3趟排序");
        System.out.println(Arrays.toString(arr));

        //第四趟排序，将第四大的数排在倒数第四位
        for (int j = 0; j < arr.length - 1-1; j++) {
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        System.out.println("第4趟排序");
        System.out.println(Arrays.toString(arr));
    }

}
