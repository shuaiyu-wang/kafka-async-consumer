package com.civip.csyy.annotation;

import com.civip.csyy.common.enums.MsgTypeEnum;

import java.lang.annotation.*;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/14
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KafkaMsgHandler {
    String name() default "";
    MsgTypeEnum msgType();
}
