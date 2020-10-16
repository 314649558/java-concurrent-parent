package com.hailong.data.algorithm;

/**
 * Created by Administrator on 2020/7/13.
 * 二分查找算法-使用非递归方式
 */
public class BinarySearchNoRecur {
    public static void main(String[] args) {
        int arr[]={1,3,8,10,11,67,100};

        int index = binarySearch(arr, 100);

        System.out.println(index);

    }


    /**
     * 二分查找非递归方式实现
     * @param arr 需要查找的数，升序排列的
     * @param target  需要查找的数据
     * @return
     */
    public static int binarySearch(int arr[],int target){

        int left=0;
        int right=arr.length - 1;

        while(left<=right){
            int mid=(left+right)/2;

            if(arr[mid]==target){
                return mid;
            }else if(target<arr[mid]){
                right=mid-1;//向左查找
            }else{
                left=mid+1;
            }

        }

        return  -1;
    }

}



