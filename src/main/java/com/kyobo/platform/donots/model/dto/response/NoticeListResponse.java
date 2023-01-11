package com.kyobo.platform.donots.model.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class NoticeListResponse {

    public NoticeListResponse(){}
    List<NoticeResponse> noticeResponseList;

    public NoticeListResponse(List<NoticeResponse> list){
        this.noticeResponseList = list;
    }
}
