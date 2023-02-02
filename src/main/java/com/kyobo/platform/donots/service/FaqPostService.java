package com.kyobo.platform.donots.service;

import com.kyobo.platform.donots.model.dto.request.FaqPostRequest;
import com.kyobo.platform.donots.model.dto.response.FaqPostListResponse;
import com.kyobo.platform.donots.model.dto.response.FaqPostResponse;
import com.kyobo.platform.donots.model.entity.FaqPost;
import com.kyobo.platform.donots.model.repository.FaqPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    public FaqPostListResponse findAllFaqPostSummaries(Pageable pageable) {

        Page<FaqPost> pageFaqPost = faqPostRepository.findAllByOrderByCreatedDatetimeDesc(pageable);

        List<FaqPostResponse> faqPostResponseList = pageFaqPost.getContent().stream()
                .map(m-> new FaqPostResponse(m))
                .collect(Collectors.toList());
        FaqPostListResponse response = new FaqPostListResponse(faqPostResponseList, pageFaqPost.getTotalPages(), pageFaqPost.getTotalElements());

        return response;
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
                // TODO [Session] 세션 연동 필요
                .adminId("dummyAdminId")
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
