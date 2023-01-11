package com.kyobo.platform.donots.model.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class FaqListResponse {

    public FaqListResponse(){}
    List<FaqResponse> faqResponseList;
    public FaqListResponse(List<FaqResponse> list){
        this.faqResponseList = list;
    }
}
