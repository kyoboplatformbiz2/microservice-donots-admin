package com.kyobo.platform.donots.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyobo.platform.donots.model.entity.QnA;
import com.kyobo.platform.donots.model.entity.QnACategory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
public class QnAResponse {
    public QnAResponse(){

    }

    @Schema(description = "QnA 번호")
    private Long key;

    @Enumerated(EnumType.STRING)
    @Schema(description = "QnA 카테고리")
    private QnACategory qnACategory;

    @Schema(description = "타이틀")
    private String title;

    @Schema(description = "질문")
    private String question;

    @Schema(description = "답변")
    private String answer;

    @Schema(description = "첨부파일 image url")
    private String imageUrl;

    @Schema(description = "등록일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openDate;

    @Schema(description = "답변일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closeDate;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "상태")
    private String status;

    public QnAResponse(QnA qna){
        this.qnACategory = qna.getQnACategory();
        this.title = qna.getTitle();
        this.question = qna.getQuestion();
        this.answer = qna.getAnswer();
        this.key = qna.getKey();
        this.email = qna.getEmail();
        this.closeDate = qna.getCloseDate();
        this.openDate = qna.getOpenDate();
        this.status = qna.getStatus();
        this.imageUrl = qna.getImageUrl();
    }
}
