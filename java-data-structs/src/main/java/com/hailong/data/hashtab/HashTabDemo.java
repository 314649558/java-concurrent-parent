package com.hailong.data.hashtab;
import java.util.Random;
import java.util.Scanner;
/**
 * Created by Administrator on 2020/6/7.
 */
public class HashTabDemo {
    public static void main(String[] args) {
        HashTab hashTab=new HashTab(7);


        initData(hashTab);  //初始化数据

        Scanner scanner=new Scanner(System.in);
        while (true){
            System.out.println("a|add 添加数据");
            System.out.println("l|list 查看手数据");
            System.out.println("f|find 查找数据 ");
            System.out.println("d|del 删除数据 ");
            System.out.println("e|exit 退出程序");

            char key=scanner.next().toCharArray()[0];

            switch (key){
                case 'a':
                    System.out.println("输入id");
                    int id=scanner.nextInt();
                    System.out.println("输入姓名");
                    String name=scanner.next();
                    hashTab.add(new Emp(id,name));
                    break;
                case 'l':
                    hashTab.list();
                    break;
                case 'f':
                    System.out.println("输入查找id");
                    id=scanner.nextInt();
                    hashTab.findEmpById(id);
                    break;
                case 'd':
                    System.out.println("输入需要删除的id");
                    id=scanner.nextInt();
                    hashTab.delete(id);
                    break;
                case 'e':
                    System.out.println("退出程序");
                    scanner.close();
                    System.exit(0);//这里是退出了JVM整个进程
                    break;
                default:
                    System.out.println("没有相应指令，请重新输入");
                    break;
            }


        }


    }


    private static void initData(HashTab hashTab){

        Random random=new Random();

        for (int i=1;i<=20;i++){
            int id=random.nextInt(10000);
            hashTab.add(new Emp(id,"hailong"+id));
        }
    }

}

class HashTab{
    private EmpLinkedList[] empLinkedListArray;
    private int size;
    public HashTab(int size) {
        empLinkedListArray=new EmpLinkedList[size];
        this.size = size;

        //初始化数组中的链表
        for(int i=0;i<size;i++){
            empLinkedListArray[i]=new EmpLinkedList();
        }
    }


    public void add(Emp emp){
        //取模，决定放在数组中的那个链表中
        int empLinkedListArrayIndex=hash(emp.id);
        empLinkedListArray[empLinkedListArrayIndex].add(emp);
    }

    public void list(){
        for(int i=0;i<size;i++){
            empLinkedListArray[i].list(i);
        }
    }

    public void findEmpById(int id){
        int empLinkedListArrayIndex=hash(id);
        Emp emp = empLinkedListArray[empLinkedListArrayIndex].findEmpById(id);

        if(emp==null){
            System.out.printf("ID=%d的员工不存在",id);
        }else{
            System.out.printf("在第[%d]的链表中找到了id=%d的员工",empLinkedListArrayIndex+1,id);
        }
        System.out.println();

    }


    public void delete(int id){
        int empLinkedListArrayIndex=hash(id);
        empLinkedListArray[empLinkedListArrayIndex].delete(id);
    }

    /**
     * 计算ID的hash值，有很多种方法去计算hash值，我们这里采用最简单的取模的方式
     * @param id
     * @return
     */
    private int hash(int id){
        return id%size;
    }


}



class EmpLinkedList{
    Emp head;
    private boolean isEmpty(){
        return head==null;
    }
    public void add(Emp emp){
        //链表为空时
        if(isEmpty()){
            head=emp;
            return;
        }
        Emp curEmp=head;
        //加到链表的最后
        while(true){
            //如果为空表示到了链表的最后
            if(curEmp.next==null){
                curEmp.next=emp;
                break;
            }
            //向后移动
            curEmp=curEmp.next;
        }
    }

    public void list(int no){
        if(isEmpty()){
            System.out.println("第["+(no+1)+"]号链表内容为空");
            return;
        }

        System.out.print("当前链表内容为");
        Emp curEmp=head;
        while (true){
            System.out.printf("=> id=%d name=%s \t ",curEmp.id,curEmp.name);
            if(curEmp.next==null){
                break;
            }
            curEmp=curEmp.next;
        }
        //换行
        System.out.println();
    }

    public Emp findEmpById(int id){
        if(isEmpty()){
            System.out.println("当前链表为空");
            return null;
        }

        Emp curEmp=head;
        while (true){
            if(id==curEmp.id){
                 break;
            }

            if(curEmp.next==null){
                curEmp=null;
                break;
            }
            curEmp=curEmp.next;
        }
        return curEmp;
    }





    public void delete(int id){

        if(isEmpty()){
            System.out.printf("id=%d的员工不存在",id);
            System.out.println();
            return;
        }

        //如果第一个节点就是需要删除的节点
        if(head.id==id){
            if(head.next!=null){
                head=head.next;
            }
            System.out.println("id="+id+"的员工被移除");
            return;
        }

        Emp curEmp=head;

        while (true){
            //如果下一个节点为空
            if(curEmp==null){
                System.out.println("id="+id+"的员工不存在");
                break;
            }
            //注意这里一定要使用当前节点的下一个节点，否则无法找到后没办法将链表链接起来【因为这里自定义的是单向链表结构】
            //如果当前节点的下一个节点的ID相同证明找到了
            if(curEmp.next.id==id){
                System.out.println("id="+id+"的员工被移除");
                curEmp.next=curEmp.next.next;
                break;
            }
            curEmp=curEmp.next;
        }




    }


}


class Emp{
    public int id;
    public String name;
    public  Emp next;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
