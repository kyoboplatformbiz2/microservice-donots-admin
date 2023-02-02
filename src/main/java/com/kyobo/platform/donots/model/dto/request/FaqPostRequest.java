package com.kyobo.platform.donots.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyobo.platform.donots.model.entity.FaqCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class FaqPostRequest {

    public FaqPostRequest(){}

    @Schema(description = "카테고리")
    @NotNull
    private FaqCategory faqCategory;

    @Schema(description = "질문")
    @NotBlank
    private String question;

    @Schema(description = "답변")
    @NotBlank
    private String answer;

    @Schema(description = "이미지 url")
    private String representativeImgUrl;

    @Schema(description = "게시 시작")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime boardStartDatetime;

    @Schema(description = "게시 종료")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime boardEndDatetime;
}
