package com.zxj.appstartup;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread t1 = new Thread(){
            @Override
            public void run() {
                System.out.println("t1:第一步执行完成!");
                System.out.println("t1:第二步执行完成!");
                countDownLatch.countDown();
                System.out.println("t1:第三步执行完成!");
            }
        };
        Thread t2 = new Thread(){
            @Override
            public void run() {
                System.out.println("t2:第一步执行完成!");
                System.out.println("t2:第二步执行完成!");
                countDownLatch.countDown();
                System.out.println("t2:第三步执行完成!");
            }
        };
        Thread t3 = new Thread(){
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("执行第三个线程任务!");
            }
        };
        t3.start();
        t2.start();
        t1.start();
    }
}