package com.kyobo.platform.donots.model.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class FaqPostListResponse {

    public FaqPostListResponse(){}
    List<FaqPostResponse> faqPostResponseList;
    public FaqPostListResponse(List<FaqPostResponse> faqPostResponseList){
        this.faqPostResponseList = faqPostResponseList;
    }
}
