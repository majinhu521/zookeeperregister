package com.majinhu.order.pool.ThreadPoolTaskExecutor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName BenchMarkTest
 * @Description
 * @Author majinhu
 * @Date 2020/3/5 10:38
 * @Version 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BenchMarkTest {
    private static final int threadnum = 200;

    private CountDownLatch cdl = new CountDownLatch(threadnum);

    long timed = 0L;

    @Before
    public void start() {
        System.out.println("开始测试");
        timed = System.currentTimeMillis();
    }

    @After
    public void end() {
        System.out.println("结束测试，执行时长" + (System.currentTimeMillis() - timed));

    }

    @Test
    public void benchmark() throws InterruptedException {
        Thread[] theads = new Thread[threadnum];
        for (int i = 0; i < threadnum; i++) {
            Thread thread = new Thread(new QueryRequest());
            theads[i] = thread;
            thread.start();
            cdl.countDown();
        }
        for (Thread thread : theads) {
            thread.join();
        }
    }

    private class QueryRequest implements Runnable {

        @Override
        public void run() {
            try{
                cdl.await();
            }catch (Exception e){
                e.printStackTrace();
            }
            //TODO 执行业务方法 service.do();

            Random random = new Random();
            int pos = random.nextInt(3000);
            try {
                //睡眠几毫秒钟
                Thread.sleep(pos);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(pos);

        }
    }


}
