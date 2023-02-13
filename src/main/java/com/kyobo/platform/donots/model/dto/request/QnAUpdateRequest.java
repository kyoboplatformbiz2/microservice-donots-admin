package com.kyobo.platform.donots.model.dto.request;

import com.kyobo.platform.donots.model.entity.QnACategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
public class QnAUpdateRequest {

    @Schema(description = "QnA 번호")
    @NotNull
    private Long key;

    @Enumerated(EnumType.STRING)
    @Schema(description = "카테고리")
    @NotNull
    private QnACategory qnACategory;

    @Schema(description = "답변")
    @NotNull
    private String answer;

}
