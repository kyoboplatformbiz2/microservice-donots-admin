package com.kyobo.platform.donots.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;


@Getter
public class NoticeRequest {
    public NoticeRequest(){

    }

    @Schema(description = "제목")
    @NotNull
    private String title;
    
    @Schema(description = "본문")
    @NotNull
    private String body;
    
    @Schema(description = "이미지 주소")
    private String imgUrl;
}
