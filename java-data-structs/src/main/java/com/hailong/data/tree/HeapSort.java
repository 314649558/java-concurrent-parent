package com.hailong.data.tree;

import java.util.Arrays;
/**
 * Created by yuanhailong on 2020/7/6.
 * 二叉树实际案例-堆排序
 */
public class HeapSort {
    public static void main(String[] args) {
        //要求将数组进行升序排列（既需要构建大顶堆）
        int[] arr={4,6,8,5,9};
        heapSort(arr);
    }
    //编写一个堆排序的方法
    public static void heapSort(int[] arr){
        int temp=0;
        System.out.println("堆排序!!");
        //分布完成
        /*adjustHeap(arr,1,arr.length);
        System.out.println("第一次："+ Arrays.toString(arr));  //4，9,8,5,6

        adjustHeap(arr,0,arr.length);
        System.out.println("第二次："+ Arrays.toString(arr));  //9,6,8,5,4*/

        //最终代码
        for(int i=arr.length/2-1;i>=0;i--){
            adjustHeap(arr,i,arr.length);
        }
        for(int j=arr.length-1;j>0;j--){
            temp=arr[j];
            arr[j]=arr[0];
            arr[0]=temp;
            adjustHeap(arr,0,j);
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 将一个数组（对应二叉树），调整成一个大顶堆
     * @param arr 待调整的数组
     * @param i 表示非叶子节点在数组中的索引
     * @param length 表示对多少个元素进行调整，length是在逐渐减少
     */
    public static void  adjustHeap(int arr[],int i,int length){
        int temp=arr[i];

        for(int k =i*2+1;k<length;k=k*2+1){
            if(k+1< length && arr[k]<arr[k+1]){  //说明左子节点的值小于右子节点的值
                k++; //k 指向右子节点
            }
            //如果子节点大于大于父节点
            if(arr[k]>temp){
                arr[i]=arr[k];  //把较大的值赋给当前节点
                i=k;//i指向k，继续循环比较
            }else{
                break;
            }
        }
        //当for循环结束后，已经将以i为父节点的最大值，放在了最顶上（局部的）
        arr[i]=temp;//将temp放在调整后的位置
    }

}
