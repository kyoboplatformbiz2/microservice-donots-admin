package com.kyobo.platform.donots.model.dto.response;

import com.kyobo.platform.donots.model.entity.service.account.ProviderType;
import com.kyobo.platform.donots.model.entity.service.account.SocialAccount;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class SocialAccountForMemberDetailsDto {
    private Long accountKey;
    private ProviderType providerType;
    private String email;

    public static SocialAccountForMemberDetailsDto from(SocialAccount socialAccount) {

        return SocialAccountForMemberDetailsDto.builder()
                .accountKey(socialAccount.getAccountKey())
                .providerType(socialAccount.getProviderType())
                .email(socialAccount.getEmail())
                .build();
    }
}
