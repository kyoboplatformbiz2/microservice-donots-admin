package com.kyobo.platform.donots.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DataNotFoundException extends BusinessException {

    public DataNotFoundException() {
        super(ErrorCode.DATA_NOT_FOUND.status, ErrorCode.DATA_NOT_FOUND.message);
    }

    public DataNotFoundException(String caseSpecificMessage) {
        super(ErrorCode.DATA_NOT_FOUND.status, caseSpecificMessage);
    }
}
