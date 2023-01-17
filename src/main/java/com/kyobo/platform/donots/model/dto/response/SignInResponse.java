package com.kyobo.platform.donots.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SignInResponse {

    @Schema(description = "Donots Admin 회원 고유 번호")
    private Long id;

    @Schema(description = "admin 회원 id")
    private String adminId;

    @Schema(description = "admin 권한")
    private String role;

    @Schema(description = "3개월 패스워드 변경 flag")
    private Boolean isPasswordChangeFlag;

}
