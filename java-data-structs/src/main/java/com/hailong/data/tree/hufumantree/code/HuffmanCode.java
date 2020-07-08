package com.hailong.data.tree.hufumantree.code;

import java.io.*;
import java.util.*;
/**
 * Created by Administrator on 2020/7/7.
 * 赫夫曼编码-数据压缩
 */
public class HuffmanCode {
    public static void main(String[] args) {
        String content="i like like like java do you like a java";
        byte[] contentBytes = content.getBytes();
        System.out.println("为压缩前长度:"+contentBytes.length);
        /*
         分步创建赫夫曼编码过程[用于代码测试便于理解]
        List<Node> nodes = getNodes(contentBytes);
        System.out.println(nodes);
        System.out.println("哈夫曼树");
        Node huffmanTree = createHuffmanTree(nodes);
        preOrder(huffmanTree);
        System.out.println("生成的赫夫曼编码~~~");
        *//*getCodes(huffmanTree,"",stringBuilder);
        System.out.println(huffmancodes);*//*
        System.out.println(getCodes(huffmanTree));
        System.out.println("赫夫曼编码字符串");
        byte[] bytes = zip(contentBytes, huffmancodes);
        System.out.println(Arrays.toString(bytes));*/

        //封装后的方法
       /* byte[] bytes = huffmanZip(contentBytes);
        System.out.println(Arrays.toString(bytes));
        //System.out.println(byteToBitString(true,(byte)-88));
        byte[] sourceBytes = decode(huffmancodes, bytes);
        System.out.println("解码后字符串:"+new String(sourceBytes));*/

       //zipFile("E:\\src.jpg","E:\\dst.zip");

        unZipFile("E:\\dst.zip","E:\\src2.jpg");

    }


