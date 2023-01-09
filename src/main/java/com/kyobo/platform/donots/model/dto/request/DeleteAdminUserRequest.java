package com.kyobo.platform.donots.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class DeleteAdminUserRequest {
    public DeleteAdminUserRequest(){}

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{4,20}$", message = "아이디는 특수문자를 제외한 4~20자리여야 합니다.")
    @Schema(description = "아이디")
    private String adminId;

}
