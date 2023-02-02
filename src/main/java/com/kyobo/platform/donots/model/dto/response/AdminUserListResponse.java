package com.kyobo.platform.donots.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
public class AdminUserListResponse {
    public AdminUserListResponse(){
    }

    @Schema(description = "어드민 유저 리스트")
    private List<AdminUserResponse> adminUserListResponse;

    @Schema(description = "총 페이지")
    private int totalPage;

    @Schema(description = "게시물수")
    private Long totalElements;

    public AdminUserListResponse(List<AdminUserResponse> adminUserListResponse, int totalPage, long totalElements){
        this.adminUserListResponse = adminUserListResponse;
        this.totalElements = totalElements;
        this.totalPage = totalPage;
    }

}
