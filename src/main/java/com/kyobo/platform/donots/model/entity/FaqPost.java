package com.kyobo.platform.donots.model.entity;

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
    private LocalDateTime createdDatetime;
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
