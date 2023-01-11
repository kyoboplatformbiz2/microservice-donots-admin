package com.kyobo.platform.donots.model.entity;

public enum FaqCategory {
    NEW_FEATURE("신규기능"),
    SIGN_IN("로그인"),
    RECIPE_POST("레시피"),
    MISCELLANY("기타");

    public String name;

    FaqCategory(String name) {
        this.name = name;
    }
}
