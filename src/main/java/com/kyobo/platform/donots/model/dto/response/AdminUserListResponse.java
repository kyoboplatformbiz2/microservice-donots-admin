package com.kyobo.platform.donots.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class AdminUserListResponse {
    public AdminUserListResponse(){
    }

    @Schema(description = "어드민 유저 리스트")
    private List<AdminUserResponse> adminUserListResponse;

    public AdminUserListResponse(List<AdminUserResponse> adminUserListResponse){
        this.adminUserListResponse = adminUserListResponse;
    }

}
