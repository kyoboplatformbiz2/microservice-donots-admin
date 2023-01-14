package com.kyobo.platform.donots.model.dto.request;

import com.kyobo.platform.donots.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class ModifyAdminUserRequest {

    public ModifyAdminUserRequest(){}

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{4,20}$", message = "아이디는 특수문자를 제외한 4~20자리여야 합니다.")
    @Schema(description = "아이디")
    private String adminId;

    @NotBlank
    @Schema(description = "어드민 유저 이름")
    private String adminUserName;

    @NotBlank
    @Schema(description = "어드민 사원 번호")
    private String adminUserNumber;

    @NotBlank
    @Schema(description = "부서 이름")
    private String departmentName;

    @NotBlank
    @Schema(description = "핸드폰 번호")
    private String phoneNumber;

    @NotBlank
    @Schema(description = "권한(SUPER_ADMIN, ADMIN)")
    @com.kyobo.platform.donots.model.annotation.Enum(enumClass = Role.class, ignoreCase = true)
    private String role;

    @NotBlank
    @Email
    @Schema(description = "e-mail")
    private String email;

    @NotBlank
    @Schema(description = "권한 부여 사유")
    private String reasonsForAuthorization;

    @NotBlank
    @Schema(description = "등록한 관리자 ID")
    private String regeditAdminId;
    public void setRole(String role) {
        this.role = role;
    }

}