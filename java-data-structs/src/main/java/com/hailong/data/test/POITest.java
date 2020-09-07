package com.hailong.data.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/9/2.
 */
public class POITest {

    public static List<POI> poiLst1=new ArrayList<>();
    public static List<POI> poiLst2=new ArrayList<>();
    static {
        poiLst1.add(new POI("poi1_1","永琪美容美发",1.0,2.0,"北京"));
        poiLst1.add(new POI("poi1_2","王黄美容美发",10.0,12.0,"上海"));
        poiLst1.add(new POI("poi1_3","张珊美容美发(深圳罗湖店)",2.0,1.0,"深圳"));
        poiLst1.add(new POI("poi1_4","王五美容美发",11.0,22.0,"长沙"));
        poiLst1.add(new POI("poi1_5","李四美容美发",2.0,4.0,"北京"));

        poiLst2.add(new POI("poi2_1","永琪美容美发1",1.0,2.0,"北京"));
        poiLst2.add(new POI("poi2_2","王黄美容美发",11.0,12.0,"上海"));
        poiLst2.add(new POI("poi2_3","张珊美容美发",2.0,1.0,"深圳"));
        poiLst2.add(new POI("poi2_4","王五美容美发",11.0,22.0,"上海"));
        poiLst2.add(new POI("poi2_5","傻蛋美容美发",2.0,4.0,"北京"));
    }

    public static void main(String[] args) {
        List<String> outList=new ArrayList<>();
        for(int i=0;i<poiLst1.size();i++){
            POI tempPoi1 = poiLst1.get(i);
            for(int k=0;k<poiLst2.size();k++){
                POI tempPoi2 = poiLst2.get(k);
                if(tempPoi1.latitude!=tempPoi2.latitude || tempPoi1.lonqitude!=tempPoi2.lonqitude || tempPoi1.address!=tempPoi2.address){
                    continue;
                }
                if(tempPoi1.poiName.indexOf(tempPoi2.poiName)>=0 || tempPoi2.poiName.indexOf(tempPoi1.poiName)>=0) {
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(tempPoi1.poiId)
                            .append(",")
                            .append(tempPoi1.poiName)
                            .append(",")
                            .append(tempPoi1.latitude)
                            .append(",")
                            .append(tempPoi1.lonqitude)
                            .append(",")
                            .append(tempPoi2.poiId)
                            .append(",")
                            .append(tempPoi2.poiName)
                            .append(",")
                            .append(tempPoi2.latitude)
                            .append(",")
                            .append(tempPoi2.lonqitude);
                    outList.add(buffer.toString());
                    break;
                }
            }

        }


        //输出结果
        for(String r:outList){
            System.out.println(r);
        }


    }

}
