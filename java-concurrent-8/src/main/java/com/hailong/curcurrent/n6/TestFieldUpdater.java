package com.hailong.curcurrent.n6;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Created by Administrator on 2020/3/29.
 * 字段更新器，保证某个引用对象中的字段的原子性
 */
@Slf4j(topic = "c.TestFieldUpdater")
public class TestFieldUpdater {
    public static void main(String[] args) {
        Student stu=new Student();

        AtomicReferenceFieldUpdater updater=AtomicReferenceFieldUpdater.newUpdater(Student.class,String.class,"name");

        boolean result= updater.compareAndSet(stu, null, "hailong");
        log.info("{}",result);
        log.info("{}",stu);

        //这一次更新失败，因为上面已经更行为hailong了
        result=updater.compareAndSet(stu, null, "hailong2");
        log.info("{}",result);
        log.info("{}",stu);

    }
}

@Data
class Student{
    volatile String name;  //必须用volatile来保证可见性，否则会报错
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
