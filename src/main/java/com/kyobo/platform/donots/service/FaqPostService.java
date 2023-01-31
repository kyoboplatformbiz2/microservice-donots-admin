package com.kyobo.platform.donots.service;

import com.kyobo.platform.donots.model.dto.request.FaqPostRequest;
import com.kyobo.platform.donots.model.dto.response.FaqPostResponse;
import com.kyobo.platform.donots.model.entity.FaqPost;
import com.kyobo.platform.donots.model.repository.FaqPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FaqPostService {
    private final FaqPostRepository faqPostRepository;


    public List<FaqPostResponse> findAllFaqPostSummaries() {
        return faqPostRepository.findAllByOrderByCreatedDatetimeDesc().stream()
                .map(m-> new FaqPostResponse(m))
                .collect(Collectors.toList());
    }

    public FaqPostResponse findFaqPostDetailsByKey(Long key) {
        FaqPost faqPost = faqPostRepository.findById(key).get();
        return new FaqPostResponse(faqPost);
    }

    @Transactional
    public Long registerFaqPost(FaqPostRequest faqPostRequest) {
        LocalDateTime now = LocalDateTime.now();
        FaqPost faqPost = FaqPost.builder()
                .faqCategory(faqPostRequest.getFaqCategory())
                .question(faqPostRequest.getQuestion())
                .answer(faqPostRequest.getAnswer())
                .representativeImgUrl(faqPostRequest.getRepresentativeImgUrl())
                .boardStartDatetime(faqPostRequest.getBoardStartDatetime())
                .boardEndDatetime(faqPostRequest.getBoardEndDatetime())
                .lastModifiedDatetime(now)
                .createdDatetime(now)
                .build();
        faqPost = faqPostRepository.saveAndFlush(faqPost);

        return faqPost.getKey();
    }

    @Transactional
    public void modifyFaqPost(Long key, FaqPostRequest faqPostRequest) {
        FaqPost faqPost = faqPostRepository.findById(key).get();
        faqPost.updateFaqPost(faqPostRequest);
    }

    @Transactional
    public void deleteFaqPost(Long key) {
        faqPostRepository.deleteById(key);
    }
}
