package com.majinhu.order.pool.ThreadPoolTaskExecutor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ClassName ApplicationTests
 * @Description @EnableAsync 必须加到启动类里面，否则是同步执行。
 * @Author majinhu
 * @Date 2020/3/5 10:07
 * @Version 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private Task task;

    @Test
    public void test() throws Exception {

        task.doTaskOne();
        task.doTaskTwo();
        task.doTaskThree();

        Thread.currentThread().join();
    }

}