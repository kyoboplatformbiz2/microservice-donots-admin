package com.kyobo.platform.donots.model.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
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

    public void deepCopyAllExceptKeyFrom(FaqPost faqPost) {
        // 값이 없는 필드가 변경되는 것은 의도에 맞지 않으므로 값이 있는 필드만 복사한다
        // TODO Later Reflection 사용한 동적 null 검사
        if (faqPost.getFaqCategory() != null) this.faqCategory = faqPost.getFaqCategory();
        if (faqPost.getQuestion() != null) this.question = faqPost.getQuestion();
        if (faqPost.getAnswer() != null) this.answer = faqPost.getAnswer();
        if (faqPost.getRepresentativeImgUrl() != null) this.representativeImgUrl = faqPost.getRepresentativeImgUrl();
        if (faqPost.getCreatedDatetime() != null) this.createdDatetime = faqPost.getCreatedDatetime();
        if (faqPost.getLastModifiedDatetime() != null) this.lastModifiedDatetime = faqPost.getLastModifiedDatetime();
    }
}
