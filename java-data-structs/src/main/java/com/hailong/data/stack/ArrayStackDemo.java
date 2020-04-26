package com.hailong.data.stack;

import java.util.Scanner;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/26
 * Description:
 *   用Array自定义栈
 */
public class ArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack stack=new ArrayStack(3);

        char key=' ';

        boolean loop=true;

        while (loop){
            System.out.println("s(show) 显示数据");
            System.out.println("p(push) 添加数据");
            System.out.println("g(get)  获取数据");
            System.out.println("e(exit) 退出程序");
            Scanner scanner=new Scanner(System.in);
            key=scanner.next().charAt(0); //接收一个字符
            switch (key){
                case 's':
                    stack.list();
                    break;
                case 'a':
                    System.out.println("请输入需要添加的数据:");
                    int n=scanner.nextInt();
                    stack.push(n);
                    break;
                case 'g':
                    try{
                        int res = stack.pop();
                        System.out.printf("获得的数据是%d\n",res);
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
        }


    }
}

/**
 * 数组模拟栈的实现
 */
class ArrayStack{

    private int capacity;  //栈的容量
    private int top=-1;   //表示栈顶的位置  -1表示栈的数据为空  Push一个数+1  pop一个数据-1
    private int[] data;  //用一个数组来存放数据


    public ArrayStack(int capacity) {
        this.capacity = capacity;
        data=new int[this.capacity];
    }

    /**
     * 栈是否已经满了
     * @return
     */
    public boolean isFull(){
        return this.capacity-1==top;
    }

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty(){
        return top==-1;
    }

    /**
     * 添加数据-入栈
     * @param num
     */
    public void push(int num){
        if(isFull()){
            System.out.println("栈已经达到了最大容量["+this.capacity+"],无法入栈");
            return;
        }
        top++;  //指针向栈顶移动
        data[top]=num;
    }

    /**
     * 出栈
     * @return
     */
    public int pop(){
        if(isEmpty()){
            throw new RuntimeException("栈中没有数据，无法出栈");
        }
        int val=this.data[top];
        top--;  //指针向栈底移动
        return val;
    }


    /**
     * 循环打印栈，从栈顶开始打印
     */
    public void list(){
        if(isEmpty()){
            System.out.println("栈中没有数据~~~~");
            return;
        }

        //从栈顶开始
        for(int i=top;i>=0;i--){
            System.out.printf("stack[%d]=%d\n",i,data[i]);
        }
    }




}
