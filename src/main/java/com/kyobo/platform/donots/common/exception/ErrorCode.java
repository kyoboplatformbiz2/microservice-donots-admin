package com.kyobo.platform.donots.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    ALREADY_REGISTERED_ID(1000, "이미 가입된 아이디입니다"),
    VALID_PARAMETER(4000, "파라메터 인자값이 정상적이지 않습니다."),
    DATA_NOT_FOUND(4001, "조회된 데이터가 없습니다."),
    ADMIN_USER_NOT_FOUND(4002, "조회된 어드민 유저가 없습니다." ),

    PASSWORD_NOT_MATCH(5000, "패스워드가 맞지 않습니다."),
    DEFAULT(9999, "정의되지 않은 에러");

    public final int status;
    public final String message;
}
