package com.hailong.curcurrent.base.exercies;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2020/3/12.
 */
@Slf4j(topic = "c.SellTicket")
public class SellTicket {

    public static void main(String[] args) throws InterruptedException {
        TickWindow window=new TickWindow(1000);
        List<Integer> amountLst=new ArrayList<>();
        List<Thread> threadLst=new ArrayList<>();
        for(int i=0;i<2000;i++){
            Thread thread=new Thread(()->{
                int amount=window.sell(random(5));
                try {
                    Thread.sleep(random(5));  //随机休眠，增加CPU上下文切换概率
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //卖出的票数
                amountLst.add(amount);
            });
            threadLst.add(thread);
            thread.start();
        }

        for(Thread thread:threadLst){
            thread.join();
        }

        //如果下面两个数加起来不等于总票数，则证明是非安全的
        log.info("剩余票数:"+window.getCount());
        log.info("总卖出票数:"+amountLst.stream().mapToInt(i -> i).sum());


    }


    static Random random=new Random();

    public static int random(int amount){
        return random.nextInt(amount)+1;
    }

}


//售票窗口类
class TickWindow{

    private int count;

    public TickWindow(int count){
        this.count=count;
    }

    public int getCount() {
        return count;
    }

    /**
     * 卖票
     * @param amount
     */
    public synchronized int sell(int amount){
        if(this.count>=amount){
            this.count-=amount;
            return amount;
        }else{
            return 0;
        }
    }


}
