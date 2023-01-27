package com.kyobo.platform.donots.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class DeleteAdminUserRequest {
    public DeleteAdminUserRequest(){}

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "아이디는 영문자(대,소문자) 숫자로 5~20자리이어야 합니다.")
    @Schema(description = "아이디")
    private String adminId;

}
