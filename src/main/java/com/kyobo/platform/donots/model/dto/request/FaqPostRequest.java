package com.kyobo.platform.donots.model.dto.request;

import com.kyobo.platform.donots.model.entity.FaqCategory;
import com.kyobo.platform.donots.model.entity.FaqPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class FaqPostRequest {

    private Long key;
    private FaqCategory faqCategory;
    private String question;
    private String answer;
    private String representativeImgUrl;
    private LocalDateTime createdDatetime;
    private LocalDateTime lastModifiedDatetime;

    public FaqPostRequest from(FaqPost faqPost) {
        return FaqPostRequest.builder()
                .key(faqPost.getKey())
                .faqCategory(faqPost.getFaqCategory())
                .question(faqPost.getQuestion())
                .answer(faqPost.getAnswer())
                .representativeImgUrl(faqPost.getRepresentativeImgUrl())
                .createdDatetime(faqPost.getCreatedDatetime())
                .lastModifiedDatetime(faqPost.getLastModifiedDatetime())
                .build();
    }

    public FaqPost toEntity() {
        return FaqPost.builder()
                .key(key)
                .faqCategory(faqCategory)
                .question(question)
                .answer(answer)
                .representativeImgUrl(representativeImgUrl)
                .createdDatetime(createdDatetime)
                .lastModifiedDatetime(lastModifiedDatetime)
                .build();
    }
}
