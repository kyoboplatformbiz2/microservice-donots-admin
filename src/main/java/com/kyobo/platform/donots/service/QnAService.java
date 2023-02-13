package com.kyobo.platform.donots.service;

import com.kyobo.platform.donots.common.exception.DataNotFoundException;
import com.kyobo.platform.donots.model.dto.request.QnAUpdateRequest;
import com.kyobo.platform.donots.model.dto.response.QnAListResponse;
import com.kyobo.platform.donots.model.dto.response.QnAResponse;
import com.kyobo.platform.donots.model.entity.QnA;
import com.kyobo.platform.donots.model.repository.QnARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnAService {
    private final QnARepository qnARepository;

    public QnAListResponse getQnAList(String search, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        Page<QnA> qnaPage;
        log.info("start : " + start + ", end : " + end);
        if (search == null) {
            if (start != null || end != null)
                qnaPage = qnARepository.findByOpenDateBetween(start, end, pageable);
            else
                qnaPage = qnARepository.findAll(pageable);
        } else {
            if (start != null || end != null)
                qnaPage = qnARepository.findByOpenDateBetweenAndEmailContaining(start, end, search, pageable);
            else
                qnaPage = qnARepository.findByEmailContaining(search, pageable);
        }

        List<QnAResponse> qnAListResponseList = qnaPage.getContent().stream()
                .map(m -> new QnAResponse(m))
                .collect(Collectors.toList());
        return new QnAListResponse(qnAListResponseList, qnaPage.getTotalPages(), qnaPage.getTotalElements());
    }

    public void qnAUpdate(QnAUpdateRequest qnAUpdateRequest) {
        QnA qna = qnARepository.findById(qnAUpdateRequest.getKey()).get();
        if (qna == null) {
            throw new DataNotFoundException();
        }
        qna.updateQnA(qnAUpdateRequest.getQnACategory(), qnAUpdateRequest.getAnswer());
    }
}
