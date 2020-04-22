package linked;

import lombok.extern.slf4j.Slf4j;

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

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("当前节点数:{}",danLinkedList.size());

    }

}


/**
 * 单向链表对象
 */
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
