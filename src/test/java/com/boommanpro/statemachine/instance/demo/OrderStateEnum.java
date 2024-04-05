package com.boommanpro.statemachine.instance.demo;

import lombok.Getter;

import lombok.Getter;

@Getter
public enum OrderStateEnum {
    START("开始", "START"),
    END("结束", "END"),
    CREATE("创建", "CREATE"),
    APPROVE("审批", "APPROVE");

    private final String text;
    private final String code;

    OrderStateEnum(String text, String code) {
        this.text = text;
        this.code = code;
    }

    public static OrderStateEnum fromCode(String code) {
        for (OrderStateEnum state : values()) {
            if (state.getCode().equals(code)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Invalid order state code: " + code);
    }
}
