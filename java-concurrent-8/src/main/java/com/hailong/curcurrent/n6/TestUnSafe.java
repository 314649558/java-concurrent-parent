package com.hailong.curcurrent.n6;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2020/3/29.
 */
@Slf4j(topic = "c.TestUnSafe")
public class TestUnSafe {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        Teacher teacher=new Teacher();

        long idOffset=unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset=unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        unsafe.compareAndSwapInt(teacher,idOffset,0,1);
        unsafe.compareAndSwapObject(teacher,nameOffset,null,"hailong");

        log.info("{}",teacher);

    }
    
    
}

@Data
class Teacher{
    private int id;
    private String name;
}
