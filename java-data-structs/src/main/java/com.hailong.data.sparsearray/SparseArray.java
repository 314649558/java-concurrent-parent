package com.hailong.data.sparsearray;

import com.hailong.common.io.FileUtils;

import java.util.List;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/19
 * Description:
 *     将一个普通数组转换为稀疏数组，稀疏数组支持本地文件存储，也能从本地文件中读取稀疏数组后还原为原数组
 *
 *     主要优点：可以大大的节约空间
 */
public class SparseArray {

    private static String path="D:\\chessarr.txt";

    /**
     * 初始化一个11行11列的棋盘
     */
    private static int[][] chessArr = new int[11][11];

    /**
     * 对盘的落子进行初始化
     * 0 表示没有落子  1 表示黑子  2 表示白子
     */
    static {
        //第3行第4列落黑子
        chessArr[2][3]=1;
        //第4行第5列落白子
        chessArr[3][4]=2;
    }

    /**
     * 对二维数组进行打印
     */
    public static void printArr(int arr[][]){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                System.out.printf("%d\t",arr[i][j]);
            }
            //换行
            System.out.println();
        }
    }


    /**
     * 将原始数组[棋盘]转换为稀疏数组
     * 稀疏数组结果  多行3列
     * 1 其中第一行第一列存储 原始数组行数   第一行第二列存储原始数组列数  第一行第三列存储 落子数
     */
    public static int[][] toSparseArr(){
        //先计算原始数组的落子数
        int sum=0;
        for(int i=0;i<chessArr.length;i++){
            for(int j=0;j<chessArr[i].length;j++){
                if(chessArr[i][j]!=0){
                    sum+=1;
                }
            }
        }

        //初始化稀疏数组
        int[][] sparseArr = new int[sum+1][3];
        //给稀疏数组添加数据【首行】
        sparseArr[0][0]=chessArr.length;  //行数
        sparseArr[0][1]=chessArr[0].length; //列数
        sparseArr[0][2]=sum;

        //将原始数组中非0的数据，放到稀疏数组中

        int index=1;

        for(int i=0;i<chessArr.length;i++){
            for(int j=0;j<chessArr[i].length;j++){
                if(chessArr[i][j]!=0){
                    sparseArr[index][0]=i;//行数
                    sparseArr[index][1]=j; //列数
                    sparseArr[index][2]=chessArr[i][j];
                    index++;
                }
            }
        }

        return sparseArr;
    }

    public static void main(String[] args) {
        System.out.println("-------------初始棋盘----------------");
        SparseArray.printArr(chessArr);
        //将二维数组转换为稀疏数组
        int[][] sparseArr = SparseArray.toSparseArr();
        System.out.println("-------------稀疏数组----------------");
        SparseArray.printArr(sparseArr);

        //稀疏数组暂存【写入到一个本地文件中】
        String s = FileUtils.arrToText(sparseArr);
        FileUtils.writeToFile(path,s);
        //读取暂存棋盘【从本地读取数据】
        List<String> chessLst = FileUtils.readFromFile(path);
        System.out.println("----------从暂存文件中读取棋盘并打印-------------------");
        int[][] sparseArr2=new int[chessLst.size()][3];
        //还原稀疏数组
        for (int i=0;i<chessLst.size();i++){
            String[] rows = chessLst.get(i).split("\t");
            sparseArr2[i][0]=Integer.valueOf(rows[0]);
            sparseArr2[i][1]=Integer.valueOf(rows[1]);
            sparseArr2[i][2]=Integer.valueOf(rows[2]);
        }
        SparseArray.printArr(sparseArr2);
        //还原原始棋盘
        int[][] chessArr2=new int[sparseArr2[0][0]][sparseArr2[0][1]];

        for(int i=1;i<sparseArr2.length;i++){
            chessArr2[sparseArr2[i][0]][sparseArr2[i][1]]=sparseArr2[i][2];
        }
        System.out.println("----------还原后的棋盘--------");
        SparseArray.printArr(chessArr2);
    }


}
