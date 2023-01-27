package com.kyobo.platform.donots.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class ChangePasswordRequest {
    public ChangePasswordRequest(){}

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "아이디는 영문자(대,소문자) 숫자로 5~20자리이어야 합니다.")
    @Schema(description = "아이디")
    private String adminId;

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}", message = "비밀번호는 8~15자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @Schema(description = "현재 비밀번호")
    private String password;

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}", message = "비밀번호는 8~15자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @Schema(description = "새 비밀번호")
    private String newPassword;
}
