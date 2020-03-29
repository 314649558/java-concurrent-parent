package com.hailong.curcurrent.n6;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2020/3/29.
 */
public class UnsafeAccessor {
    private final static Unsafe unsafe;
    static {
        Field theUnsafe = null;
        try {
            theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new Error(e);
        }
    }
    public static Unsafe getUnsafe(){
        return unsafe;
    }
}
