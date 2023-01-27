package com.kyobo.platform.donots.model.entity;

import com.kyobo.platform.donots.model.dto.request.FaqPostRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Data
@Table(name = "FAQ_POST")
public class FaqPost {

    @Id
    @Column(name = "faq_post_key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;

    @Column(name = "FAQ_CATEGORY", nullable = false)
    private FaqCategory faqCategory;

    @Column(name = "QUESTION", nullable = false)
    private String question;
    @Column(name = "ANSWER", nullable = false)
    private String answer;
    @Column(name = "REPRESENTATIVE_IMG_URL", nullable = false)
    private String representativeImgUrl;

    @Column(name = "CREATED_DATETIME", nullable = false)
    private LocalDateTime createdDatetime;
    @Column(name = "LAST_MODIFIED_DATETIME", nullable = false)
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

