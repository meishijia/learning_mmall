package com.mmall.test;

import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalTest {
    @Test
    public void test1(){
        System.out.println(0.05+0.01);
        System.out.println(1.0-0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);
    }

    @Test
    public void test2(){
        BigDecimal b1 = new BigDecimal(0.05);
        BigDecimal b2 = new BigDecimal(0.01);
        BigDecimal b3 = new BigDecimal(1.0);
        BigDecimal b4 = new BigDecimal(0.42);
        BigDecimal b5 = new BigDecimal(4.015);
        BigDecimal b6 = new BigDecimal(100);
        BigDecimal b7 = new BigDecimal(123.3);
        System.out.println(b1.add(b2));
        System.out.println(b3.subtract(b4));
        System.out.println(b5.multiply(b6));
        System.out.println(b7.divide(b6));
    }

    @Test
    public void test3(){
        BigDecimal b1 = new BigDecimal("0.05");
        BigDecimal b2 = new BigDecimal("0.01");
        BigDecimal b3 = new BigDecimal("1.0");
        BigDecimal b4 = new BigDecimal("0.42");
        BigDecimal b5 = new BigDecimal("4.015");
        BigDecimal b6 = new BigDecimal("100");
        BigDecimal b7 = new BigDecimal("123.3");
        System.out.println(b1.add(b2));
        System.out.println(b3.subtract(b4));
        System.out.println(b5.multiply(b6));
        System.out.println(b7.divide(b6));
    }

}
