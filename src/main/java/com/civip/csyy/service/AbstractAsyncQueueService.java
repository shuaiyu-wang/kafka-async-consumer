package com.civip.csyy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/15
 */
public abstract class AbstractAsyncQueueService<T> implements CommandLineRunner, DisposableBean {

    private final static Logger logger = LoggerFactory.getLogger(AbstractAsyncQueueService.class);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private int batchSize = 400;
    private int batchIntervalOfMs = 10000;
    private final ArrayBlockingQueue<T> queue = new ArrayBlockingQueue<>(batchSize);
    private volatile boolean running = true;
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    public AbstractAsyncQueueService() {
    }

    public AbstractAsyncQueueService(int batchSize, int batchIntervalOfMs) {
        this.batchSize = batchSize;
        this.batchIntervalOfMs = batchIntervalOfMs;
    }

    private void consume() {
        try {
            long start = System.currentTimeMillis();
            while (running) {
                if (queue.size()>=batchSize) {
                    handle(batchSize);
                    start = System.currentTimeMillis();
                } else if (queue.size()>0 && (System.currentTimeMillis()-start)>=batchIntervalOfMs){
                    handle(queue.size());
                    start = System.currentTimeMillis();
                } else {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        logger.error("内存队列 消费线程的sleep被中断了，剩余未处理数据量：{}", queue.size());
                    }
                }
            }
        } finally {
            // 关闭前处理掉剩余数据
            handle(queue.size());
            countDownLatch.countDown();
            logger.info("内存队列 消费线程安全关闭。");
        }
    }

    public void put(T t) {
        if (!queue.offer(t)) {
            logger.warn("内存队列已满！");
        }
    }

    private void handle(int size) {
        ArrayList<T> ts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ts.add(queue.poll());
        }
        try {
            doHandle(ts);
        } catch (Exception e) {
            logger.info("内存队列 消费线程数据处理发生异常", e);
        }
    }

    protected abstract void doHandle(ArrayList<T> ts) throws Exception;

    @Override
    public void run(String[] args) {
        executorService.execute(()-> {
            try {
                consume();
            } catch (Exception e) {
                logger.error("内存队列 消费程序发生未知异常导致中断, 退出程序", e);
                System.exit(1);
            }
        });
    }

    @Override
    public void destroy() throws Exception {
        this.running = false;
        countDownLatch.await();
        executorService.shutdownNow();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }
}
