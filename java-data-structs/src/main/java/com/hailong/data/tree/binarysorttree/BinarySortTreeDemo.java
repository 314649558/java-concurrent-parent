package com.hailong.data.tree.binarysorttree;

/**
 * Created by Administrator on 2020/7/10.
 * 二叉排序树：左子节点的值必须小于父节点，右子节点的值必须大于父节点
 *   如：
 *                7
 *            3       12
 *        1      5  10    14
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr={7,3,10,12,5,1,9,2};
        BinarySortTree binarySortTree=new BinarySortTree();
        //循环生成二插排序树
        for(int i=0;i<arr.length;i++){
            binarySortTree.add(new Node(arr[i]));
        }
        //中序遍历 打印结果
        binarySortTree.infixOrder();
        binarySortTree.delNode(1);
        System.out.println("删除节点");
        binarySortTree.infixOrder();
    }
}

/**
 * 二叉排序树
 */
class BinarySortTree{
    private Node root;

    public void add(Node node){
        if(root==null){
            root=node;//如果为空则直接将新增节点挂到根节点
        }else{
            root.add(node);
        }
    }



    public Node search(int value){
        if(root==null){
            return null;
        }else{
            return root.search(value);
        }
    }


    public Node searchParent(int value){
        if(root==null){
            return null;
        }else{
            return root.searchParent(value);
        }
    }

    /**
     * 返回以node为根节点的二叉排序树最小节点的值
     * @param node
     * @return
     */
    public int delRightTreeMin(Node node){
        Node targetNode=node;
        //查找左子节点就会找到最小的Node节点值
        while (targetNode.left!=null){
            targetNode=targetNode.left;
        }
        //删除最小节点
        delNode(targetNode.value);
        return targetNode.value;
    }

    public void delNode(int value){
        if(root==null){
            return;
        }else{
            Node targetNode=search(value);//需要删除的节点
            //没有查找到需要删除的节点
            if(targetNode==null){
                return;
            }
            //表示只有一个根节点
            if(root.left==null && root.right==null){
                root=null;
                return;
            }
            //查找targetNode的父节点
            Node parentNode=searchParent(value);
            //如果删除的节点是叶子结点
            if(targetNode.left==null && targetNode.right==null){
                //判断targetNode是父节点的左子节点还是右子节点
                if(parentNode.left!=null && parentNode.left.value==value){
                    parentNode.left=null;
                }else if(parentNode.right!=null && parentNode.right.value==value){
                    parentNode.right=null;
                }
            }else if(targetNode.left!=null && targetNode.right!=null){//左右子节点都不为空
                int minVal = delRightTreeMin(targetNode.right);
                targetNode.value=minVal;
            }else{//左右子节点其中一个为空的情况
                //如果要删除的节点有左子节点
                if (targetNode.left != null) {
                    if(parentNode!=null) {
                        if (parentNode.left.value == value) {
                            parentNode.left = targetNode.left;
                        } else {
                            parentNode.right = targetNode.left;
                        }
                    }else{
                        parentNode=parentNode.left ;
                    }
                } else {//有右子节点
                    if(parentNode!=null) {
                        if (parentNode.left.value == value) {
                            parentNode.left = targetNode.right;
                        } else {
                            parentNode.right = targetNode.right;
                        }
                    }else{
                        parentNode=parentNode.right;
                    }
                }
            }
        }
    }


    public void infixOrder(){
        if(root!=null){
            root.infixOrder();
        }else{
            System.out.println("二叉排序树为空，无法遍历~~~");
        }
    }
}



/**
 * 节点
 */
class Node{
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }


    /**
     * 二叉排序树新增节点方法
     * @param node  需要新增的节点
     */
    public void add(Node node){
        if(node==null){
            return;
        }

        //小于当前节点这天加到左边
        if(node.value<this.value){
          if(this.left==null){
              this.left=node;
          }else{
              this.left.add(node);//如果不为空则递归继续判断
          }
        }else{ //新增节点大于当前节点的值则新增到当前节点的右边
            if(this.right==null){
                this.right=node;
            }else{
                this.right.add(node);
            }
        }
    }


    /**
     * 查找需要删除的节点
     * @param value   需要删除的节点值
     * @return  找到返回Node 否则返回null
     */
    public Node search(int value){
        //当前节点
        if(value==this.value){
            return this;
        }else if(value<this.value){//如果查找的值小于当前值则需要向左查找
            if(this.left!=null) {
                return this.left.search(value);
            }else{
                return null;
            }
        }else{
            if(this.right!=null){
                return this.right.search(value);
            }else{
                return null;
            }
        }

    }

    /**
     * 查找要删除节点的父节点
     * @param value 需要查找的值
     * @return  返回父节点，没有折返回Null
     */
    public Node searchParent(int value){
        //如果当前节点是就是需要删除节点的父节点，则直接返回
        if((this.left!=null &&this.left.value==value)
                || (this.right!=null && this.right.value==value)){
            return this;
        }else{
            //如果查找的值小于当前节点的值,并且当前节点的左子节点不为空
            if(value<this.value && this.left!=null){
                return this.left.searchParent(value);
            }else if(value>=this.value && this.right!=null){//如果查找的值不小于当前节点的值,并且当前节点的右子节点不为空
                return this.right.searchParent(value);
            }else{
                return null;
            }
        }
    }
    /**
     * 中序遍历方法
     */
    public void infixOrder(){
        if(this.left!=null){
            this.left.infixOrder();
        }
        System.out.println(this);
        if(this.right!=null){
            this.right.infixOrder();
        }
    }


    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
