package com.civip.csyy.kafka.handler;

import com.civip.csyy.annotation.KafkaMsgHandler;
import com.civip.csyy.common.enums.MsgTypeEnum;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/14
 */
@Component
public class KafkaMsgHandlerDispatcher implements ApplicationContextAware {
    private final Map<String, AbstractKafkaMsgHandler> handlerMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(KafkaMsgHandler.class);
        if (!beansWithAnnotation.isEmpty()) {
            for (Object value : beansWithAnnotation.values()) {
                Class<?> targetClass = AopUtils.getTargetClass(value);
                KafkaMsgHandler annotation = targetClass.getAnnotation(KafkaMsgHandler.class);
                MsgTypeEnum msgTypeEnum = annotation.msgType();
                if (!Objects.isNull(msgTypeEnum)) {
                    AbstractKafkaMsgHandler handler = (AbstractKafkaMsgHandler) value;
                    handlerMap.put(msgTypeEnum.getCode(), handler);
                }
            }
        }
    }

    public AbstractKafkaMsgHandler getHandler(String key) {
        return this.handlerMap.get(key);
    }
}
