package com.hailong.data.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/20
 * Description:
 *    用数组模拟队列先进先出
 *    该案例有以下几个问题
 *    1 数据取出后并没有真正的将其移除
 *    2 一旦数据添加满了后由于没有真正的移除，因此即使调用了get方法后也无法再次添加数据
 *    3 可以利用下面的自定义环形队列来解决这个问题
 */
public class ArrayQueueDemo {
    public static void main(String[] args) {
        ArrayQueue queue=new ArrayQueue(3);


        char key=' ';
        boolean loop=true;

        do {
            System.out.println("s(show) 显示数据");
            System.out.println("a(add) 添加数据");
            System.out.println("g(get) 获取数据");
            System.out.println("h(head) 头部数据");
            System.out.println("e(exit) 退出程序");

            Scanner scanner=new Scanner(System.in);
            key=scanner.next().charAt(0); //接收一个字符

            switch (key){
                case 's':
                    queue.show();
                    break;
                case 'a':
                    System.out.println("请输入需要添加的数据:");
                    int n=scanner.nextInt();
                    queue.addQueue(n);
                    break;
                case 'g':
                    try{
                        int res = queue.getQueue();
                        System.out.printf("获得的数据是%d\n",res);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try{
                        System.out.printf("获得的数据是%d\n",queue.headShow());
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop=false;
                    System.out.println("退出程序~!!!!!!");
                    break;
                default:
                    break;
            }




        }while (loop);

    }

}


class ArrayQueue{

    //队列最大容量
    private int maxSize;
    //队列头
    private int front;
    //队列尾
    private int rear;

    private int[] arr;


    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        front=-1;  //队列头，指向队列头的前一个位置
        rear=-1;
        arr=new int[this.maxSize];
    }

    /**
     * 判断队列是否满了
     * @return
     */
    public boolean isFull(){
        return rear == maxSize -1;
    }

    /**
     * 判断队列是否为空
     * @return
     */
    public boolean isEmpty(){
        return rear == front;
    }

    /**
     * 添加数据到队列中
     * @param n
     */
    public void addQueue(int n){
        if(isFull()){
            System.out.println("队列已满，无法添加数据");
            return;
        }
        rear++;  //向后移1位
        arr[rear]=n;
    }

    /**
     * 从队列中取数据
     * @return
     */
    public int getQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列为空，无法获取数据");
        }
        front++;
        return arr[front];
    }


    /**
     * 显示队列内容
     */
    public void show(){
        for(int i=0;i<arr.length;i++){
            System.out.printf("arr[%d]=%d\n",i,arr[i]);
        }
    }

    /**
     * 显示头部数据
     */
    public int headShow(){
        try{
            return arr[front+1];
        }catch (Exception e){
            throw new RuntimeException("队列为空，不能获取数据");
        }

    }


}
