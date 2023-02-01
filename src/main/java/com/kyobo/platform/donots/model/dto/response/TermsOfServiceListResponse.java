package com.kyobo.platform.donots.model.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class TermsOfServiceListResponse {

    List<TermsOfServiceResponse> termsOfServiceResponseList;

    public TermsOfServiceListResponse(){}

    public TermsOfServiceListResponse(List<TermsOfServiceResponse> termsOfServiceResponseList){
        this.termsOfServiceResponseList = termsOfServiceResponseList;
    }
}
