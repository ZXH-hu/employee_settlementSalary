package com.huizhi.aianswering;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RxJavaTest {

    @Test
    public void test() throws InterruptedException {
        // 创建数据流，每隔一秒中产生一条数据
        Flowable<Long> flowable = Flowable.interval(1, TimeUnit.SECONDS)
                .map(i -> i + 1)
                // 指定执行操作用的线程池
                .subscribeOn(Schedulers.io());

        // 订阅数据流，并输出打印的数字
        flowable.observeOn(Schedulers.io())
                .doOnNext(item -> System.out.println(item.toString()))
                .subscribe();

        // 阻塞主线程，以便观察输出
        Thread.sleep(10000l);
    }
}
