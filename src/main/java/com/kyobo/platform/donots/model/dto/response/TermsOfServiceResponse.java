package com.kyobo.platform.donots.model.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyobo.platform.donots.model.entity.TermsOfService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TermsOfServiceResponse {

    @Schema(description = "키")
    private Long key;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "본문")
    private String body;

    @Schema(description = "버전")
    private String version;

    @Schema(description = "등록자 ID")
    private String adminId;

    @Schema(description = "게시시작일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postingStartDatetime;

    @Schema(description = "게시종료일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postingEndDatetime;

    @Schema(description = "작성일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDatetime;

    @Schema(description = "최종수정일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDatetime;

    public TermsOfServiceResponse(){}

    public TermsOfServiceResponse(TermsOfService termsOfService){
        this.key = termsOfService.getKey();
        this.title = termsOfService.getTitle();
        this.body = termsOfService.getBody();
        this.version = termsOfService.getVersion();
        this.adminId = termsOfService.getAdminId();
        this.postingStartDatetime = termsOfService.getPostingStartDatetime();
        this.postingEndDatetime = termsOfService.getPostingEndDatetime();
        this.createdDatetime = termsOfService.getCreatedDatetime();
        this.lastModifiedDatetime = termsOfService.getLastModifiedDatetime();
    }
}
