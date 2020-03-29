package com.hailong.curcurrent.base;

import org.openjdk.jol.info.ClassLayout;

/**
 * Created by Administrator on 2020/3/12.
 *
 * -XX:-UseBiasedLocking   禁用偏向锁
 */
public class TestBasic {

    public static void main(String[] args) {
        Dog dog=new Dog();
        System.out.println(ClassLayout.parseClass(Dog.class).toPrintable(dog));
    }
}


class Dog{

}
