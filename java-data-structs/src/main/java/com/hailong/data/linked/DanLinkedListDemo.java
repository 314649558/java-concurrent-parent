package com.hailong.data.linked;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.util.Stack;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/22
 * Description:
 *  自定义实现单向链表
 */
@Slf4j(topic = "c.DanLinkedListDemo")
public class DanLinkedListDemo {

    public static void main(String[] args) {

        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        DanLinkedList danLinkedList=new DanLinkedList();
       /* danLinkedList.add(hero1);
        danLinkedList.add(hero2);
        danLinkedList.add(hero3);
        danLinkedList.add(hero4);*/


       //故意打乱编号添加，输出后看是否会按照编号排序输出
        danLinkedList.addOrder(hero1);
        danLinkedList.addOrder(hero2);
        danLinkedList.addOrder(hero4);
        danLinkedList.addOrder(hero3);
        //danLinkedList.addOrder(hero2);   //因为已经被添加过，因此这个节点应该添加失败

        /*danLinkedList.show();
        danLinkedList.update(new HeroNode(5,"小路","玉麒麟~~~~~"));

        log.info("修改后的链表数据...");
        */

        //删除
        //danLinkedList.delete(hero1);
        danLinkedList.show();
        log.info("当前节点数:{}",danLinkedList.size());


        log.info("根据索引反向获取:{}",danLinkedList.getNodeByLastIndex(1));
        log.info("根据索引正向获取:{}",danLinkedList.getNodeByIndex(2));


        danLinkedList.reversalNode();

        log.info("反转后");
        danLinkedList.show();

        log.info("反向打印");
        reversalPrint(danLinkedList);

    }


    /**
     * 实现单向列表的反向打印[不要改变原链表的结构]
     * 思路：
     *    把数据循环压入栈中，利用栈的先进后出原理实现反向打印
     * @param danLinkedList
     */
    public static void reversalPrint(DanLinkedList danLinkedList){

        HeroNode head = danLinkedList.getHead();
        //链表为空，无需做处理
        if(!head.hasNext()){
            return;
        }

        Stack<HeroNode> stack=new Stack<>();

        HeroNode cur = head.next;

        //数据压入stack中
        while(true){
            stack.push(cur);
            if(!cur.hasNext()){
                break;
            }
            cur=cur.next;
        }

        //从栈中取出实现反向打印
        while(!stack.empty()){
            log.info("{}",stack.pop());
        }


    }


}


/**
 * 单向链表对象
 */
@Data
@Slf4j(topic = "c.DanLinkedList")
class DanLinkedList{

    //初始化一个头结点，不存放具体数据，仅用来标识链表的头部
    private HeroNode head=new HeroNode();