    public static void unZipFile(String zipFile,String dstFile){
        //先定义文件输入流
        InputStream is=null;
        ObjectInputStream ois=null;
        FileOutputStream os=null;
        try{
            is=new FileInputStream(zipFile);
            ois=new ObjectInputStream(is);
            byte[] huffmanBytes = (byte[]) ois.readObject();  //赫夫曼对象字节
            Map<Byte,String> huffmanCode = (Map<Byte,String>) ois.readObject();   //赫夫曼对象编码表
            byte[] bytes = decode(huffmanCode, huffmanBytes);


            os=new FileOutputStream(dstFile);
            os.write(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
                ois.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public static void zipFile(String srcFile,String dstFile){
        OutputStream os=null;
        ObjectOutputStream oos=null;
        FileInputStream is=null;
        try{
            is=new FileInputStream(srcFile);
            byte[] b=new byte[is.available()];
            is.read(b);
            byte[] huffmanBytes=huffmanZip(b);

            os=new FileOutputStream(dstFile);
            oos=new ObjectOutputStream(os);

            oos.writeObject(huffmanBytes); //写入赫夫曼编码字节数
            oos.writeObject(huffmancodes); //赫夫曼编码写入，为后续解压

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
                oos.close();
                os.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 完成对压缩数据的解码
     * @param huffmanCodes  赫夫曼编码表
     * @param huffmanBytes  赫夫曼编码得到的字节数组
     * @return 原来字符串对应的字节数组
     */
    private static byte[] decode(Map<Byte,String> huffmanCodes,byte[] huffmanBytes){
        //1.先得到huffmanBytes对应的二进制字符串，形式如1001001001...
        StringBuilder stringBuilder=new StringBuilder();
        //2. 将byte数组转成二进制的字符串
        for(int i=0;i<huffmanBytes.length;i++){
            if(i==huffmanBytes.length-1){
                stringBuilder.append(byteToBitString(false,huffmanBytes[i]));
            }else{
                stringBuilder.append(byteToBitString(true,huffmanBytes[i]));
            }
        }
        System.out.println("赫夫曼字节数组转对应的二进制字符串~~~");
        System.out.println(stringBuilder.toString());


        //把字符串按照指定的赫夫曼编码进行解码
        //把赫夫曼编码表进行调换，因为需要反向查询
        Map<String,Byte> map=new HashMap<>();
        for(Map.Entry<Byte,String> entry:huffmanCodes.entrySet()){
            map.put(entry.getValue(),entry.getKey());
        }

        //创建一个集合，存放byte
        List<Byte> list=new ArrayList<>();
        for(int i=0;i<stringBuilder.length();){
            int count=1;
            boolean flag=true;
            Byte b=null;

            while(flag) {
                String key = stringBuilder.substring(i, i + count);
                b = map.get(key);
                if (b == null) {
                    count++;
                } else {
                    flag = false;
                }
            }
            list.add(b);
            i += count;  //i直接移动到count的位置
        }

        byte[] b=new byte[list.size()];

        for(int i=0;i<b.length;i++){
            b[i]=list.get(i);
        }
        return b;
    }




    /**
     * 将byte转换成一个二进制的字符串
     * @param b
     * @param flag 是否需要补高位，true 需要补高位，flase不需要补高位
     * @return
     */
    private static String byteToBitString(boolean flag,byte b){
        //使用一个变量保存b
        int temp=b;  //将b转换成了int

        if(flag){
            temp|=256; //按位与
        }
        String str = Integer.toBinaryString(temp);  //这个地方返回的是temp对应的二进制补码
        if(flag) {
            return str.substring(str.length() - 8);
        }else{
            return str;
        }
    }
    /**
     * 封装生成huffman编码的方法
     * @param bytes 原始字符串对应的字节数组
     * @return 返回经过赫夫曼编码处理后的字节数组(压缩后的数组)
     */
    private static byte[] huffmanZip(byte[] bytes){
        List<Node> nodes = getNodes(bytes);
        Node huffmanTree = createHuffmanTree(nodes);
        Map<Byte, String> huffmanCodes = getCodes(huffmanTree);
        byte[] huffmanByteCodes = zip(bytes, huffmanCodes);
        return huffmanByteCodes;
    }


    /**
     * 编写一个方法，将一个字符串对应的byte数组，通过生成的赫夫曼编码表，返回一个赫夫曼编码处理后的一个byte数组
     * @param bytes 原始字符串对应的byte[]
     * @param hufumanCodes  赫夫曼编码
     * @return 赫夫曼编码处理后的byte[]
     */
    private static byte[] zip(byte[] bytes,Map<Byte,String> hufumanCodes){
        //1. 利用hufumanCodes 将bytes转成赫夫曼编码对应的字符串
        StringBuilder stringBuilder=new StringBuilder();

        //遍历bytes
        for(byte b:bytes){
            stringBuilder.append(hufumanCodes.get(b));
        }
        //对应的赫夫曼编码字符串 字符串长度133
        //System.out.println(stringBuilder.toString());

        //统计返回byte[] huffmanCodeBytes长度
        int len=stringBuilder.length()%8==0?stringBuilder.length()/8:stringBuilder.length()/8+1;

        //创建存储压缩后的byte数组
         byte[] huffmanCodeBytes=new byte[len];

         int index=0;

         for(int i=0;i<stringBuilder.length();i+=8){//步长为8
             String strByte;

             if(i+8>stringBuilder.length()){
                 strByte=stringBuilder.substring(i);
             }else{
                 strByte=stringBuilder.substring(i,i+8);
             }
            //将strByte 转换成一个byte,放入到huffmanCodeBytes
             huffmanCodeBytes[index]= (byte) Integer.parseInt(strByte,2);
             index++;
         }

         return huffmanCodeBytes;


    }


    //将生成的赫夫曼树生成对应的赫夫曼编码
    //思路分析：
    //1. 将赫夫曼编码存放在Map(Byte,String)中，形式 32->01 97->100 100->11000 等
    static Map<Byte,String> huffmancodes=new HashMap<>();
    //2. 在生成赫夫曼编码表时需要去拼接路径，定义一个StringBuilder 存储某个叶子节点的路径
    static StringBuilder stringBuilder=new StringBuilder();


    /**
     * 重载getCodes
     * @param node
     * @return
     */
    private static Map<Byte,String> getCodes(Node node){
        if(node==null){
            return null;
        }else{
            /*getCodes(node.left,"0",stringBuilder);
            getCodes(node.right,"1",stringBuilder);*/
            getCodes(node,"",stringBuilder);
            return huffmancodes;
        }
    }
    /**
     * 将传入的node节点的所有叶子节点的赫夫曼编码得到，并放入到huffmancodes集合中
     * @param node  传入的节点
     * @param code  路劲：左子节点是0，右子节点是1
     * @param s     用于拼接路径
     */
    private static void getCodes(Node node,String code,StringBuilder s){
        StringBuilder stringBuilder2 = new StringBuilder(s);

        //将传入的code加入到buffer
        stringBuilder2.append(code);
        if(node != null){
            //判断当前节点是否是叶子结点
            if(node.data==null){ //表示非叶子节点
                //递归处理
                //向左
                getCodes(node.left,"0",stringBuilder2);
                //向右
                getCodes(node.right,"1",stringBuilder2);
            }else{
                //如果为空表示找到了某个叶子节点的最后
                huffmancodes.put(node.data,stringBuilder2.toString());
            }
        }
    }


    /**
     * 前序遍历
     * @param node
     */
    private static void preOrder(Node node){
        if(node!=null){
            node.preOrder();
        }else{
            System.out.println("赫夫曼树为空，不能遍历~~~");
        }
    }


    /**
     *
     * @param bytes 接收的字节数组
     * @return  返回List
     */
    private static List<Node> getNodes(byte[] bytes){
        ArrayList<Node> nodes = new ArrayList<>();

        //统计每个byte出现的次数
        HashMap<Byte, Integer> countsMap = new HashMap<>();
        for(byte b:bytes){
            Integer count = countsMap.get(b);
            if(count==null){
                countsMap.put(b,1);
            }else{
                countsMap.put(b,count+1);
            }
        }
        //把每个键值对转换成node对象，并加入到nodes集合中
        for(Map.Entry<Byte,Integer> entry:countsMap.entrySet()){
            nodes.add(new Node(entry.getKey(),entry.getValue()));
        }
        return nodes;
    }


    /**
     * 通过List创建赫夫曼树
     * @return
     */
    private static Node createHuffmanTree(List<Node> nodes){
        while(nodes.size()>1){
            //排序  从小到大排序
            Collections.sort(nodes);
            //取出第一个最小的二叉树
            Node leftNode = nodes.get(0);
            //取出第二小的二叉树
            Node rightNode=nodes.get(1);
            //创建一个新的二叉树，它的根节点没有data,只有权重
            Node parentNode=new Node(leftNode.weight+rightNode.weight);
            parentNode.left=leftNode;
            parentNode.right=rightNode;
            //删除处理过的二叉树
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //将parentNode添加到List中
            nodes.add(parentNode);
        }
        //经过上述处理后nodes只会有一个哈夫曼树的根节点
        return nodes.get(0);
    }





}

class Node implements Comparable<Node>{
    Byte data;//存放数据本身，比如'a'=>97  ' '=>32
    int weight;//权值，在字符中出现的次数
    Node left;
    Node right;

    public Node(int weight) {
        this.weight = weight;
    }

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    /**
     * 前序遍历方法
     */
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
        return this.weight-o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }
}
