package com.kyobo.platform.donots.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyobo.platform.donots.common.util.AES256Util;
import com.kyobo.platform.donots.common.util.MarkingUtil;
import com.kyobo.platform.donots.model.entity.service.parent.ParentGrade;
import com.kyobo.platform.donots.model.entity.service.parent.ParentType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Data
public class ParentAccountResponse {
    private ParentType type;
    private ParentGrade grade;
    private String nickname;
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String phoneNumber;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastSignInAt;
    private Long key;

    @QueryProjection
    public ParentAccountResponse(ParentType type, ParentGrade grade, String nickname, String id, LocalDateTime createdAt, String phoneNumber, String email, LocalDateTime lastSignInAt, Long key) throws Exception {
        MarkingUtil markingUtil = new MarkingUtil();
        AES256Util aes256Util = new AES256Util();

        this.type = type;
        this.grade = grade;
        if (StringUtils.hasText(nickname))
            this.nickname = markingUtil.nicknameMasking(nickname);

        if (StringUtils.hasText(id))
            this.id = id;

        this.createdAt = createdAt;
        this.phoneNumber = markingUtil.phoneMasking(aes256Util.decrypt(phoneNumber));
        this.email = markingUtil.emailUsername4LettersMasking(email);
        this.lastSignInAt = lastSignInAt;
        this.key = key;
    }
}
