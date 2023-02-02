package com.kyobo.platform.donots.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyobo.platform.donots.model.entity.FaqCategory;
import com.kyobo.platform.donots.model.entity.FaqPost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class FaqPostResponse {

    @Schema(description = "FAQ 키")
    private Long key;

    @Schema(description = "유형")
    private FaqCategory faqCategory;

    @Schema(description = "질문")
    private String question;

    @Schema(description = "답변")
    private String answer;

    @Schema(description = "대표이미지 URL")
    private String representativeImgUrl;

    @Schema(description = "게시시작일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime boardStartDatetime;

    @Schema(description = "게시종료일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime boardEndDatetime;

    @Schema(description = "작성자")
    private String adminId;

    @Schema(description = "작성일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createdDatetime;

    @Schema(description = "최종수정일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime lastModifiedDatetime;

    public FaqPostResponse(FaqPost faqPost){
        this.key = faqPost.getKey();
        this.faqCategory = faqPost.getFaqCategory();
        this.question = faqPost.getQuestion();
        this.answer = faqPost.getAnswer();
        this.representativeImgUrl = faqPost.getRepresentativeImgUrl();
        this.boardStartDatetime = faqPost.getBoardStartDatetime();
        this.boardEndDatetime = faqPost.getBoardEndDatetime();
        this.adminId = faqPost.getAdminId();
        this.createdDatetime = faqPost.getCreatedDatetime();
        this.lastModifiedDatetime = faqPost.getLastModifiedDatetime();
    }
}
