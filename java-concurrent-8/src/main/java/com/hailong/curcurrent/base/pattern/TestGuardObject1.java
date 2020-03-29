package com.hailong.curcurrent.base.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2020/3/14.
 * 保护性暂停 设计模式 增强
 */
@Slf4j(topic = "c.TestGuardObject")
public class TestGuardObject1 {

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        TimeUnit.SECONDS.sleep(1);
        for (Integer id : MailBoxs.getIds()) {
            new Postman(id, "内容" + id).start();
        }

    }
}



@Slf4j(topic = "c.People")
class People extends Thread{
    @Override
    public void run() {
        // 收信
        GuardObject1 guardedObject = MailBoxs.createGuardedObject();
        log.debug("开始收信 id:{}", guardedObject.getId());
        Object mail = guardedObject.get(5000);
        log.debug("收到信 id:{}, 内容:{}", guardedObject.getId(), mail);
    }
}


@Slf4j(topic = "c.Postman")
class Postman extends Thread {
    private int id;
    private String mail;

    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardObject1 guardedObject = MailBoxs.getGuardedObject(id);
        log.debug("送信 id:{}, 内容:{}", id, mail);
        guardedObject.complete(mail);
    }
}



class MailBoxs{
    private static Map<Integer, GuardObject1> boxes = new Hashtable<>();

    private static int id = 1;
    // 产生唯一 id
    private static synchronized int generateId() {
        return id++;
    }

    public static GuardObject1 getGuardedObject(int id) {
        return boxes.remove(id);
    }

    public static GuardObject1 createGuardedObject() {
        GuardObject1 go = new GuardObject1(generateId());
        boxes.put(go.getId(), go);
        return go;
    }

    public static Set<Integer> getIds() {
        return boxes.keySet();
    }
}






class GuardObject1{

    private int id;

    public GuardObject1(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private Object respose;

    public synchronized Object get(long timeout){
        long begin=System.currentTimeMillis();

        long parseTime=0;

        //如果没有获得结果就继续等待
        while (this.respose==null){
            long waitTime=timeout-parseTime;
            if(waitTime<=0){
                break;
            }

            try {
                this.wait(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            parseTime=System.currentTimeMillis()-begin;
        }
        return respose;
    }

    public synchronized  void complete(Object respose){
            this.respose = respose;
            this.notify();
    }


}
