package com.kyobo.platform.donots.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "NOTICE_POST")
public class NoticePost {
    @Id
    @Column(name = "NOTICE_POST_KEY")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticePostKey;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "BODY", nullable = false)
    private String body;

    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "LAST_MODIFIED_DATE", nullable = false)
    private LocalDateTime lastModifiedDate;

    @Column(name = "BOARD_START_DATE", nullable = false)
    private LocalDateTime boardStartDate;

    @Column(name = "BOARD_END_DATE", nullable = false)
    private LocalDateTime boardEndDate;

    public void updateNotice(String title, String body, String imageUrl, LocalDateTime boardStartDate, LocalDateTime boardEndDate){
        LocalDateTime now = LocalDateTime.now();
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.lastModifiedDate = now;
        this.boardStartDate = boardStartDate;
        this.boardEndDate = boardEndDate;
    }

}
