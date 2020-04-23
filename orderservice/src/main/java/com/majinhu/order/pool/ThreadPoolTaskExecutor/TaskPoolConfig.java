package com.majinhu.order.pool.ThreadPoolTaskExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName TaskPoolConfig
 * @Description
 * 通过使用ThreadPoolTaskExecutor创建了一个线程池，同时设置了以下这些参数：
 *
 * 核心线程数10：线程池创建时候初始化的线程数
 * 最大线程数20：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
 * 缓冲队列200：用来缓冲执行任务的队列
 * 允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
 * 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
 * 线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
 *
 * 作者：程序猿DD
 * 链接：https://www.jianshu.com/p/74f00fb7e758
 * 来源：简书
 * @Author majinhu
 * @Date 2020/3/5 10:04
 * @Version 1.0
 **/
@Component
public class TaskPoolConfig {
    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
