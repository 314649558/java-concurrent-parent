package com.hailong.data.linked;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/24
 * Description:
 *    自定义单向环形链表【约瑟夫环问题】
 */
public class CircleLinkedListDemo {
    public static void main(String[] args) {
        CircleLinkedList circleLinkedList=new CircleLinkedList();
        circleLinkedList.init(5);
        circleLinkedList.show();
        circleLinkedList.countBoy(1,2,5);
    }
}


@Slf4j(topic = "c.CircleLinkedList")
class CircleLinkedList{

    //先预先定义环形链表的第一个节点
    private BoyNode first=null;
    /**
     * 初始化一个环
     * @param nums   初始化环的大小
     */
    public void init(int nums){
        //至少的创建一个节点
        if(nums<1){
            return;
        }
        BoyNode curBoy=null;  //辅助指针，帮助构建环形链表
        for(int i=1;i<=nums;i++){
            BoyNode boy = new BoyNode(i);
            //如果是的一个
            if(i==1) {
                first=boy;
                first.setNext(first);
                curBoy=first;
            }else{
                curBoy.setNext(boy);  //指向新的boy
                boy.setNext(first);  //boy指向first节点  构成环形链表
                curBoy=boy;  //curBoy后移
            }
        }
    }


    /**
     * 根据用户的输入，计算小孩的出圈顺序
     * @param startNo  从第几个小孩开始
     * @param countNum   表示数几下
     * @param nums   环形的大小
     */
    public void countBoy(int startNo,int countNum,int nums){
        if(first==null || startNo<1 || startNo>nums){
            log.info("参数输入有误，重新输入");
            return;
        }

        BoyNode helper=first;

        //让helper指向first 的前一个节点
        while(true){
            if(helper.getNext()==first){
                break;
            }
            helper=helper.getNext();
        }

        //报数前移动k-1次【从哪个地方开始报数】
        for(int i=0;i<startNo-1;i++){
            first=first.getNext();
            helper=helper.getNext();
        }

        //循环报数[约瑟夫问题]
        while (true){
            if(helper == first){
                break;
            }
            //first和helper指针同时移动countNum-1
            for(int i=0;i<countNum-1;i++){
                first=first.getNext();
                helper=helper.getNext();   //在first的前一个节点
            }
            //这时first指向的节点就是要出环的节点
            log.info("出圈boy[{}]",first.getId());
            first=first.getNext();
            helper.setNext(first);
        }

        //最后留在圈中的小孩编号
        log.info("最后留在圈中的小孩编号:{}",helper.getId());

    }


    public void show(){
        if(first==null){
            log.info("没有数据");
            return;
        }

        //由于first不能动，因此需要一个辅助指针完成遍历
        //如果动了就没办法判断何时出圈了
        BoyNode curBoy=first;

        while (true){
            log.info("小孩节点的编号：{}",curBoy.getId());
            if(curBoy.getNext()==first){  //表示已经循环完毕
                break;
            }
            curBoy=curBoy.getNext();  //后移
        }

    }
}

/**
 * 节点
 */
@Data
class BoyNode{
    private int id;
    private BoyNode next;

    /**
     * 是否有下一个节点
     * @return true 有  false 没有
     */
    public boolean hasNext(){
        return next!=null;
    }

    public BoyNode(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BoyNode{" +
                "id=" + id +
                '}';
    }
}
