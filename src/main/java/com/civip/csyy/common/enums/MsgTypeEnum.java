package com.civip.csyy.common.enums;

import com.civip.csyy.common.constant.KafkaConstant;

import java.util.Arrays;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/14
 */
public enum MsgTypeEnum {

    LOW_SPEED_VEHICLE(KafkaConstant.KEY_LOW_SPEED_VEHICLE, "城市运营低速车")
    ;

    private String code;

    private String desc;

    MsgTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static MsgTypeEnum of(String code) {
        return Arrays.stream(MsgTypeEnum.values()).filter(t -> t.getCode().equals(code)).findFirst().orElse(null);
    }

}
