package com.kyobo.platform.donots.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
public class NoticeRequest {
    public NoticeRequest(){

    }

    @Schema(description = "제목")
    @NotBlank
    private String title;
    
    @Schema(description = "본문")
    @NotBlank
    private String body;
    
    @Schema(description = "이미지 주소")
    private String imgUrl;

    @Schema(description = "게시 시작")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime boardStartDate;

    @Schema(description = "게시 종료")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime boardEndDate;
}
