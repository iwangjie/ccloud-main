package com.ccloud.main.config.aop;

import com.ccloud.main.mapper.BusinessRequestLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 启动
 *
 * @author 杨航
 */

@Component
public class SystemLogConsumerRunner implements CommandLineRunner {

    @Autowired
    private BusinessRequestLogMapper businessRequestLogMapper;

    @Override
    public void run(String... args) throws Exception {
        SystemLogQueueListener listener = new SystemLogQueueListener(businessRequestLogMapper);
        new Thread(listener).start();
    }
}