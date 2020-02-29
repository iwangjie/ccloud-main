package com.ccloud.main.config.aop;


import com.ccloud.main.entity.BusinessRequestLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Slf4j
@Component
public class SystemLogQueue {


    // 队列大小
    public static int QUEUE_MAX_SIZE;
    // 默认队列处理长度
    public static int DEFAULT_HANDLE_SIZE;
    // 默认间隔处理队列时间
    public static int DEFAULT_HANDLE_TIME;
    // 阻塞队列
    private static BlockingQueue<BusinessRequestLog> blockingQueue;
    private final static SystemLogQueue systemLogQueue = new SystemLogQueue();


    @Value("${system.log.queue.maxsize}")
    private void setQueueMaxSize(int maxsize) {
        QUEUE_MAX_SIZE = maxsize;
        blockingQueue = new ArrayBlockingQueue<>(QUEUE_MAX_SIZE);
    }

    @Value("${system.log.queue.default.handle.size}")
    private void setDefaultHandleSize(int handleSize) {
        DEFAULT_HANDLE_SIZE = handleSize;
    }

    @Value("${system.log.queue.default.handle.time}")
    private void setDefaultHandleTime(int handleTime) {
        DEFAULT_HANDLE_TIME = handleTime;
    }

    private SystemLogQueue() {

    }

    public static SystemLogQueue getInstance() {
        return systemLogQueue;
    }

    /**
     * 消息入队
     *
     * @param businessRequestLog
     * @return
     */
    public boolean push(BusinessRequestLog businessRequestLog) {
        return SystemLogQueue.blockingQueue.add(businessRequestLog);
    }

    /**
     * 消息出队
     *
     * @return
     */
    public BusinessRequestLog poll() {
        BusinessRequestLog result = null;
        try {
            result = SystemLogQueue.blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取队列大小
     *
     * @return
     */
    public int size() {
        return SystemLogQueue.blockingQueue.size();
    }
}