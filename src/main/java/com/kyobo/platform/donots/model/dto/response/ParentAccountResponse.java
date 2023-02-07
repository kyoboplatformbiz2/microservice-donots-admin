package com.kyobo.platform.donots.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyobo.platform.donots.model.entity.service.parent.ParentGrade;
import com.kyobo.platform.donots.model.entity.service.parent.ParentType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParentAccountResponse {
    private ParentType type;
    private ParentGrade grade;
    private String nickname;
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private String phoneNumber;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastSignInAt;
    private Long key;

    @QueryProjection
    public ParentAccountResponse(ParentType type, ParentGrade grade, String nickname, String id, LocalDateTime createdAt, String phoneNumber, String email, LocalDateTime lastSignInAt, Long key) {
        this.type = type;
        this.grade = grade;
        this.nickname = nickname;
        this.id = id;
        this.createdAt = createdAt;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.lastSignInAt = lastSignInAt;
        this.key = key;
    }
}
