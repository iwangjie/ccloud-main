package com.ccloud.main.config.aop;

import com.ccloud.main.mapper.BusinessRequestLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SystemLogConsumerRunner implements CommandLineRunner {

    @Autowired
    private BusinessRequestLogMapper systemLogQueueCustomer;

    @Override
    public void run(String... args) throws Exception {
        SystemLogQueueListener listener = new SystemLogQueueListener(systemLogQueueCustomer);
        new Thread(listener).start();
    }
}