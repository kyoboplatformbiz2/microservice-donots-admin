package com.kyobo.platform.donots.service;

import com.kyobo.platform.donots.common.exception.DataNotFoundException;
import com.kyobo.platform.donots.model.dto.request.QnAListRequest;
import com.kyobo.platform.donots.model.dto.request.QnAUpdateRequest;
import com.kyobo.platform.donots.model.dto.response.QnAListResponse;
import com.kyobo.platform.donots.model.dto.response.QnAResponse;
import com.kyobo.platform.donots.model.entity.AdminUser;
import com.kyobo.platform.donots.model.entity.QnA;
import com.kyobo.platform.donots.model.repository.QnARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnAService {
    private final QnARepository qnARepository;
    private final HttpSession httpSession;
//    public QnAListResponse getQnAList(QnAListRequest qnAListRequest, Pageable pageable) {
//        Page<QnA> qnaPage;
//        String search = qnAListRequest.getSearch();
//        LocalDateTime start = qnAListRequest.getStart();
//        LocalDateTime end = qnAListRequest.getEnd();
//        log.info("search" + search + ", start" + start + ", end" + end + ", pageable" + pageable );
//        if (search == null) {
//            if (start != null || end != null)
//                qnaPage = qnARepository.findByOpenDateBetween(start, end, pageable);
//            else
//                qnaPage = qnARepository.findAll(pageable);
//        } else {
//            if (start != null || end != null)
//                qnaPage = qnARepository.findByOpenDateBetweenAndEmailContaining(start, end, search, pageable);
//            else
//                qnaPage = qnARepository.findByEmailContaining(search, pageable);
//        }
//
//        List<QnAResponse> qnAListResponseList = qnaPage.getContent().stream()
//                .map(m -> new QnAResponse(m))
//                .collect(Collectors.toList());
//        return new QnAListResponse(qnAListResponseList, qnaPage.getTotalPages(), qnaPage.getTotalElements());
//    }

    public QnAListResponse getQnAList(QnAListRequest qnAListRequest, Pageable pageable) {
        Page<QnA> qnaPage;
        String search = qnAListRequest.getSearch();
        LocalDateTime start = null;
        LocalDateTime end = null;
        if(qnAListRequest.getStart()!=null)
            start = convertStringForLocalDateTime(qnAListRequest.getStart());
        if(qnAListRequest.getEnd()!=null)
            end = convertStringForLocalDateTime(qnAListRequest.getEnd());

        log.info("search" + search + ", start" + start + ", end" + end + ", pageable" + pageable );

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
        AdminUser myAdminUser = (AdminUser) httpSession.getAttribute("adminUser");
        QnA qna = qnARepository.findById(qnAUpdateRequest.getKey()).get();
        if (qna == null) {
            throw new DataNotFoundException();
        }
        qna.updateQnA(qnAUpdateRequest, myAdminUser.getAdminId());
    }

    private LocalDateTime convertStringForLocalDateTime(String time){
        time = time + " 00:00:00.000";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return LocalDateTime.parse(time, formatter);
    }
}
