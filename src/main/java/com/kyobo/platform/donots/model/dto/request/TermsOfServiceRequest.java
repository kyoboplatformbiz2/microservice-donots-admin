package com.kyobo.platform.donots.model.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
public class TermsOfServiceRequest {

    public TermsOfServiceRequest(){}

    @Schema(description = "제목")
    @NotBlank
    private String title;

    @Schema(description = "본문")
    @NotBlank
    private String body;

    @Schema(description = "버전")
    @NotBlank
    private String version;

    @Schema(description = "등록자 ID")
    @NotBlank
    private String adminId;

    @Schema(description = "HTML 파일 URL")
    @NotBlank
    private String bodyHtmlFileUrl;

    @Schema(description = "게시 시작")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime postingStartDatetime;

    @Schema(description = "게시 종료")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime postingEndDatetime;
}
