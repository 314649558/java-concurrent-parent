package com.hailong.curcurrent.base;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2020/3/27.
 */
@Slf4j(topic = "c.Test")
public class Test {

    private static final int MAXIMUM_CAPACITY= 1 << 30 ;

    public static void main(String[] args) {

    }


    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
