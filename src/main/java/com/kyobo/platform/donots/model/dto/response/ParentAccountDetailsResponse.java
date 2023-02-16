package com.kyobo.platform.donots.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyobo.platform.donots.common.util.AES256Util;
import com.kyobo.platform.donots.common.util.MarkingUtil;
import com.kyobo.platform.donots.model.entity.service.account.Account;
import com.kyobo.platform.donots.model.entity.service.account.SocialAccount;
import com.kyobo.platform.donots.model.entity.service.parent.Baby;
import com.kyobo.platform.donots.model.entity.service.parent.Parent;
import com.kyobo.platform.donots.model.entity.service.parent.ParentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter // 이 애너테이션이 없으면 @JsonProperty가 지정된 필드만 직렬화된다.
public class ParentAccountDetailsResponse {
    // 회원 속성
    private ParentType type;
    private String nickname;
    private String email;
    @Builder.Default
    private List<BabyDto> babies = new ArrayList<>();

    // 계정 속성
    private String id;
    private String name;
    private String gender;
    private String birthDay;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String phoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastSignInAt;

    // 소셜계정 목록
    @Builder.Default
    private List<SocialAccountForMemberDetailsDto> socialAccounts = new ArrayList<>();

    public static ParentAccountDetailsResponse from(Parent parent, Account account, List<SocialAccount> socialAccounts) throws Exception {
        MarkingUtil markingUtil = new MarkingUtil();
        AES256Util aes256Util = new AES256Util();

        List<BabyDto> babyDtosFromEntity = new ArrayList<>();
        for (Baby baby : parent.getBabies())
            babyDtosFromEntity.add(BabyDto.from(baby));

        List<SocialAccountForMemberDetailsDto> socialAccountDtosFromEntity = new ArrayList<>();
        for (int i = 0; i < socialAccounts.size(); i++) {
            socialAccountDtosFromEntity.add(SocialAccountForMemberDetailsDto.from(socialAccounts.get(i)));
            socialAccountDtosFromEntity.get(i).setEmail(markingUtil.emailUsername4LettersMasking(socialAccountDtosFromEntity.get(i).getEmail()));
        }

        return ParentAccountDetailsResponse.builder()
                .type(parent.getType())
                .nickname(parent.getNickname())
                .email(markingUtil.emailUsername4LettersMasking(parent.getEmail()))
                .babies(babyDtosFromEntity)
                .id(account.getId())
                .name(markingUtil.nameMasking(aes256Util.decrypt(account.getName())))
                .gender(account.getGender())
                .birthDay(account.getBirthDay())
                .createdAt(account.getCreatedAt())
                .phoneNumber(markingUtil.phoneMasking(aes256Util.decrypt(account.getPhoneNumber())))
                .lastSignInAt(account.getLastSignInAt())
                .socialAccounts(socialAccountDtosFromEntity)
                .build();
    }
}
