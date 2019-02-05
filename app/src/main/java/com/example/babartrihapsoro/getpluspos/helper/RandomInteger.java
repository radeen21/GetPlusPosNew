package com.example.babartrihapsoro.getpluspos.helper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by randiwaranugraha on 3/2/17.
 */

public class RandomInteger {

    private static final AtomicInteger atomicInteger = new AtomicInteger(1000);

    private RandomInteger() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static int next() {
        return atomicInteger.incrementAndGet();
    }
}