package com.hailong.data.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Administrator on 2020/7/12.
 */
public class Graph {

    private ArrayList<String> vertexList;//存储顶点的集合

    private int[][] edges; //存储图对应的邻接矩阵

    private int numOfEdges;//边的数量

    //定义一个数组，记录某个节点是否被访问过
    private boolean[] isVisited;


    public Graph(int n) {
        vertexList=new ArrayList<>(n);
        edges=new int[n][n];
        isVisited=new boolean[n];
    }

    /**
     * 得到第一个邻接节点下标
     * @param index
     * @return 如果存在返回对应的下标，否则返回-1
     */
    public int getFirstNeighbor(int index){
        for(int j=0;j<vertexList.size();j++){
            if(edges[index][j]>0){
                return j;
            }
        }
        return -1;
    }


    /**
     * 根据前一个邻接节点的下标获取下一个邻接节点
     * @param v1
     * @param v2
     * @return
     */
    public int getNextNeighbor(int v1,int v2){
        for(int j=v2+1;j<vertexList.size();j++){
            if(edges[v1][j]>0){
                return j;
            }
        }
        return -1;
    }


    /**
     * 深度优先算法遍历
     * @param isVisited
     * @param i
     */
    public void dfs(boolean[] isVisited,int i){
       //首次访问该节点
        System.out.print(getValueByIndex(i)+"->");
        //将该节点设置为已访问
        isVisited[i]=true;

        //查找节点i的第一个邻接节点w
        int w=getFirstNeighbor(i);
        //有邻接节点
        while(w!=-1){
            //没有被访问过
            if(!isVisited[w]){
                dfs(isVisited,w);
            }

            //如果w节点已经被访问过
            w=getNextNeighbor(i,w);
        }
        //w不存在的情况
    }


    public void dfs(){
        //遍历所有的节点，进行DFS
        for(int i=0;i<getNumofVertex();i++){
            if(!isVisited[i]){
                dfs(isVisited,i);
            }
        }
    }



    /**
     * 对一个节点进行广度优先遍历的方法
     * @param isVisited
     * @param i
     */

    private void bfs(boolean[] isVisited,int i){
        int u;//表示队列的头结点对应的下标
        int w;//邻接节点
        //队列，记录节点访问的顺序
        LinkedList<Integer> queue = new LinkedList<>();
        //访问节点
        System.out.print(getValueByIndex(i)+"->");
        //标记为已访问
        isVisited[i]=true;
        //将节点加入队列
        queue.addLast(i);

        while (!queue.isEmpty()){
            //取出队列的头结点下标
            u = queue.removeFirst();

            w=getFirstNeighbor(u);


            while (w!=-1){
                //是否被访问过
                if(!isVisited[w]){
                    System.out.print(getValueByIndex(w)+"->");

                    isVisited[w]=true;

                    //入列
                    queue.addLast(w);
                }

                //以u为前驱点，找w后面的下一个邻接点
                w=getNextNeighbor(u,w);

            }

        }

    }






    /**
     */
    public void bfs(){
        //遍历所有的节点，进行BFS
        for(int i=0;i<getNumofVertex();i++){
            if(!isVisited[i]){
                bfs(isVisited,i);
            }
        }
    }




    //图中常用的方法

    /**
     * 返回顶点数
     * @return
     */
    public int getNumofVertex(){
        return vertexList.size();
    }


    /**
     * 返回边的数目
     */
    public int getNumOfEdges(){
        return numOfEdges;
    }


    /**
     * 根据索引返回节点名称
     * @param i
     * @return
     */
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    /**
     * 返回权重
     * @param v1
     * @param v2
     * @return
     */
    public int getWeight(int v1,int v2){
        return edges[v1][v2];
    }

    /**
     * 显示图对应的矩阵
     */
    public void showGraph(){
        for(int[] link:edges){
            System.out.println(Arrays.toString(link));
        }
    }


    //插入顶点
    public void insertVertex(String vertex){
        vertexList.add(vertex);
    }

    /**
     * 添加边
     * @param v1 表示点的下标，既第几个顶点
     * @param v2 第二个顶点对应的下标
     * @param weight
     */
    public void insertEdge(int v1,int v2 ,int weight){
        edges[v1][v2]=weight;
        edges[v2][v1]=weight;
        numOfEdges++;
    }


    public static void main(String[] args) {
        String vetexs[]={"A","B","C","D","E"};

        //创建图对象
        Graph graph = new Graph(vetexs.length);

        //添加顶点
        for(String vetex:vetexs){
            graph.insertVertex(vetex);
        }

        //添加边
        //A-B,A-C,B-C,B-D,B-E
        graph.insertEdge(0,1,1);
        graph.insertEdge(0,2,1);
        graph.insertEdge(1,2,1);
        graph.insertEdge(1,3,1);
        graph.insertEdge(1,4,1);


        System.out.println("打印邻接矩阵");
        graph.showGraph();


        //测试深度优先遍历
        /*System.out.println("深度遍历");
        graph.dfs();*/

        System.out.println("广度优先");
        graph.bfs();
    }
}
