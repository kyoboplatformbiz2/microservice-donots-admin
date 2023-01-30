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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime createdDatetime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime lastModifiedDatetime;
    public void updateFaqPost(FaqPostRequest faqPostRequest) {
        LocalDateTime now = LocalDateTime.now();
        this.faqCategory = faqPostRequest.getFaqCategory();
        this.question = faqPostRequest.getQuestion();
        this.answer = faqPostRequest.getAnswer();
        this.representativeImgUrl = faqPostRequest.getRepresentativeImgUrl();
        this.lastModifiedDatetime = now;
    }
}
