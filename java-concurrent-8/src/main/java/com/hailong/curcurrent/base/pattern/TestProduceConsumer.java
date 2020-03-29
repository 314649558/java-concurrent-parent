package com.hailong.curcurrent.base.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * Created by Administrator on 2020/3/14.
 */
@Slf4j(topic = "c.TestProduceConsumer")
public class TestProduceConsumer {

    public static void main(String[] args) {

        MessageQueue queue=new MessageQueue(2);

        for(int i=1;i<=3;i++){
            final int id=i;
            new Thread(()->{
                queue.put(new Message(id,"message"+id));
            },"生产者"+id).start();
        }

        new Thread(()->{
            while(true){
                queue.take();
            }
        },"消费者").start();
    }
}


@Slf4j(topic = "c.MessageQueue")
class MessageQueue{

    //消息容量，最多只能存放这么多消息
    private int capacity;

    private LinkedList<Message> messages=new LinkedList<>();

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 获取消息
     * @return
     */
    public Message take(){
        synchronized (messages){
            //如果队列为空则需要等待
            while (messages.isEmpty()){
                try {
                    log.info("没有消息，消费者需要等待");
                    messages.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            //如果有消息了则获取消息,获取队列头部的第一条消息
            Message message=messages.removeFirst();
            log.info(message.toString());
            messages.notifyAll();  //唤醒其他等待的线程
            return message;
        }
    }


    public void put(Message message){
        synchronized (messages){
            //如果消息已经达到容量大小了则需要等待其他线程取走消息才能重新存放
            while (messages.size()==capacity){
                try {
                    log.info("队列已满，生产者等待");
                    messages.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            log.info("生产了一条消息");
            //如果已经取走了消息
            messages.addLast(message);
            //唤醒其他线程，告诉其他线程有消息了可以获取消息了
            messages.notifyAll();
        }
    }



}

class Message{
    private int id;
    private Object value;
    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }
    public int getId() {
        return id;
    }
    public Object getValue() {
        return value;
    }
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