    /**
     * 向单向链表中添加数据的方法
     *  不考虑编号顺序
     *
     *  1 找到节点的最后一个节点
     *  2 将节点的最后一个节点的next指向需要添加的node节点
     * @param node
     */
    public void add(HeroNode node){
        //由于头结点不能动，因此我们定义一个辅助的临时节点
        HeroNode temp=head;

        for(;;) {
            //如果next为空表示已经找到了尾部节点
            if (!temp.hasNext()) {
                break;
            }
            temp = temp.next;  //向后移动   必须后移，否则会出问题
        }
        //如果跳出循环则表示已经招到了最后一个节点
        //将其最后一个节点的next指向需要添加的节点
        temp.next=node;
    }
    /**
     * 按照编号顺序添加
     * @param heroNode
     */
    public void addOrder(HeroNode heroNode){

        HeroNode temp = head;
        boolean flag=false;
        while (true){
            if(!temp.hasNext()){//链表的尾部
                break;
            }

            //找到需要添加的位置，就在temp的后面加入节点
            if(temp.next.no>heroNode.no){
                break;
            }

            //节点已经被添加过了
            if(temp.next.no==heroNode.no){
                flag=true;
                break;
            }
            temp=temp.next; //向后移动
        }
        if(flag){
            log.info("节点[{}]已经被添加咯",heroNode);
        }else {
            //添加节点，将新的节点添加到temp后面，并将新节点的next节点指向原来temp.next节点指向它
            heroNode.next=temp.next;
            temp.next=heroNode;
        }
    }
    /**
     * 对节点内容修改，编号不能变
     * @param heroNode
     */
    public void update(HeroNode heroNode){
        HeroNode temp = this.head;

        boolean flag=false;

        while (true){
            if(!temp.hasNext()){
                break;
            }

            if(temp.no==heroNode.no){
                flag=true;
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
     * 删除节点:主要是通过no对比【找到要删除节点的前一个节点】
     * @param heroNode
     */
    public void delete(HeroNode heroNode){
        HeroNode temp=head;

        boolean flag=false;//是否找到需要删除的节点

        if(!temp.hasNext()){
            log.info("链表为空~~~~");
            return;
        }
        while (true){
            if(!temp.hasNext()){
                log.info("没有找到需要删除的节点");
                break;
            }

            //找到了删除节点的前一个节点
            if(temp.next.no==heroNode.no){
                flag=true;
                break;
            }

            temp=temp.next;
        }
        if(flag){
            //temp.next 表示当前节点，即需要删除的节点
            //当前节点如果么有被任何对象引用由于会被GC回收，所以我们不需要自己删除这个对象
            HeroNode prev= temp;  //前一个节点
            HeroNode next=temp.next.next;
            prev.next=next;
            //temp.next=temp.next.next;
        }else{
            log.info("没有找到需要删除的节点");
        }
    }
    /**
     * 获取链表的有效节点数【即排除head节点】
     * @return
     */
    public int size(){
        HeroNode temp=head;

        if(!temp.hasNext()){
            return 0;
        }
        HeroNode cur=temp.next;
        int size=0;
        for(;;){
            size++;
            if(!cur.hasNext()){
                break;
            }
            cur=cur.next;
        }
        return size;

    }
    /**
     * 正向获取数据
     * @param index  从0开始
     * @return
     */
    public HeroNode getNodeByIndex(int index){
        if(!head.hasNext()){
            return null;
        }
        int size=size();  //得到链表节点数
        if(index>=size || index<0){
            throw new IndexOutOfBoundsException("链表大小为:"+size+" Index:"+ index);
        }
        HeroNode cur = head.next;
        //循环获取数据
        for(int i=0;i<index;i++){
            cur=cur.next;  //向后移，移动到最后一个位置就是我们需要的数据节点
        }
        return cur;
    }
    /**
     *
     * 从尾部根据索引获取节点数据
     * 根据lastIndex【从链表的尾部开始计数】得到单向链表的数据
     *
     * @param lastIndex    索引【从尾部就必须从1开始】
     * @return
     */
    public HeroNode getNodeByLastIndex(int lastIndex){
        //表示没有数据
        if(!head.hasNext()){
            return null;
        }
        int size=size();  //得到链表节点数
        //如果索引超出范围则找不到数据
        if(lastIndex>size || lastIndex<=0){
            return null;
        }

        HeroNode cur = head.next;
        //循环获取数据
        for(int i=0;i<size-lastIndex;i++){
            cur=cur.next;  //向后移，移动到最后一个位置就是我们需要的数据节点
        }
        return cur;
    }


    /**
     * 对单向链表进行反转
     */
    public void reversalNode(){
        //如果单向链表数据为空，或者仅有一个数据，则不需要反转
        if(!head.hasNext() || !head.next.hasNext()){
            return;
        }
        //定义一个反转的临时头节点，用来协助反转
        HeroNode reversalHead=new HeroNode();
        HeroNode cur = head.next;
        HeroNode next=null;   //指向当前节点的下一个节点
        /**
         * 遍历链表
         */
        while (cur!=null){
            next=cur.next;  //获取当前节点的下一个节点
            cur.next = reversalHead.next;  //将cur的下一个节点指向链表的最前端
            reversalHead.next=cur; //将cur链接到新的链表上
            cur=next;//向后移动
        }
        //将head的next节点指向reversal的next节点，实现最终的反转
        head.next=reversalHead.next;
    }









    /**
     * 输出节点内容
     */
    public void show(){

        if(!head.hasNext()){
            log.info("链表为空show");
            return;
        }

        //头节点知识界标识节点，因此需要从头结点的next节点开始打印
        HeroNode temp=head.next;
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
class HeroNode{

    public int no;
    public String name;
    public String nickname;
    public HeroNode next;
    public HeroNode(){

    }
    public HeroNode(int no, String name, String nickname) {
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
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
