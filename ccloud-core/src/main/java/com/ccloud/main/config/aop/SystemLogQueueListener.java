package com.ccloud.main.config.aop;


import com.ccloud.main.entity.BusinessRequestLog;
import com.ccloud.main.mapper.BusinessRequestLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SystemLogQueueListener implements Runnable {

    // 用来存放从队列拿出的数据
    private List<BusinessRequestLog> queueDataList;

    // 回调接口
    @Autowired
    private BusinessRequestLogMapper process;

    public SystemLogQueueListener(BusinessRequestLogMapper process) {
        this.process = process;
        queueDataList = new ArrayList<>(SystemLogQueue.DEFAULT_HANDLE_SIZE);
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        BusinessRequestLog t = null;
        while (true) {
            try {
                SystemLogQueue systemLogQueue = SystemLogQueue.getInstance();
                // 从队列拿出队列头部的元素，如果没有就阻塞
                t = systemLogQueue.poll();
                if (null != t) {
                    queueDataList.add(t);
                }
                if (queueDataList.size() >= SystemLogQueue.DEFAULT_HANDLE_SIZE) {
                    log.info("队列数据超过默认处理长度，开始消费,size：{},queueSize：{}", queueDataList.size(), systemLogQueue.size());
                    startTime = batchProcess(queueDataList);
                    continue;
                }
                long currentTime = System.currentTimeMillis();
                if (currentTime - startTime > SystemLogQueue.DEFAULT_HANDLE_TIME) {
                    log.info("超过队列默认处理间隔时间，开始消费,defaultTime：{},handleTime：{},size：{},queueSize：{}",
                            SystemLogQueue.DEFAULT_HANDLE_TIME, currentTime - startTime, queueDataList.size(), systemLogQueue.size());
                    startTime = batchProcess(queueDataList);
                    continue;
                }
                log.info("未满足队列批量处理条件继续等待,defaultTime：{},handleTime：{},size：{},queueSize：{}",
                        SystemLogQueue.DEFAULT_HANDLE_TIME, currentTime - startTime, queueDataList.size(), systemLogQueue.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 批量处理队列数据
     *
     * @param queueDataList
     * @return
     */
    private long batchProcess(List<BusinessRequestLog> queueDataList) {
        // 处理队列
        try {
            log.info("批量日志消费开始,size：{}", queueDataList.size());
            process.processData(queueDataList);
            log.info("批量日志消费结束,size：{}", queueDataList.size());
        } catch (Exception e) {
            log.info("批量日志消费异常,errorMsg：{},exception：{}", e.getMessage(), e);
        } finally {
            // 清理掉dataList中的元素
            this.clearQueueDataList();
        }
        return System.currentTimeMillis();
    }

    /**
     * 清理生成的list
     */
    public void clearQueueDataList() {
        queueDataList = null;
        queueDataList = new ArrayList<>();
    }
}