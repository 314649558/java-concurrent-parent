package com.hailong.test;

import com.hailong.common.io.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Administrator on 2020/5/29.
 */
@Slf4j(topic = "c.GenDataTest")
public class GenDataTest {


    private  static final Random random=new Random();

    public static void main(String[] args) {

        genData2();

        log.info("写出成功");
    }


    private static  void genData2(){
        String[] city={"北京","上海","深圳","广州","长沙","益阳","常德","泉州","福州","厦门","漳州","许昌","郑州","合肥","安庆","西安","成都","武汉"};

        int citySize=city.length;

        log.info("城市总量:{}",city.length);

        String[] name={"张三","李四","王五","宋江","林冲","卢俊义","鲁智深","李逵","张飞","关羽","刘备","曹操","孙悟空","猪八戒","沙和尚","小白龙","薛宝钗"};

        int nameSize=name.length;


        StringBuffer buffer=new StringBuffer();

        for(int i=0;i<1000000;i++){
            String cityName=city[random.nextInt(citySize)];
            String nameStr=name[random.nextInt(nameSize)]+random.nextInt(1000);
            int salary=random.nextInt(100000);
            String line=cityName+","+nameStr+","+salary+"\r\n";
            buffer.append(line);
        }
        FileUtils.writeToFile("E:\\city.txt",buffer.toString());
    }


    private static void genData1() {
        String sessionId="";
        long timestamp=0L;


        List<String> list=new ArrayList<>();


        //随机生成n个sessionid
        for(int i=0;i<500;i++){
            sessionId= UUID.randomUUID().toString().replaceAll("-","");
            list.add(sessionId);
        }
        log.info("sessionId 数:{}",list.size());
        StringBuffer buffer=new StringBuffer();
        for (int i=0;i<1000000;i++){
            int r=random.nextInt(list.size());
            int tr=random.nextInt(1000*60*10);
            sessionId=list.get(r);
            timestamp=System.currentTimeMillis()+tr;
            //log.info("sessionId:{}",sessionId);
            //log.info("timeStamp:{}",timestamp);
            String line=sessionId+","+timestamp+"\r\n";
            buffer.append(line);
        }

        FileUtils.writeToFile("E:\\sessionId.txt",buffer.toString());
    }


}
