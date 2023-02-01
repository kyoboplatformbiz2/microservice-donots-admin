package com.kyobo.platform.donots.service;

import com.amazonaws.util.CollectionUtils;
import com.kyobo.platform.donots.common.exception.TermsOfServiceNotFoundException;
import com.kyobo.platform.donots.model.dto.request.TermsOfServiceRequest;
import com.kyobo.platform.donots.model.dto.response.FaqPostResponse;
import com.kyobo.platform.donots.model.dto.response.TermsOfServiceResponse;
import com.kyobo.platform.donots.model.entity.FaqPost;
import com.kyobo.platform.donots.model.entity.TermsOfService;
import com.kyobo.platform.donots.model.repository.TermsOfServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TermsOfServiceService {
    private final TermsOfServiceRepository termsOfServiceRepository;

    private final EntityManager em;

    @Transactional
    public Long postTermsOfService(TermsOfServiceRequest termsOfServiceRequest) {
        LocalDateTime now = LocalDateTime.now();
        TermsOfService termsOfService = TermsOfService.builder()
                .title(termsOfServiceRequest.getTitle())
                .body(termsOfServiceRequest.getBody())
                .version(termsOfServiceRequest.getVersion())
                .adminId(termsOfServiceRequest.getAdminId())
                .postingStartDatetime(termsOfServiceRequest.getPostingStartDatetime())
                .postingEndDatetime(termsOfServiceRequest.getPostingEndDatetime())
                .createdDatetime(now)
                .lastModifiedDatetime(now)
                .build();

        em.persist(termsOfService);

        return termsOfService.getKey();
    }

    @Transactional
    public void modifyTermsOfService(Long key, TermsOfServiceRequest termsOfServiceRequest) {
        TermsOfService termsOfService = termsOfServiceRepository.findById(key)
                                            .orElseThrow(() -> new TermsOfServiceNotFoundException());

        termsOfService.updateTermsOfService(termsOfServiceRequest);
    }

    @Transactional
    public void deleteTermsOfService(Long key) {
        if (!termsOfServiceRepository.existsById(key))
            throw new TermsOfServiceNotFoundException();

        termsOfServiceRepository.deleteById(key);
    }

    public TermsOfServiceResponse findByKey(Long key) {
        TermsOfService termsOfService = termsOfServiceRepository.findById(key)
                .orElseThrow(() -> new TermsOfServiceNotFoundException());

        return new TermsOfServiceResponse(termsOfService);
    }

    public TermsOfServiceResponse findMostRecentTermsOfServiceByTitle(String title) {
        List<TermsOfService> termsOfServices = termsOfServiceRepository.findByTitleOrderByCreatedDatetimeDesc(title);
        if (CollectionUtils.isNullOrEmpty(termsOfServices))
            throw new TermsOfServiceNotFoundException();

        return new TermsOfServiceResponse(termsOfServices.get(0));
    }

    public List<TermsOfServiceResponse> findAllByOrderByCreatedDatetimeDesc() {
        return termsOfServiceRepository.findAllByOrderByCreatedDatetimeDesc().stream()
                .map(m -> new TermsOfServiceResponse(m))
                .collect(Collectors.toList());
    }
}
