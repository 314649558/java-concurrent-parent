package com.hailong.data.recursion;

/**
 * Created by Administrator on 2020/5/7.
 */
public class MiGong {

    public static void main(String[] args) {

        //初始化一个8行7列的地图
        int[][] map=new int[8][7];

        //第一行和最后一行初始化为1 表示墙
        for(int i=0;i<7;i++){
            map[0][i]=1;
            map[7][i]=1;
        }

        //第一列和最后一列初始化为1
        for(int i=0;i<8;i++){
            map[i][0]=1;
            map[i][6]=1;
        }

        map[3][1]=1;
        map[3][2]=1;

        System.out.println("初始化地图-------------------------------");
        for (int i=0;i<8;i++){
            for (int j=0;j<7;j++){
                System.out.print(map[i][j]+" ");
            }
            //换行
            System.out.println();
        }

        //从初始位置(1,1)寻找终点(6,5)
        findWay(map,1,1);
        System.out.println("走过后的路线-------------------------------");
        for (int i=0;i<8;i++){
            for (int j=0;j<7;j++){
                System.out.print(map[i][j]+" ");
            }
            //换行
            System.out.println();
        }
    }


    /**
     * 通过递归找路
     *    如果map[6][5]=2 表示已经找到了路
     *    说明：
     *    1 表示墙，0 表示还没有走过的路  ， 2 表示已经走过了  3 表示走过，但是此路不通
     *    2 策略，下->右->上->左
     * @param map  地图
     * @param i    行
     * @param j    列
     * @return
     */
    public static boolean findWay(int[][] map,int i,int j){
        //已经抵达目的地，直接返回
        if(map[6][5]==2){
            return true;
        }else{
            if(map[i][j]==0){
                map[i][j]=2;//假定该路线是可以走通的

                if(findWay(map,i+1,j)){//向下走
                    return true;
                }else if(findWay(map,i,j+1)){//向右走
                    return true;
                }else if(findWay(map,i-1,j)){//向上走
                    return true;
                }else if(findWay(map,i,j-1)){//向左走
                    return true;
                }else{
                    //此路不通
                    map[i][j]=3;
                    return false;
                }

            }else{
                return false;
            }
        }
    }





}
