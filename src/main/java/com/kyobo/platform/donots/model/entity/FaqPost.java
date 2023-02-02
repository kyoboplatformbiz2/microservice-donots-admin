package com.kyobo.platform.donots.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyobo.platform.donots.model.dto.request.FaqPostRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "FAQ_POST")
public class FaqPost {

    @Id
    @GeneratedValue
    @Column(name = "faq_post_key")
    private Long key;

    private FaqCategory faqCategory;
    private String question;
    private String answer;
    private String representativeImgUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime boardStartDatetime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime boardEndDatetime;
    private String adminId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createdDatetime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime lastModifiedDatetime;

    public void updateFaqPost(FaqPostRequest faqPostRequest) {
        this.faqCategory = faqPostRequest.getFaqCategory();
        this.question = faqPostRequest.getQuestion();
        this.answer = faqPostRequest.getAnswer();
        this.representativeImgUrl = faqPostRequest.getRepresentativeImgUrl();
        this.boardStartDatetime = faqPostRequest.getBoardStartDatetime();
        this.boardEndDatetime = faqPostRequest.getBoardEndDatetime();
        this.lastModifiedDatetime = LocalDateTime.now();
    }
}
