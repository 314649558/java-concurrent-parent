package com.hailong.data.recursion;

/**
 * Created by yuanhailong on 2020/5/7.
 */
public class Queue8 {
    int max=8;
    int[] array=new int[max];
    static int count=0;

    public static void main(String[] args) {
        Queue8 queue8 = new Queue8();
        queue8.check(0);
        System.out.println("一共有"+count+"解法");
    }


    /**
     * 检查第n个皇后是否可以放入，回溯算法
     * @param n
     */
    public void check(int n){
        if(n==max){   //表示开始放置第九个皇后，表示8个皇后都放好了
            print();
            return;
        }

        for(int i=0;i<max;i++){
            array[n]=i;
            if(judge(n)){
                check(n+1);  //如果不冲突就递归放第n+1个皇后
            }

            //如果冲突，会继续循环将第n个皇后放在第n行的i+1列上
        }

    }


    /**
     * 判断是否冲突
     * 1 不能再一条同一列和斜线上
     * @param n 表示第n个皇后
     * @return true 表示不冲突
     */
    public boolean judge(int n){

        for(int i=0;i<n;i++){
            //array[i]==array[n] 在同一列上
            // Math.abs(n-i)==Math.abs(array[n]-array[i]) 在对角线上
            if(array[i]==array[n] ||
                    Math.abs(n-i)==Math.abs(array[n]-array[i])){
                return false;
            }
        }

        return true;
    }


    /**
     * 打印
     */
    private void print(){
        count++;
        for(int i=0;i<max;i++){
            System.out.print(array[i]+" ");
        }
        System.out.println();

    }




}
