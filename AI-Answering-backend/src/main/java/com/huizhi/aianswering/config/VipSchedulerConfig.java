package com.huizhi.aianswering.config;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟创建 VIP 用户的专属线程池，与原来的 Schedulers.io(）线程池隔离
 */
@Configuration
@Data
public class VipSchedulerConfig {

    @Bean
    public Scheduler vipScheduler() {
        ThreadFactory threadFactory = new ThreadFactory() {
            // 使用并发计数器标记线程编号
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(@NotNull Runnable r) {
                // 自定义线程池中线程名称
                Thread thread = new Thread(r, "VIPThreadPool-" + threadNumber.getAndIncrement());
                // 非守护线程
                thread.setDaemon(false);
                return thread;
            }
        };
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10, threadFactory);
        return Schedulers.from(scheduledExecutorService);
    }
}
