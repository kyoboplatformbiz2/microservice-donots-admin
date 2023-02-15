package com.kyobo.platform.donots.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

@Getter
@Setter
public class QnAListRequest {

    public QnAListRequest(){}

    private String search;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String start;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String end;

}
