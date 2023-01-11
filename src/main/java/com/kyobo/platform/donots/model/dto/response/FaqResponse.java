package com.kyobo.platform.donots.model.dto.response;

import com.kyobo.platform.donots.model.entity.FaqCategory;
import com.kyobo.platform.donots.model.entity.FaqPost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class FaqResponse {

    @Schema(description = "게시물 번호")
    private Long key;

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

    public FaqResponse(FaqPost faqPost){
        this.key = faqPost.getKey();
        this.faqCategory = faqPost.getFaqCategory();
        this.answer = faqPost.getAnswer();
        this.question = faqPost.getQuestion();
        this.representativeImgUrl = faqPost.getRepresentativeImgUrl();
    }
}
