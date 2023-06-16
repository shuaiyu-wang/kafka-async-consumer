package com.civip.csyy.kafka.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/14
 */
public abstract class AbstractKafkaMsgHandler {

    private final static Logger logger = LoggerFactory.getLogger(AbstractKafkaMsgHandler.class);

    public void handle(String msg) {
        try {
            doHandle(msg);
        } catch (Exception e) {
            logger.error("kafka 消息处理失败，消息：{}", msg);
        }
    }

    protected abstract void doHandle(String msg) throws Exception;
}
