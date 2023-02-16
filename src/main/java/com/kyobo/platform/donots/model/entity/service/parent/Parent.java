package com.kyobo.platform.donots.model.entity.service.parent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parent")
//@TypeDef(
//        name = "list-array",
//        typeClass = ListArrayType.class
//)
//@JsonFilter("ParentFilter")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // Builder를 위한 생성자 (NoArgsConstructor를 protected로 선언하면 오류 발생)
@Builder
@Data
@ToString(exclude = {"babies"/*, "profileSelectedBaby"*/}, callSuper = true)    // ToString 순환참조 방지
public class Parent {

    @Id @GeneratedValue
    @Column(name = "parent_key")
    private Long key;

    @Column(unique = true)
    private Long accountKey;

    @Enumerated(EnumType.STRING)
    private ParentType type;

    @Enumerated(EnumType.STRING)
    private ParentGrade grade;
//    @Transient private ActivityIndicator activityIndicator;

    // Internet Explorer에서 입력 가능한 최대 URL 길이. (GET Method 사용)
    // 다른 브라우저는 이것보다 더 길기 때문에 가장 짧은 Internet Explorer를 기준으로 했다.
    @Column(length = 2048)
    private String nickname;
    private String email;
    private String profilePictureUrl;
    private String briefBio;
    private String socialMediaUrl;

    @OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Builder.Default
    private List<Baby> babies = new ArrayList<>();

    private Long profileSelectedBabyKey;

    // 마케팅 개인정보 수집 약관
    @JsonProperty("isTermsCollectingPersonalDataMarketingAgreed")
    private Boolean isTermsCollectingPersonalDataMarketingAgreed;

    // 전송매체별 광고성 정보의 수신 동의
    @JsonProperty("isEmailReceiveAgreed")
    private Boolean isEmailReceiveAgreed;
    private LocalDateTime emailReceiveAgreementModifiedDatetime;

    @JsonProperty("isTextMessageReciveAgreed")
    private Boolean isTextMessageReciveAgreed;
    private LocalDateTime textMessageReciveAgreementModifiedDatetime;

    // 설정 > Push 설정 > 마케팅 알림 수신
    @JsonProperty("isMarketingInfoPushNotifSet")
    private Boolean isMarketingInfoPushNotifSet;
    private LocalDateTime marketingInfoPushNotifSettingModifiedDatetime;

    // 설정 > Push 설정 > 작성 콘텐츠 등록/검수 결과
    @JsonProperty("isPostCensorshipResultPushNotifSet")
    private Boolean isPostCensorshipResultPushNotifSet;

    // 회원등급 산정과 오늘의 회원을 위한 활동지표
    @Builder.Default private Integer pageviewedCount = 0;                       // 조회된수
    @Builder.Default private Integer recipePostingCount = 0;                    // 레시피게시수
    @Builder.Default private Integer scrapbookedCount = 0;                      // 스크랩된수
    @Builder.Default private Integer reactionAddingCount = 0;                   // 반응한수
    @Builder.Default private Integer scrapbookingCount = 0;                     // 스크랩한수
    @Builder.Default private Integer todaysMemberRanking = Integer.MAX_VALUE;   // 오늘의 회원 순위
    private LocalDateTime lastActivityIndicatorAggregatedDatetime;              // 마지막 활동지표 집계시각

    // 마케팅 동의 및 알림방식 => 계정영역에서 처리함
//    @Enumerated(EnumType.STRING)
//    @Type(type = "list-array")
//    @Column(columnDefinition = "text[]")
//    private List<NotifMethod> notifMethods = new ArrayList<>();

    /*
    public int getBabyIdxByKey(Long babyKey) {
        for (int i = 0; i < babies.size(); ++i) {
            if (babies.get(i).getKey() == babyKey)
                return i;
        }

        return -1;
    }

    // 연관관계 편의 메서드
    public void removeBaby(Baby baby) {
        int babyIdx = getBabyIdxByKey(baby.getKey());
        if (babyIdx == -1) {
            // TODO 예외처리
            return;
        }

        this.babies.remove(babyIdx);
//        baby.setParent(new Parent()); // 이건 됨...
//        baby.setParent(null);   // NPE 발생
        // Call-by-reference
        baby = null;    // 이건 왜 안 될까
    }
    */

    public void updatePostCensorshipResultNotifSet(boolean postCensorshipResultNotifSet) {
        this.isPostCensorshipResultPushNotifSet = postCensorshipResultNotifSet;
    }

    // TODO API 별로 유효한 값만 받도록 메서드를 여러개 둬야할까?
    // 아니면 유효성 체크로 다 걸러낼까?
    public void deepCopyAllExceptKeyFrom(Parent parent) {
        // 값이 없는 필드가 변경되는 것은 의도에 맞지 않으므로 값이 있는 필드만 복사한다
        // TODO Later Reflection 사용한 동적 null 검사
        if (parent.getAccountKey() != null) this.accountKey = parent.getAccountKey();
        if (parent.getType() != null)   this.type = parent.getType();
        if (parent.getGrade() != null)  this.grade = parent.getGrade();
        if (parent.getNickname() != null)   this.nickname = parent.getNickname();
        if (parent.getEmail() != null)  this.email = parent.getEmail();
        if (parent.getProfilePictureUrl() != null)  this.profilePictureUrl = parent.getProfilePictureUrl();
        if (parent.getBriefBio() != null)   this.briefBio = parent.getBriefBio();
        if (parent.getSocialMediaUrl() != null) this.socialMediaUrl = parent.getSocialMediaUrl();
        if (!CollectionUtils.isEmpty(parent.getBabies()))   this.babies = parent.getBabies();
        if (parent.getProfileSelectedBabyKey() != null)     this.profileSelectedBabyKey = parent.getProfileSelectedBabyKey();
        if (parent.getIsPostCensorshipResultPushNotifSet() != null) this.isPostCensorshipResultPushNotifSet = parent.getIsPostCensorshipResultPushNotifSet();
        if (parent.getIsMarketingInfoPushNotifSet() != null)    this.isMarketingInfoPushNotifSet = parent.getIsMarketingInfoPushNotifSet();
        if (parent.getIsTermsCollectingPersonalDataMarketingAgreed() != null)   this.isTermsCollectingPersonalDataMarketingAgreed = parent.getIsTermsCollectingPersonalDataMarketingAgreed();
    }
}
