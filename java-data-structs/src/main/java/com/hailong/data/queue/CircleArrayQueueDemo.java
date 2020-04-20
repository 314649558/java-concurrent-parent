package com.hailong.data.queue;

import java.util.Scanner;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/20
 * Description:
 *  自定义环形队列
 *  思路如下:
 *      1 front变量做如下调整：front就指向队列的第一个元素，front初始值为0
 *      2 rear变量做如下调整: rear指向队列的最后一个元素的前一个位置。因为希望空出一个位置作为约定
 *      3 队列满是条件为 (rear + 1) % maxSize == front
 *      4 队列为空的条件为 rear == front
 *      5 队列中有有效的数据为 (rear + maxSize - front) % maxSize
 *      6 通过以上分析可得出一个环形队列
 */
public class CircleArrayQueueDemo {

    public static void main(String[] args) {
        //注意  由于这里需要空出一个位置作为我们的约定，因此实际上我们只能存储3个数据
        CircleArrayQueue queue=new CircleArrayQueue(4);
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
                    queue.showQueue();
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

/**
 * 定义一个环形队列
 */
class CircleArrayQueue{



    //队列最大容量
    private int maxSize;
    //队列头 初始值0
    private int front;
    //队列尾 初始值0
    private int rear;

    private int[] arr;

    public CircleArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        arr=new int[this.maxSize];
    }


    /**
     * 判断队列是否满了
     * @return
     */
    public boolean isFull(){
        return (rear + 1) % maxSize == front;
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
        //因为从0开始，因此先添加数据
        arr[rear]=n;
        rear=(rear + 1) % maxSize;
    }
    /**
     * 从队列中取数据
     * @return
     */
    public int getQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列为空，无法获取数据");
        }
        int value=arr[front];
        front = (front + 1) % maxSize;
        return value;
    }


    /**
     * 打印队列中有效数据
     */
    public void showQueue(){
        for(int i=front;i<front + size();i++){
            System.out.printf("arr[%d]=%d\n",i % maxSize,arr[i % maxSize]);
        }
    }

    /**
     * 显示头部数据
     */
    public int headShow(){

        if(isEmpty()){
            throw new RuntimeException("队列为空，不能获取数据");
        }
        return arr[front];

    }


    /**
     * 取出队列中有效元素个数
     * @return
     */
    public int size(){
        return (rear + maxSize - front) % maxSize;
    }



}
