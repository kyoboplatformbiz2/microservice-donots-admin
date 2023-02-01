package com.kyobo.platform.donots.model.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyobo.platform.donots.model.entity.NoticePost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeResponse {

    public NoticeResponse() {}

    @Schema(description = "공지 게시물 번호")
    private Long noticePostKey;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "본문")
    private String body;

    @Schema(description = "이미지 주소")
    private String imgUrl;

    @Schema(description = "작성일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createdDate;
    
    @Schema(description = "최종수정일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime lastModifiedDate;

    @Schema(description = "게시 시작 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime boardStartDate;

    @Schema(description = "게시 종료 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime boardEndDate;

    @Schema(description = "신규 공지사항 (true) 등록일 기준 1주일 미만")
    private Boolean isNewPost;


    public NoticeResponse(NoticePost noticePost) {
        this.noticePostKey = noticePost.getNoticePostKey();
        this.title = noticePost.getTitle();
        this.body = noticePost.getBody();
        this.imgUrl = noticePost.getImageUrl();
        this.createdDate = noticePost.getCreatedDate();
        this.lastModifiedDate = noticePost.getLastModifiedDate();
        this.boardStartDate = noticePost.getBoardStartDate();
        this.boardEndDate = noticePost.getBoardEndDate();

        LocalDateTime now = LocalDateTime.now();
        if (noticePost.getCreatedDate().plusDays(7).isAfter(now))
            this.isNewPost = true;
        else
            this.isNewPost = false;
    }

}
