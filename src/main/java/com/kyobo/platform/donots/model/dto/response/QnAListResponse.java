package com.kyobo.platform.donots.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class QnAListResponse {
    public QnAListResponse(){

    }

    @Schema(description = "QnA 리스트")
    private List<QnAResponse> qnAResponseList;

    @Schema(description = "총 페이지")
    private int totalPage;

    @Schema(description = "게시물수")
    private Long totalElements;

    public QnAListResponse(List<QnAResponse> qnAResponseList, int totalPage, Long totalElements){
        this.qnAResponseList = qnAResponseList;
        this.totalElements = totalElements;
        this.totalPage = totalPage;
    }
}
