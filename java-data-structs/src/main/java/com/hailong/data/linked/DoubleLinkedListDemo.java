package com.hailong.data.linked;

import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/23
 * Description:
 * 双向链表的实现
 */
@Slf4j(topic = "c.DoubleLinkedListDemo")
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        DoubleLinkedList doubleLinkedList=new DoubleLinkedList();

        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");

        doubleLinkedList.add(hero1);
        doubleLinkedList.add(hero2);
        doubleLinkedList.add(hero3);
        doubleLinkedList.add(hero4);

        doubleLinkedList.show();

        log.info("更新节点");
        doubleLinkedList.update(new HeroNode2(3, "公孙胜", "入云龙"));
        doubleLinkedList.show();

        log.info("删除节点");
        //doubleLinkedList.delete(new HeroNode2(4, "公孙胜", "入云龙"));
        doubleLinkedList.delete(4);
        doubleLinkedList.delete(1);
        doubleLinkedList.show();


    }
}


@Slf4j(topic = "c.DoubleLinkedList")
class DoubleLinkedList{
    HeroNode2 head=new HeroNode2();

    /**
     * 添加数据
     * @param node
     */
    public void add(HeroNode2 node){
        //由于头结点不能动，因此我们定义一个辅助的临时节点
        HeroNode2 temp=head;
        for(;;) {
            //如果next为空表示已经找到了尾部节点
            if (!temp.hasNext()) {
                break;
            }
            temp = temp.next;  //向后移动   必须后移，否则会出问题
        }
        //双向指定
        temp.next=node;
        node.prev=temp;
    }

    /**
     * 对节点内容修改，编号不能变
     * @param heroNode
     */
    public void update(HeroNode2 heroNode){
        HeroNode2 temp = this.head;

        boolean flag=false;

        while (true){
            if(temp.no==heroNode.no){
                flag=true;
                break;
            }
            if(!temp.hasNext()){  //到达最后了
                break;
            }
            temp=temp.next;
        }

        if(flag){
            temp.name=heroNode.name;
            temp.nickname=heroNode.nickname;
        }else{
            log.info("没有修改成功:{}",heroNode);
        }
    }

    /**
     * 根据编号删除
     * @param no
     */
    public void delete(int no){
        delete(new HeroNode2(no,"",""));
    }


    /**
     * 删除节点:主要是通过no对比
     * @param heroNode
     */
    public void delete(HeroNode2 heroNode){

        HeroNode2 temp=head;

        boolean flag=false;//是否找到需要删除的节点

        if(!temp.hasNext()){
            log.info("链表为空~~~~");
            return;
        }
        while (true){


            //找到了删除节点
            if(temp.no==heroNode.no){
                flag=true;
                break;
            }
            if(!temp.hasNext()){
                log.info("没有找到需要删除的节点");
                break;
            }

            temp=temp.next;
        }
        if(flag){
            //当前节点的前一个节点的next节点指针改变为指向当前节点的下一个节点
            temp.prev.next=temp.next;

            //如果不加这个判断，最后一个节点由于没有next节点 会有空指针异常
            if(temp.hasNext()) {
                //当前节点下一个节点的前一个节点指向当前节点的前一个节点
                temp.next.prev = temp.prev;
            }
        }else{
            log.info("没有找到需要删除的节点");
        }
    }

    /**
     * 数据打印
     */
    public void show(){

        if(!head.hasNext()){
            log.info("链表为空show");
            return;
        }

        //头节点知识界标识节点，因此需要从头结点的next节点开始打印
        HeroNode2 temp=head.next;
        while (true){
            if(temp==null){  //表示已经到了最后一个节点了
                break;
            }
            log.info("{}",temp);  //输出节点信息
            temp=temp.next;  //后移【必须后移，否则会进入死循环】
        }
    }

}


/**
 * 节点对象
 */
class HeroNode2{

    public int no;
    public String name;
    public String nickname;
    public HeroNode2 prev;
    public HeroNode2 next;
    public HeroNode2(){

    }
    public HeroNode2(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }
    /**
     * 是否还有下一个节点
     * @return
     */
    public boolean hasNext(){
        return this.next!=null;
    }
    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}

