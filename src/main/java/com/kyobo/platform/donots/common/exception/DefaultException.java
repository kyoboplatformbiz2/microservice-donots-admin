package com.kyobo.platform.donots.common.exception;

public class DefaultException extends BusinessException {

    public DefaultException() {
        super(ErrorCode.DEFAULT.status, ErrorCode.DEFAULT.message);
    }

    public DefaultException(String caseSpecificMessage) {
        super(ErrorCode.DEFAULT.status, caseSpecificMessage);
    }
}
