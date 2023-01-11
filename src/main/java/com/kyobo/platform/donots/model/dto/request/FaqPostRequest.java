package com.kyobo.platform.donots.model.dto.request;

import com.kyobo.platform.donots.model.entity.FaqCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class FaqPostRequest {

    public FaqPostRequest(){}

    @Schema(description = "카테고리")
    @NotNull
    private FaqCategory faqCategory;

    @Schema(description = "질문")
    @NotNull
    private String question;

    @Schema(description = "답변")
    @NotNull
    private String answer;

    @Schema(description = "이미지 url")
    private String representativeImgUrl;

}
