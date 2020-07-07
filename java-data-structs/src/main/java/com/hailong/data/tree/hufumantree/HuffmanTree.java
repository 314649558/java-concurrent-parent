package com.hailong.data.tree.hufumantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2020/7/7.
 * 赫夫曼树(最优二叉树) ：wpl最小的树
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int arr[]={13,7,8,3,29,6,1};
        Node huffmanTree = createHuffmanTree(arr);
        System.out.println("前序遍历赫夫曼树");
        preOrder(huffmanTree);
    }


    public static void preOrder(Node root){
        if(root!=null) {
            root.preOrder();
        }else{
            System.out.println("空树不能遍历");
        }
    }

    /**
     * 创建赫夫曼树
     * @param arr  需要被创建赫夫曼树的原始数组
     */
    public static Node createHuffmanTree(int arr[]){
        //第一步：为了操作方便
        //1 遍历arr数组
        //2 将arr的每个元素构建成一个node
        //3 将node放入到ArrayList中
        List<Node> nodes=new ArrayList<>();
        for(int value:arr){
            nodes.add(new Node(value));
        }

        //排序，从小到大排序
        Collections.sort(nodes);

        System.out.println("排序后的数组");
        System.out.println(nodes);


        while (nodes.size()>1) {
            //(1)取出权值最小的节点(二叉树)
            Node leftNode = nodes.get(0);
            //(2)取出权值次小的节点(二叉树)
            Node rightNode = nodes.get(1);
            //(3)构建新的二叉树
            Node parentNode = new Node(leftNode.value + rightNode.value);
            parentNode.left = leftNode;
            parentNode.right = rightNode;
            //(4)从ArrayList中删除处理过的二叉树
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //(5)将parent加入到ArrayList
            //nodes.add(parentNode);
            //Collections.sort(nodes);  //排序

            nodes.add(0,parentNode);

        }

        //Collections.sort(nodes);
        /*System.out.println("第一次处理后nodes");
        System.out.println(nodes);*/

        //返回赫夫曼树的root节点
        return nodes.get(0);



    }
}


class Node implements Comparable<Node>{
    int value;//节点权值
    Node left; //指向左子节点
    Node right;//指向右子节点


    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }


    //前序遍历
    public void preOrder(){
        System.out.println(this);
        if(this.left!=null){
            this.left.preOrder();
        }

        if(this.right!=null){
            this.right.preOrder();
        }
    }

    @Override
    public int compareTo(Node o) {
        //表示从小到大排序
        return this.value - o.value;
    }
}
