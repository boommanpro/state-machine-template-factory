package com.boommanpro.statemachine.instance.demo;

import lombok.Getter;

@Getter
public enum OrderEventEnum {
    INIT("初始化", "INIT"),
    NEXT("继续", "NEXT"),
    FINISHED("停止", "FINISHED");

    private final String text;
    private final String code;

    OrderEventEnum(String text, String code) {
        this.text = text;
        this.code = code;
    }



    public static OrderEventEnum fromCode(String code) {
        for (OrderEventEnum event : values()) {
            if (event.getCode().equals(code)) {
                return event;
            }
        }
        throw new IllegalArgumentException("Invalid order event code: " + code);
    }

    @Override
    public String toString() {
        return this.text;
    }
}
