package com.kyobo.platform.donots.service;

import com.kyobo.platform.donots.common.exception.DataNotFoundException;
import com.kyobo.platform.donots.model.dto.request.NoticeRequest;
import com.kyobo.platform.donots.model.dto.response.NoticeResponse;
import com.kyobo.platform.donots.model.entity.NoticePost;
import com.kyobo.platform.donots.model.repository.NoticePostRepository;

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
public class NoticeService {

    private final NoticePostRepository noticePostRepository;

    public Long noticeRegedit(NoticeRequest noticeRequest) {
        NoticePost noticepost = noticePostRepository.save(noticePostRegedit(noticeRequest));
        return noticepost.getNoticePostKey();
    }
    public List<NoticeResponse> getNoticeList() {
        return noticePostRepository.findAllByOrderByCreatedDateDesc().stream()
                .map(m-> new NoticeResponse(m))
                .collect(Collectors.toList());
    }
    public NoticeResponse getNoticeDetail(Long noticePostKey) {
        NoticePost noticePost = noticePostRepository.findByNoticePostKey(noticePostKey);
        return new NoticeResponse(noticePost);
    }

    public void deleteNotice(Long noticePostKey) {
        noticePostRepository.deleteById(noticePostKey);
    }

    @Transactional
    public void updateNotice(Long noticePostKey, NoticeRequest noticeRequest) {
        NoticePost noticePost = noticePostRepository.findByNoticePostKey(noticePostKey);
        if(noticePost == null){
            throw new DataNotFoundException();
        }
        noticePost.updateNotice(noticeRequest.getTitle(), noticeRequest.getBody(), noticeRequest.getImgUrl(),
                noticeRequest.getBoardStartDate(), noticeRequest.getBoardEndDate());
    }

    private NoticePost noticePostRegedit(NoticeRequest noticeRequest){
        LocalDateTime now = LocalDateTime.now();
        NoticePost noticePost = NoticePost.builder()
                .title(noticeRequest.getTitle())
                .imageUrl(noticeRequest.getImgUrl())
                .body(noticeRequest.getBody())
                .boardStartDate(noticeRequest.getBoardStartDate())
                .boardEndDate(noticeRequest.getBoardEndDate())
                .createdDate(now)
                .lastModifiedDate(now)
                .build();
        return noticePost;
    }
}
