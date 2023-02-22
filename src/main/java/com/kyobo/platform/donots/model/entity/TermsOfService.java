package com.kyobo.platform.donots.model.entity;

import com.kyobo.platform.donots.model.dto.request.TermsOfServiceRequest;
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
@Table(name = "terms_of_service")
public class TermsOfService {
    @Id
    @GeneratedValue
    @Column(name = "terms_of_service_key")
    private Long key;
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;
    private String version;
    private String adminId;

    // Internet Explorer에서 입력 가능한 최대 URL 길이. (GET Method 사용)
    // 다른 브라우저는 이것보다 더 길기 때문에 가장 짧은 Internet Explorer를 기준으로 했다.
    @Column(length = 2048)
    private String bodyHtmlFileUrl;
    private LocalDateTime postingStartDatetime;
    private LocalDateTime postingEndDatetime;
    private LocalDateTime createdDatetime;
    private LocalDateTime lastModifiedDatetime;

    public void updateTermsOfService(TermsOfServiceRequest termsOfServiceRequest) {
        this.title = termsOfServiceRequest.getTitle();
        this.body = termsOfServiceRequest.getBody();
        this.version = termsOfServiceRequest.getVersion();
        this.adminId = termsOfServiceRequest.getAdminId();
        this.bodyHtmlFileUrl = termsOfServiceRequest.getBodyHtmlFileUrl();
        this.postingStartDatetime = termsOfServiceRequest.getPostingStartDatetime();
        this.postingEndDatetime = termsOfServiceRequest.getPostingEndDatetime();
        this.lastModifiedDatetime = LocalDateTime.now();
    }
}
