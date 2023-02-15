package com.kyobo.platform.donots.model.entity;

import com.kyobo.platform.donots.model.dto.request.QnAUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "QNA")
public class QnA {
    @Id
    @Column(name = "QNA_KEY")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "QUESTION", columnDefinition = "TEXT", nullable = false)
    private String question;

    @Column(name = "ANSWER", columnDefinition = "TEXT")
    private String answer;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "REGEDIT_ID", nullable = false)
    private Long regeditId;

    @Column(name = "ADMIN_ID")
    private String adminId;

    @Enumerated(EnumType.STRING)
    @Column(name = "QNA_CATEGORY")
    private QnACategory qnACategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "OPEN_DATE", nullable = false)
    private LocalDateTime openDate;

    @Column(name = "CLOSE_DATE")
    private LocalDateTime closeDate;

    public void updateQnA(QnAUpdateRequest qnAUpdateRequest, String adminId) {
        this.qnACategory = qnAUpdateRequest.getQnACategory();
        this.answer = qnAUpdateRequest.getAnswer();
        this.status = qnAUpdateRequest.getStatus();
        this.adminId = adminId;
        this.closeDate = LocalDateTime.now();
    }
}
