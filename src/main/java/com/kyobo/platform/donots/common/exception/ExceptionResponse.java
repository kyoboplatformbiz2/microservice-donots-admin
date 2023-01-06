package com.kyobo.platform.donots.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private Date timestamp;
    private int code;
    private String message;
    private String details;

    public ExceptionResponse(Date timestamp, BusinessException be, String details) {
        this.timestamp = timestamp;
        this.code = be.code;
        this.message = be.getMessage();
        this.details = details;
    }
}
