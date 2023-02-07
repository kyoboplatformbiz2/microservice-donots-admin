package com.kyobo.platform.donots.model.entity.service.parent;

public enum ParentType {
    GUEST("게스트", "http://test.com/test"),
    NORMAL_MEMBER("정회원", "http://test.com/test"),
    EXPERT("전문가", "http://test.com/test");

    public final String name;
    public final String badgeImgUrl;

    ParentType(String name, String badgeImgUrl) {
        this.name = name;
        this.badgeImgUrl = badgeImgUrl;
    }

}
