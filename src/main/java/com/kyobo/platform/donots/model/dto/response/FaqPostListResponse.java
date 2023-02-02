package com.kyobo.platform.donots.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class FaqPostListResponse {

    public FaqPostListResponse(){}

    @Schema(description = "Faq 리스트")
    List<FaqPostResponse> faqPostResponseList;

    @Schema(description = "총 페이지")
    private int totalPage;

    @Schema(description = "게시물수")
    private Long totalElements;
    public FaqPostListResponse(List<FaqPostResponse> faqPostResponseList, int totalPage, long totalElements){
        this.faqPostResponseList = faqPostResponseList;
        this.totalElements = totalElements;
        this.totalPage = totalPage;
    }
}
