package com.kyobo.platform.donots.model.entity;

public enum Status {

    I("진행중"),
    F("완료");
    public String name;

    Status(String name) {
        this.name = name;
    }
}
