package com.hailong.data.sort;

import java.util.Arrays;

/**
 * Created by Administrator on 2020/5/9.
 * 选择排序
 *   排序思想：将第一个数假定为最小数，然后一次和后面的数据对比，如果发现最小数则将两个数据的位置对换
 *             第二次假定第二个数据为最小数，然后后后面的数据对比，如果发现最小数则将两个数据的位置对换
 *             依次类推
 *
 *             总共需要进行数组大小-1轮排序
 *
 */
public class SelectSort {
    public static void main(String[] args) {

        //int[] arr={39,-1,129,101};
        int[] arr={39,-1,129,101,2,5,-10,20,100};
        System.out.println("排序前");
        System.out.println(Arrays.toString(arr));

        //selectSortProcess(arr);
        selectSort(arr);

        //System.out.println("排序后");
    }


    /**
     * 选择排序
     * @param arr
     */
    public static void selectSort(int[] arr){

        for(int i=0;i<arr.length-1;i++) {

            int minIndex = i;
            int min = arr[i];//先假设第一个是最小值[实际上可能不是]
            //查找最小值
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) {  //从小到达排序，若要从大到小排序，将>换成<即可
                    minIndex = j;
                    min = arr[j];
                }
            }

            //如果本身就是最小的就不用进行交换【优化】
            if(i!=minIndex) {
                //数据交换
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
            System.out.println("第"+(i+1)+"轮排序后");
            System.out.println(Arrays.toString(arr));
        }
    }


    /**
     * 选择排序推演过程
     * @param arr{39,-1,129,101};
     */
    public static void selectSortProcess(int[] arr){

        //第一轮排序
        int minIndex=0;
        int min=arr[0];//先假设第一个是最小值[实际上可能不是]

        //查找最小值
        for(int j=0+1;j<arr.length;j++){
            if(min>arr[j]){
                minIndex=j;
                min=arr[j];
            }
        }

        //数据交换
        arr[minIndex]=arr[0];
        arr[0]=min;
        System.out.println("第一轮排序后");
        System.out.println(Arrays.toString(arr));



        //第二轮排序
        minIndex=1;
        min=arr[1];//先假设第一个是最小值[实际上可能不是]

        //查找最小值
        for(int j=1+1;j<arr.length;j++){
            if(min>arr[j]){
                minIndex=j;
                min=arr[j];
            }
        }

        //数据交换
        arr[minIndex]=arr[1];
        arr[1]=min;
        System.out.println("第二轮排序后");
        System.out.println(Arrays.toString(arr));



        //第三轮排序
        minIndex=2;
        min=arr[2];//先假设第一个是最小值[实际上可能不是]

        //查找最小值
        for(int j=2+1;j<arr.length;j++){
            if(min>arr[j]){
                minIndex=j;
                min=arr[j];
            }
        }

        //数据交换
        arr[minIndex]=arr[2];
        arr[2]=min;
        System.out.println("第三轮排序后");
        System.out.println(Arrays.toString(arr));

    }

}
