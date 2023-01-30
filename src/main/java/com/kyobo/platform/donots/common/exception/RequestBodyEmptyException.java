package com.kyobo.platform.donots.common.exception;

public class RequestBodyEmptyException extends BusinessException {

    public RequestBodyEmptyException() {
        super(ErrorCode.REQUEST_BODY_IS_EMPTY.status, ErrorCode.REQUEST_BODY_IS_EMPTY.message);
    }

    public RequestBodyEmptyException(String caseSpecificMessage) {
        super(ErrorCode.REQUEST_BODY_IS_EMPTY.status, caseSpecificMessage);
    }
}
