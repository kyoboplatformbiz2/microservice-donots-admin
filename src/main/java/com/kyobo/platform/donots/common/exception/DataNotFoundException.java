package com.kyobo.platform.donots.common.exception;

public class DataNotFoundException extends BusinessException {

    public DataNotFoundException() {
        super(ErrorCode.DATA_NOT_FOUND.status, ErrorCode.DATA_NOT_FOUND.message);
    }

    public DataNotFoundException(String caseSpecificMessage) {
        super(ErrorCode.DATA_NOT_FOUND.status, caseSpecificMessage);
    }
}
