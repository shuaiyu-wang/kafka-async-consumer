package com.civip.csyy.common.config;

import com.civip.csyy.processor.VehicleLocAsyncQueueBatchProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/19
 */
@Configuration
public class CustomConfig {
    @Bean
    public VehicleLocAsyncQueueBatchProcessor vehicleLocProcessor() {
        VehicleLocAsyncQueueBatchProcessor processor = new VehicleLocAsyncQueueBatchProcessor();
        processor.start();
        return processor;
    }
}
