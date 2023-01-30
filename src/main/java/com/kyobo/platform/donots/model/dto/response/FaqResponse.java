package com.kyobo.platform.donots.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyobo.platform.donots.model.entity.FaqCategory;
import com.kyobo.platform.donots.model.entity.FaqPost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @Schema(description = "작성일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime createdDatetime;

    @Schema(description = "최종수정일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime lastModifiedDatetime;

    public FaqResponse(FaqPost faqPost){
        this.key = faqPost.getKey();
        this.faqCategory = faqPost.getFaqCategory();
        this.answer = faqPost.getAnswer();
        this.question = faqPost.getQuestion();
        this.representativeImgUrl = faqPost.getRepresentativeImgUrl();
        this.createdDatetime = faqPost.getCreatedDatetime();
        this.lastModifiedDatetime = faqPost.getLastModifiedDatetime();
    }
}
